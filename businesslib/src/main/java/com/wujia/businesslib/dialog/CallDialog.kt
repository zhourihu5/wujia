package com.wujia.businesslib.dialog

import android.app.Dialog
import android.content.Context
import android.view.View

import com.wujia.businesslib.R


/**
 * Created by shenbingkai on 19/1/25 下午5:48
 * Email shenbingkai@163.com
 */
class CallDialog(context: Context) : Dialog(context), View.OnClickListener {

    init {
        //初始化布局
        setContentView(R.layout.layout_dialog_call)
        setCanceledOnTouchOutside(false)
        setCancelable(false)

        findViewById<View>(R.id.btn1).setOnClickListener(this)
    }

    override fun onClick(v: View) {
        dismiss()
    }
}
