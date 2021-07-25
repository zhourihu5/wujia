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
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.jingxi.smartlife.pad.market.R
import com.jingxi.smartlife.pad.market.mvp.adapter.GroupBuyAdapter
import com.jingxi.smartlife.pad.market.mvp.data.AttaInfosItem
import com.jingxi.smartlife.pad.market.mvp.data.GroupBuyDetailVo
import com.jingxi.smartlife.pad.market.mvp.data.GroupBuyVo
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
import kotlinx.android.synthetic.main.fragment_group_buy.*
import kotlinx.android.synthetic.main.fragment_group_buy_detail2.*
import me.yokeyword.fragmentation.SupportFragment
import java.util.*


/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12 20:06
 * description ：
 */
class GroupBuyDetailFragment2 : TitleFragment() {
    override val layoutId: Int
        get() =  R.layout.fragment_group_buy_detail2
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
        addSubscribe(MarketModel().getGroupBuyDetail(id).subscribeWith(object : SimpleRequestSubscriber<ApiResponse<GroupBuyDetailVo>>(this@GroupBuyDetailFragment2, ActionConfig(true, SHOWERRORMESSAGE)) {
            override fun onResponse(response: ApiResponse<GroupBuyDetailVo>) {
                super.onResponse(response)
                val ac=response.data!!.activity
                tvName.text = ac.title
                ImageLoaderManager.instance.loadImage(ac.cover, 0, ivCover)

                endDate=ac.endDate
                initTimer()
                llAvatar.visibility=View.GONE
                response.data!!.userInfoList?.let {
                    avatarContainer.setFlag(true)
                    avatarContainer.setSpWidth(dp2px(context!!,27f))
                    avatarContainer.setAdapter(object : PileAvartarLayout.Adapter {
                        override fun getCount(): Int {
                            return response.data!!.userInfoList!!.size
                        }
                        override fun getView( position: Int): View? {
                            if(position<10){
                                val imageView=LayoutInflater.from(context).inflate(R.layout.item_goods_avatar2,avatarContainer,false)

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

                val priceArr=ac.price.split('.')
                tvPriceInt.text = priceArr[0]
                if(priceArr.size>1){
                    tvFloat.text = ".${priceArr[1]}"
                }
                tvPriceOld.text = "￥"+ac.commodity.price
                tvPriceOld.paint.flags= Paint.STRIKE_THRU_TEXT_FLAG

            }

        }))
        addSubscribe(MarketModel().getGroupBuyOtherList().subscribeWith(object : SimpleRequestSubscriber<ApiResponse<GroupBuyVo>>(this@GroupBuyDetailFragment2, ActionConfig(true, SHOWERRORMESSAGE)) {
            override fun onResponse(response: ApiResponse<GroupBuyVo>) {
                super.onResponse(response)
                val mAdapter = GroupBuyAdapter(context!!, response.data!!.content!!)
                mAdapter.setClickLisner{
                    holder, t, position->
                    run {
                        val groupBuyDetailFragment: SupportFragment?
                        groupBuyDetailFragment = if("1" == t.isJoin){
                            newInstance()
                        }else{
                            GroupBuyDetailFragment.newInstance()
                        }

                        val bundle = Bundle()
                        bundle.putString(GroupBuyDetailFragment.BUNDLE_KEY_ID, t.id)
                        groupBuyDetailFragment.arguments = bundle
                        start(groupBuyDetailFragment)
                    }
                }

                rvGroupBuy!!.adapter = mAdapter

            }

            override fun onFailed(apiException: ApiException) {
                super.onFailed(apiException)
                swipeRefreshLayout!!.isRefreshing = false
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
            tvHour.text = "00"
            tvMinute.text = "00"
            tvSecond.text = "00"
            handler?.removeCallbacksAndMessages(null)
            return
        }
        val hour = milli / 1000 / 3600
        val minute =milli % (3600 * 1000) / (60 * 1000)
        val second = milli % (1000 * 60) / 1000
        tvHour.text = formatNumber(hour)
        tvMinute.text = formatNumber(minute)
        tvSecond.text = formatNumber(second)

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

        fun newInstance(): GroupBuyDetailFragment2 {
            val fragment = GroupBuyDetailFragment2()
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

