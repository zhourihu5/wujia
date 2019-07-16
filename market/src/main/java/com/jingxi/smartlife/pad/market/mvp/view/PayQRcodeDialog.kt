package com.jingxi.smartlife.pad.market.mvp.view

import android.app.Dialog
import android.content.Context
import android.view.View

import com.jingxi.smartlife.pad.market.R


/**
 * Created by shenbingkai on 19/1/25 下午5:48
 * Email shenbingkai@163.com
 * 支付二维码dialog
 */
class PayQRcodeDialog(context: Context) : Dialog(context), View.OnClickListener {
    private var listener: PayListener? = null

    init {
        //初始化布局
        setContentView(R.layout.dialog_qrcode_layout)

        findViewById<View>(R.id.btn9).setOnClickListener(this)
    }

    override fun onClick(v: View) {

        val id = v.id
        if (id == R.id.btn1) {

        }
        if (null != listener)
            listener!!.choosePayType(0)
        dismiss()

    }

    fun setListener(listener: PayListener) {
        this.listener = listener
    }

    interface PayListener {
        fun choosePayType(type: Int)
    }
}
