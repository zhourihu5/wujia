package com.jingxi.smartlife.pad.market.mvp.adapter

import android.content.Context
import android.graphics.Paint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.jingxi.smartlife.pad.market.R
import com.jingxi.smartlife.pad.market.mvp.data.OrderItemVo
import com.wujia.lib.imageloader.ImageLoaderManager
import com.wujia.lib_common.base.baseadapter.CommonAdapter
import com.wujia.lib_common.base.baseadapter.base.ViewHolder

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
class OrderAdapter(context: Context, datas: List<OrderItemVo>) : CommonAdapter<OrderItemVo>(context, R.layout.item_order, datas) {

    var btnClickLisner:((holder: ViewHolder, t: OrderItemVo, position: Int)->Unit)?=null
    override fun convert(holder: ViewHolder, t: OrderItemVo, position: Int) {
        val img = holder.getView<ImageView>(R.id.ivCover)
        ImageLoaderManager.instance.loadImage(t.activity.cover, 0, img)
        holder.setText(R.id.tvName,t.activity.title)
        val priceArr= t.activity.price.split('.')
        holder.setText(R.id.tvPriceInt,priceArr[0])
        if(priceArr.size>1){
            holder.setText(R.id.tvFloat,".${priceArr[1]}")
        }
        holder.setText(R.id.tvPriceOld,"￥${t.commodity.price}")
        holder.getView<TextView>(R.id.tvPriceOld).paint.flags= Paint.STRIKE_THRU_TEXT_FLAG
//        holder.setText(R.id.tvSaleNum,"已抢购${t.commodity.salesNum}件")

        holder.setVisible(R.id.tvToPay,false)
        holder.setVisible(R.id.tvGoStroll,false)
        holder.setVisible(R.id.tvAskDilivery,false)
        when(t.status){
            "1"->{//待付款
                holder.setVisible(R.id.tvToPay,true)
            }
            "2"->{//待配送
            }
            "3"->{//已收货
                holder.setVisible(R.id.tvGoStroll,true)
            }
            "4"->{//已过期
                holder.setVisible(R.id.tvGoStroll,true)
            }
            "5"->{//配送中
                holder.setVisible(R.id.tvAskDilivery,true)
            }

        }
        holder.getView<View>(R.id.tvToPay).setOnClickListener {
            btnClickLisner?.invoke(holder,t,position)
        }
        holder.getView<View>(R.id.tvAskDilivery).setOnClickListener {
            btnClickLisner?.invoke(holder,t,position)
        }
        holder.getView<View>(R.id.tvGoStroll).setOnClickListener {
            btnClickLisner?.invoke(holder,t,position)
        }


    }


}
