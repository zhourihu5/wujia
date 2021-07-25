package com.wujia.businesslib.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout

import com.wujia.lib.uikit.R

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17 21:30
 * description ：
 */
abstract class CommDialog : Dialog {

    protected lateinit var mContext: Context

    val baseWidthPx: Int
        get() = 60

    protected abstract val layoutId: Int

    constructor(context: Context, themeResId: Int) : super(context, themeResId) {
        set(context)
    }

    private fun set(context: Context) {
        mContext = context
        setContentView(R.layout.layout_comm_dialog)
        val cont = findViewById<FrameLayout>(R.id.dialog_comm_container)
        cont.addView(LayoutInflater.from(context).inflate(layoutId, null))

        findViewById<View>(R.id.dialog_comm_close_btn).setOnClickListener { dismiss() }
        init(context)
    }

    protected abstract fun init(context: Context)
}
