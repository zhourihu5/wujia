package com.wujia.businesslib.dialog

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.TextView

import com.wujia.lib.uikit.R


class LoadingProgressDialog : Dialog {
    private var tvTitle: TextView? = null
    internal var title = "下载中,请勿离开..."

    constructor(context: Context) : super(context, R.style.dialog) {
        init()
    }

    constructor(context: Context, themeResId: Int) : super(context, themeResId) {}

    private fun init() {
        setContentView(R.layout.layout_loading_dialog)
        setCanceledOnTouchOutside(false)
        tvTitle = findViewById(R.id.tv_title)
        tvTitle!!.text = title
        tvTitle!!.visibility = View.VISIBLE
    }

    fun updateProgress(per: Int) {
        tvTitle!!.text = "$title$per%"
    }

    fun setTvTitle(title: String) {
        tvTitle!!.text = title
    }

}
