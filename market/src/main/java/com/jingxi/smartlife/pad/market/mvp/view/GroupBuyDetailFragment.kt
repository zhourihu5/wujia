package com.jingxi.smartlife.pad.market.mvp.view

import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.jingxi.smartlife.pad.market.R
import com.jingxi.smartlife.pad.market.mvp.data.AttaInfosItem
import com.jingxi.smartlife.pad.market.mvp.data.GroupBuyDetailVo
import com.jingxi.smartlife.pad.market.mvp.model.MarketModel
import com.wujia.businesslib.TitleFragment
import com.wujia.businesslib.data.ApiResponse
import com.wujia.businesslib.event.EventBusUtil
import com.wujia.businesslib.event.EventToGroupBuy
import com.wujia.businesslib.event.IMiessageInvoke
import com.wujia.lib.imageloader.DensityUtil.Companion.dp2px
import com.wujia.lib.imageloader.ImageLoaderManager
import com.wujia.lib.widget.PileAvartarLayout
import com.wujia.lib_common.data.network.SimpleRequestSubscriber
import com.wujia.lib_common.data.network.exception.ApiException
import com.wujia.lib_common.utils.DateUtil
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.fragment_group_buy_detail.*
import java.util.*


/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12 20:06
 * description ：
 */
class GroupBuyDetailFragment : TitleFragment() {
    override val layoutId: Int
        get() =  R.layout.fragment_group_buy_detail
    override val title: Int
        get() = R.string.group_buy_detail

    var endDate:String?= null
     private var handler:Handler?= null

    private val eventGroupBuy = EventToGroupBuy(object : IMiessageInvoke<EventToGroupBuy> {
        override fun eventBus(event: EventToGroupBuy) {
            pop()
        }
    })
    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        val id= arguments?.getString(BUNDLE_KEY_ID,"")
        addSubscribe(MarketModel().getGroupBuyDetail(id).subscribeWith(object : SimpleRequestSubscriber<ApiResponse<GroupBuyDetailVo>>(this@GroupBuyDetailFragment, ActionConfig(true, SHOWERRORMESSAGE)) {
            override fun onResponse(response: ApiResponse<GroupBuyDetailVo>) {
                super.onResponse(response)
                val ac=response.data!!.activity
                tvGoodsTitle.text = ac.title
                ImageLoaderManager.instance.loadImage(ac.cover, 0, ivCover)
                val bannerAdapter=BannerAdapter(context!!,ac.commodity.attaInfos!!)
                viewPager.adapter=bannerAdapter
                endDate=ac.endDate
                initTimer()
                val lables= ac.commodity.labelsName
                if(!TextUtils.isEmpty(lables)){
                    flowlayout.adapter= object : TagAdapter<String>(lables.split('|')) {
                        override fun getView(parent: FlowLayout?, position: Int, t: String?): View {
                            val tvLabel= LayoutInflater.from(mContext).inflate(R.layout.item_label,parent,false) as TextView
                            tvLabel.text = t
                            return tvLabel
                        }
                    }
                }
                llAvatar.visibility=View.GONE
                response.data!!.userInfoList?.let {
                    avatarContainer.setFlag(true)
                    avatarContainer.setSpWidth(dp2px(context!!,15f))
                    avatarContainer.setAdapter(object : PileAvartarLayout.Adapter {
                        override fun getCount(): Int {
                            return response.data!!.userInfoList!!.size
                        }
                        override fun getView( position: Int): View? {
                            if(position<10){
                                val imageView=LayoutInflater.from(context).inflate(R.layout.item_goods_avatar,avatarContainer,false)
                                 response.data!!.userInfoList?.get(position)?.wxCover?.let {
                                     ImageLoaderManager.instance.loadCircleImage(it,0,imageView as ImageView)
                                     return imageView
                                 }

                            }
                            return null
                        }
                    })

                    tvUserNum.text = "…等${ac.commodity.salesNum}名用户已参与"
                    if(response.data!!.userInfoList!!.isNotEmpty()){
                        llAvatar.visibility=View.VISIBLE
                    }
                }

                if(!TextUtils.isEmpty(ac.saleTip)){
                    val saleTipArr=  ac.saleTip.split(',')
                    tvRejoinNum.text = saleTipArr[0]
                    tvRejoinMoney.text = saleTipArr[1]
                    llRejoinNum.visibility=View.VISIBLE
                }else{
                    llRejoinNum.visibility=View.GONE
                }
                btGo.setOnClickListener {
                    val orderConfirmFragment=OrderConfirmFragment.newInstance(response.data!!  )
                    start(orderConfirmFragment)
                }


                val priceArr=ac.price.split('.')
                tvPriceInt.text = priceArr[0]
                if(priceArr.size>1){
                    tvFloat.text = ".${priceArr[1]}"
                }
                tvPriceOld.text = "￥"+ac.commodity.price
                tvPriceOld.paint.flags= Paint.STRIKE_THRU_TEXT_FLAG
                tvSaleNum.text = "已抢购${ac.commodity.salesNum}件"
                tvDiscount.text = ac.largeMoney

                val goodsFmt= arrayOf("产地","规格", "重量", "包装", "保质期", "贮存方式")
                val goodsFmtDes=ac.commodity.formatVals!!
                llGoodsFormat.removeAllViews()
                for( i in goodsFmt.indices){
                    val item=LayoutInflater.from(context).inflate(R.layout.item_goods_format,llGoodsFormat,false)
                    item.findViewById<TextView>(R.id.tvFormatName).text = goodsFmt[i]
                    item.findViewById<TextView>(R.id.tvFormatDes).text = goodsFmtDes[i]
                    if(i==0){
                        item.setBackgroundResource(R.drawable.table_cell_top)
                    }else{
                        item.setBackgroundResource(R.drawable.table_cell)
                    }
                    llGoodsFormat.addView(item)
                }

                webView.settings.layoutAlgorithm= WebSettings.LayoutAlgorithm.SINGLE_COLUMN
                webView.loadData(response.data!!.activity.commodity.infos, "text/html; charset=UTF-8", null)
            }

        }))
        EventBusUtil.register(eventGroupBuy)
    }



    fun initTimer(){
        handler=object :Handler(){
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                setTimeRemain()
            }
        }
        (handler as Handler).sendEmptyMessage(0)
    }
    fun setTimeRemain() {
        if(TextUtils.isEmpty(endDate)){
            handler?.removeCallbacksAndMessages(null)
            return
        }
        val endDate=DateUtil.getDate(endDate!!)
        val now = Date()
        val milli = endDate.time - now.time
        if (milli <= 0) {
            tvTimeToEnd.text = "距结束截止仅剩：00时 00分 "
            tvTimeToEndSeconde.text = "00秒"
            handler?.removeCallbacksAndMessages(null)
            return
        }
        val hour = milli / 1000 / 3600
        val minute =milli % (3600 * 1000) / (60 * 1000)
        val second = milli % (1000 * 60) / 1000
        tvTimeToEnd.text = "距结束截止仅剩：${formatNumber(hour)}时 ${formatNumber(minute)}分 "
        tvTimeToEndSeconde.text = "${formatNumber(second)}秒"

        handler?.sendEmptyMessageDelayed(0,1000)
    }
    private fun formatNumber(n: Long):String{
        var t=""
        if(n<10){
            t="0"
        }
        return "${t}${n}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler?.removeCallbacksAndMessages(null)
        EventBusUtil.unregister(eventGroupBuy)
    }

    companion object {
        const val BUNDLE_KEY_ID="id"

        fun newInstance(): GroupBuyDetailFragment {
            val fragment = GroupBuyDetailFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    class BannerAdapter(private var mContext: Context, private var datas: List<AttaInfosItem>) :PagerAdapter(){
        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        override fun getCount(): Int {
            return datas.size
        }
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val imageView=LayoutInflater.from(mContext).inflate(R.layout.item_goods_banner,container,false) as ImageView
            imageView.scaleType=ImageView.ScaleType.CENTER_CROP
            ImageLoaderManager.instance.loadImage(datas[position].attaAddr, 0, imageView)

            container.addView(imageView)
            return imageView
        }
        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

    }
}

