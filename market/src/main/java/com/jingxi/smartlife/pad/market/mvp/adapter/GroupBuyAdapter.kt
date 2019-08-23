package com.jingxi.smartlife.pad.market.mvp.adapter

import android.content.Context
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.jingxi.smartlife.pad.market.R
import com.jingxi.smartlife.pad.market.mvp.data.ContentItem
import com.wujia.lib.imageloader.ImageLoaderManager
import com.wujia.lib_common.base.baseadapter.CommonAdapter
import com.wujia.lib_common.base.baseadapter.base.ViewHolder
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
class GroupBuyAdapter(context: Context, datas: List<ContentItem>) : CommonAdapter<ContentItem>(context, R.layout.item_group_buy, datas) {

    var toDetailClickLisner:((holder: ViewHolder, t: ContentItem, position: Int)->Unit)?=null
    override fun convert(holder: ViewHolder, t: ContentItem, position: Int) {
       var labelArr:List<String>?=null
        t.commodity.labelsName?.let { labelArr=it.split('|') }
        val flowLayout=holder.getView<com.zhy.view.flowlayout.TagFlowLayout>(R.id.flowlayout)

        flowLayout.adapter= object : TagAdapter<String>(labelArr) {
            override fun getView(parent: FlowLayout?, position: Int, t: String?): View {
                val tvLabel=LayoutInflater.from(mContext).inflate(R.layout.item_label,parent,false) as TextView
                tvLabel.setText(t)
                return tvLabel
            }
        }
        val img = holder.getView<ImageView>(R.id.ivCover)
        ImageLoaderManager.instance.loadImage(t.cover, 0, img)
        holder.setText(R.id.tvName,t.title)
        val priceArr= t.price.split('.')
        holder.setText(R.id.tvPriceInt,priceArr[0])
        if(priceArr.size>1){
            holder.setText(R.id.tvFloat,".${priceArr[1]}")
        }
        holder.setText(R.id.tvPriceOld,"${t.commodity.price}")
        holder.getView<TextView>(R.id.tvPriceOld).paint.flags=STRIKE_THRU_TEXT_FLAG
        holder.setText(R.id.tvSaleNum,"已抢购${t.commodity.salesNum}件")
        holder.getView<View>(R.id.btGo).setOnClickListener {
            toDetailClickLisner?.invoke(holder,t,position)
        }

    }
    fun setClickLisner(toDetailClickLisner:((holder: ViewHolder, t: ContentItem, position: Int)->Unit)?){
        this.toDetailClickLisner=toDetailClickLisner
    }

}
