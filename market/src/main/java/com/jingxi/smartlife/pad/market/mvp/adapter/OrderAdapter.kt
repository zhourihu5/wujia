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
        tvAskDilivery.setOnClickListener {
            btnClickLisner?.invoke(holder,t,position)
        }

        ImageLoaderManager.instance.loadImage(t.activity.cover, 0, ivCover)
        tvPriceOld.setText("￥${t.commodity.price}")
        tvPriceOld.paint.flags= Paint.STRIKE_THRU_TEXT_FLAG

        tvName.setText(t.activity.title)
        val priceArr= t.activity.price.split('.')
        tvPriceInt.setText(priceArr[0])
        if(priceArr.size>1){
            tvFloat.setText(".${priceArr[1]}")
        }

        tvTimeToEnd.setText("")
        tvOrderStatus.setTextColor(0xBA9C77)
        tvPriceInt.setTextColor(0xC8AC88)
        tvFloat.setTextColor(0xC8AC88)
        tvName.setTextColor(0x333333)


        when(t.status){
            "1"->{//待付款
                tvToPay.visibility=View.VISIBLE
                val endDate = DateUtil.getDate(t.payEndDate)
                setTimeRemain(endDate,tvTimeToEnd)
                tvToptitle.setText("付款剩余时间：")
                tvOrderStatus.setText("待付款")
            }
            "2"->{//待配送
                tvToptitle.setText("配送小哥正在取货中…")
                tvOrderStatus.setText("待收货")
            }
            "3"->{//已收货
                tvGoStroll.visibility=View.VISIBLE
                tvToptitle.setText("收货时间：${t.receiveDate}")
                tvOrderStatus.setText("已收货")

            }
            "4"->{//已过期
                tvGoStroll.visibility=View.VISIBLE
                tvToptitle.setText("过期时间：${t.payEndDate}")
                tvOrderStatus.setText("已过期")
                tvOrderStatus.setTextColor(0x888888)
                tvPriceInt.setTextColor(0x888888)
                tvFloat.setTextColor(0x888888)
                tvName.setTextColor(0x888888)
            }
            "5"->{//配送中
                tvAskDilivery.visibility=View.VISIBLE
                tvToptitle.setText("送货人：${t.deliveryPerson}")
                tvOrderStatus.setText("待收货")
            }

        }

    }
    fun setTimeRemain(endDate:Date,tvTimeToEnd:TextView) {
        var now = Date();
        var milli = endDate!!.getTime() - now.getTime()
        if (milli <= 0) {
            tvTimeToEnd.setText("00： 00：00 ")
            return
        }
        var hour = milli / 1000 / 3600
        var minute = milli % (3600 * 1000) / (60 * 1000)
        var second = milli % (1000 * 60) / 1000

        tvTimeToEnd.setText("待付款时间：${formatNumber(hour)}：${formatNumber(minute)}：${formatNumber(second)}")


    }
    fun formatNumber(n: Long): String {
        var t = ""
        if (n < 10) {
            t = "0"
        }
        return "${t}${n}"
    }

}
