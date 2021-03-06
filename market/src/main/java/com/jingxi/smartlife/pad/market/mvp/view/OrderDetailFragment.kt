package com.jingxi.smartlife.pad.market.mvp.view

import android.app.Dialog
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.jingxi.smartlife.pad.market.R
import com.jingxi.smartlife.pad.market.mvp.PromoteHomeFragment
import com.jingxi.smartlife.pad.market.mvp.data.OrderDetailVo
import com.jingxi.smartlife.pad.market.mvp.model.MarketModel
import com.wujia.businesslib.TitleFragment
import com.wujia.businesslib.data.ApiResponse
import com.wujia.businesslib.event.EventBusUtil
import com.wujia.businesslib.event.EventOrder
import com.wujia.businesslib.event.EventToGroupBuy
import com.wujia.lib.imageloader.ImageLoaderManager
import com.wujia.lib.widget.util.ToastUtil
import com.wujia.lib_common.data.network.SimpleRequestSubscriber
import com.wujia.lib_common.data.network.exception.ApiException
import com.wujia.lib_common.utils.DateUtil
import kotlinx.android.synthetic.main.fragment_order_detail.*
import java.util.*


/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12 20:06
 * description ：
 */
class OrderDetailFragment : TitleFragment() {
    override val layoutId: Int
        get() = R.layout.fragment_order_detail
    override val title: Int
        get() = R.string.order_detail

    var endDate: Date? = null
    private var handler: Handler? = null
    var status: String? = null
    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        val id = arguments?.getString(BUNDLE_KEY_ID, "")
        addSubscribe(MarketModel().getOrderDetail(id).subscribeWith(object : SimpleRequestSubscriber<ApiResponse<OrderDetailVo>>(this@OrderDetailFragment, ActionConfig(true, SHOWERRORMESSAGE)) {
            override fun onResponse(response: ApiResponse<OrderDetailVo>) {
                super.onResponse(response)
                val apiData = response.data!!

                tvMoneyToPay.text = "付款 ${apiData.realPrice}"
                tvMoneyToPay.setOnClickListener {
                    addSubscribe(
                            MarketModel().generateOrderDetailQrCode(apiData.id)
                                    .subscribeWith(
                                            object : SimpleRequestSubscriber<ApiResponse<String>>(
                                                    this@OrderDetailFragment, ActionConfig(true, SHOWERRORMESSAGE)) {
                                                override fun onResponse(response: ApiResponse<String>) {
                                                    showQrCodeDialog(response.data!!)
                                                }
                                            })
                    )


                }
                tvCancelOrder.setOnClickListener {
                    addSubscribe(MarketModel().cancelOrder(id).subscribeWith(object : SimpleRequestSubscriber<ApiResponse<Any>>(this@OrderDetailFragment, ActionConfig(true, SHOWERRORMESSAGE)) {
                        override fun onResponse(response: ApiResponse<Any>) {
                            ToastUtil.showShort(context, "订单已取消")
                            EventBusUtil.post(EventOrder())
                            pop()
                        }
                    }))
                }
                tvGoStroll.setOnClickListener {
                    (parentFragment?.parentFragment as PromoteHomeFragment).switchTab(0)
                    EventBusUtil.post(EventToGroupBuy())
                    pop()
                }
                tvConfirmReceive.setOnClickListener {
                    addSubscribe(MarketModel().rereiveOrder(id).subscribeWith(object : SimpleRequestSubscriber<ApiResponse<Any>>(this@OrderDetailFragment, ActionConfig(true, SHOWERRORMESSAGE)) {
                        override fun onResponse(response: ApiResponse<Any>) {
                            ToastUtil.showShort(context, "确认成功")
                            EventBusUtil.post(EventOrder())
                            pop()
                        }
                    }))
                }


                val ac = response.data!!.activity
                ImageLoaderManager.instance.loadImage(ac.cover, 0, ivCover)
                tvGoodsTitle.text = ac.title
                status = apiData.status


                tvConfirmReceive.visibility = View.GONE
                tvGoStroll.visibility = View.GONE
                tvMoneyToPay.visibility = View.GONE
                tvCancelOrder.visibility = View.GONE
                llDilivery.visibility = View.GONE

                apiData.deliveryPerson.let {
                    tvDeliveryPerson.setText("送货员：${it}")
                    llDilivery.visibility = View.VISIBLE
                }
                apiData.deliveryPhone.let {
                    tvLinkPhone.setText("送货员联系方式：${it}")
                }
                apiData.deliveryHour.let {
                    tvdeliveryHour.setText("送货周期：${it}h")
                }
                apiData.receiveDate.let {
                    tvdeliveryHour.setText("收货时间：${it}")
                }

                when (status) {
                    "1" -> {//待付款
                        tvOrderStatus.text = "待付款"

                        tvMoneyToPay.visibility = View.VISIBLE
                        tvCancelOrder.visibility = View.VISIBLE

                    }
                    "2" -> {//待配送
                        tvOrderStatus.text = "待收货"
                        tvConfirmReceive.visibility = View.VISIBLE
                    }
                    "3" -> {//已收货
                        tvOrderStatus.text = "已收货"
                        tvTimeToEnd.text = "您的商品已收到～"


                        tvGoStroll.visibility = View.VISIBLE
                    }
                    "4" -> {//已过期
                        tvOrderStatus.text = "已过期"
                        tvTimeToEnd.text = "您的商品已过期～"

                        tvTimeToEnd.setTextColor(resources.getColor(R.color.tv_status_expired))
                        tvOrderStatus.setTextColor(resources.getColor(R.color.tv_status_expired))
                        llOrderStatus.setBackgroundResource(R.drawable.order_status_expired_bg)

                        tvGoStroll.visibility = View.VISIBLE

                    }
                    "5" -> {//配送中
                        tvOrderStatus.text = "待收货"
                        tvConfirmReceive.visibility = View.VISIBLE

                    }

                }
                if (apiData.status == "1") {
                    endDate = DateUtil.getDate(apiData.payEndDate)
                } else if (apiData.status == "2" || apiData.status == "5") {
                    endDate = DateUtil.getDate(apiData.activity.endDate)
                    endDate!!.time = endDate!!.time + Integer.valueOf(apiData.activity.deliveryHour) * 3600 * 1000
                }
                initTimer()


                val priceArr = ac.price.split('.')
                tvPriceInt.text = priceArr[0]
                if (priceArr.size > 1) {
                    tvFloat.text = ".${priceArr[1]}"
                }
                tvPriceOld.text = "￥" + apiData.commodity.price
                tvPriceOld.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG


                tvOrderMoney.text = "订单金额：￥${apiData.realPrice}"
                tvCreateTime.text = "创建时间：${apiData.createDate}"
                tvOrderCode.text = "订单编号：${apiData.code}"
                tvAddr.text = "收货地址：${apiData.deliveryAddress}"

            }

        }))

    }
    fun showQrCodeDialog(qrCodeImg: String) {
        val dialog = Dialog(mActivity)
        val conv = LayoutInflater.from(mActivity).inflate(R.layout.dialog_wxapp_qrcode, null)

        val ivQrcode = conv.findViewById<ImageView>(R.id.ivQrCode)
        ImageLoaderManager.instance.loadImage(qrCodeImg,0,ivQrcode)


        dialog.setContentView(conv)
        val dialogWindow = dialog.window
        dialogWindow!!.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialogWindow.setGravity(Gravity.CENTER)
        dialog.show()
    }

    fun initTimer() {
        handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                setTimeRemain()
            }
        }
        (handler as Handler).sendEmptyMessage(0)
    }

    fun setTimeRemain() {
        if (endDate == null) {
            handler?.removeCallbacksAndMessages(null)
            return
        }
        val now = Date()
        val milli = endDate!!.time - now.time
        if (milli <= 0) {
            when (status) {
                "1" -> {
                    tvTimeToEnd.text = "待付款时间：00： 00：00 "
                }
                "2", "5" -> {//待收货
                    tvTimeToEnd.text = "剩余系统默认收货时间：00： 00：00 "
                }
            }

            handler?.removeCallbacksAndMessages(null)
            return
        }
        val hour = milli / 1000 / 3600
        val minute = milli % (3600 * 1000) / (60 * 1000)
        val second = milli % (1000 * 60) / 1000

        when (status) {
            "1" -> {
                tvTimeToEnd.text = "待付款时间：${formatNumber(hour)}：${formatNumber(minute)}：${formatNumber(second)}"
            }
            "2", "5" -> {//待收货
                tvTimeToEnd.text = "剩余系统默认收货时间：${formatNumber(hour)}：${formatNumber(minute)}：${formatNumber(second)} "
            }
        }


        handler?.sendEmptyMessageDelayed(0, 1000)
    }

    private fun formatNumber(n: Long): String {
        var t = ""
        if (n < 10) {
            t = "0"
        }
        return "${t}${n}"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler?.removeCallbacksAndMessages(null)
    }

    companion object {
        const val BUNDLE_KEY_ID = "id"

        fun newInstance(id: String): OrderDetailFragment {
            val fragment = OrderDetailFragment()
            val args = Bundle()
            args.putString(BUNDLE_KEY_ID, id)
            fragment.arguments = args
            return fragment
        }
    }

}

