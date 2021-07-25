package com.jingxi.smartlife.pad.market.mvp.adapter

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.jingxi.smartlife.pad.market.R
import com.jingxi.smartlife.pad.market.mvp.data.OrderItemVo
import com.wujia.lib.imageloader.ImageLoaderManager
import com.wujia.lib_common.base.baseadapter.CommonAdapter
import com.wujia.lib_common.base.baseadapter.base.ViewHolder
import com.wujia.lib_common.utils.DateUtil
import java.util.*

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
class OrderAdapter(context: Context, datas: List<OrderItemVo>) : CommonAdapter<OrderItemVo>(context, R.layout.item_order, datas) {

    var btnClickLisner:((holder: ViewHolder, t: OrderItemVo, position: Int)->Unit)?=null
    override fun convert(holder: ViewHolder, t: OrderItemVo, position: Int) {




        val ivCover = holder.getView<ImageView>(R.id.ivCover)
        val tvOrderStatus=holder.getView<TextView>(R.id.tvOrderStatus)
        val tvToptitle=holder.getView<TextView>(R.id.tvToptitle)
        val tvTimeToEnd=holder.getView<TextView>(R.id.tvTimeToEnd)
        val tvPriceOld=holder.getView<TextView>(R.id.tvPriceOld)
        val tvMoney=holder.getView<TextView>(R.id.tvMoney)
        val tvName=holder.getView<TextView>(R.id.tvName)
        val tvPriceInt=holder.getView<TextView>(R.id.tvPriceInt)
        val tvFloat=holder.getView<TextView>(R.id.tvFloat)
        val tvToPay=holder.getView<TextView>(R.id.tvToPay)
        val tvGoStroll=holder.getView<TextView>(R.id.tvGoStroll)
        val tvAskDilivery=holder.getView<TextView>(R.id.tvAskDilivery)

        tvToPay.visibility=View.GONE
        tvGoStroll.visibility=View.GONE
        tvAskDilivery.visibility=View.GONE

        tvToPay.setOnClickListener {
            btnClickLisner?.invoke(holder,t,position)
        }
        tvGoStroll.setOnClickListener {
            btnClickLisner?.invoke(holder,t,position)
        }
//        tvAskDilivery.setOnClickListener {
//            btnClickLisner?.invoke(holder,t,position)
//        }

        tvName.text = t.activity.title
        ImageLoaderManager.instance.loadImage(t.activity.cover, 0, ivCover)

//        tvPriceOld.setText("￥${t.commodity.price}")
//        tvPriceOld.paint.flags= Paint.STRIKE_THRU_TEXT_FLAG
//
//        val priceArr= t.activity.price.split('.')
//        tvPriceInt.setText(priceArr[0])
//        if(priceArr.size>1){
//            tvFloat.setText(".${priceArr[1]}")
//        }
        tvMoney.text = "￥${t.realPrice}"

        tvTimeToEnd.text = ""
        tvOrderStatus.setTextColor(Color.parseColor("#BA9C77"))
        tvMoney.setTextColor(Color.parseColor("#C8AC88"))
        tvPriceInt.setTextColor(Color.parseColor("#C8AC88"))
        tvFloat.setTextColor(Color.parseColor("#C8AC88"))
        tvName.setTextColor(Color.parseColor("#333333"))


        when(t.status){
            "1"->{//待付款
                tvToPay.visibility=View.VISIBLE
                val endDate = DateUtil.getDate(t.payEndDate)
                setTimeRemain(endDate,tvTimeToEnd)
                tvToptitle.text = "付款剩余时间："
                tvOrderStatus.text = "待付款"
            }
            "2"->{//待配送
                tvToptitle.text = "配送小哥正在取货中…"
                tvOrderStatus.text = "待收货"
            }
            "3"->{//已收货
                tvGoStroll.visibility=View.VISIBLE
                tvToptitle.text = "收货时间：${t.receiveDate}"
                tvOrderStatus.text = "已收货"

            }
            "4"->{//已过期
                tvGoStroll.visibility=View.VISIBLE
                tvToptitle.text = "过期时间：${t.payEndDate}"
                tvOrderStatus.text = "已过期"
                tvOrderStatus.setTextColor(Color.parseColor("#888888"))
                tvMoney.setTextColor(Color.parseColor("#888888"))
                tvPriceInt.setTextColor(Color.parseColor("#888888"))
                tvFloat.setTextColor(Color.parseColor("#888888"))
                tvName.setTextColor(Color.parseColor("#888888"))
            }
            "5"->{//配送中
                tvAskDilivery.visibility=View.VISIBLE
                tvToptitle.text = "送货人：${t.deliveryPerson}"
                tvOrderStatus.text = "待收货"
                tvAskDilivery.text = "送货人联系电话：${t.deliveryPhone}"
            }

        }

    }
    private fun setTimeRemain(endDate:Date, tvTimeToEnd:TextView) {
        val now = Date()
        val milli = endDate.time - now.time
        if (milli <= 0) {
            tvTimeToEnd.text = "00： 00：00 "
            return
        }
        val hour = milli / 1000 / 3600
        val minute = milli % (3600 * 1000) / (60 * 1000)
        val second = milli % (1000 * 60) / 1000

        tvTimeToEnd.text = "待付款时间：${formatNumber(hour)}：${formatNumber(minute)}：${formatNumber(second)}"


    }
    private fun formatNumber(n: Long): String {
        var t = ""
        if (n < 10) {
            t = "0"
        }
        return "${t}${n}"
    }

}
