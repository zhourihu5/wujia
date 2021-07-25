package com.wujia.businesslib.dialog

import android.app.Dialog
import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.TextView

import com.wujia.lib.uikit.R


class LoadingDialog : Dialog {
    private var tvTitle: TextView? = null
    private val title = "加载中..."
    private var cancelOnTouchOutside = true

    fun setCancelOnTouchOutside(cancelOnTouchOutside: Boolean) {
        this.cancelOnTouchOutside = cancelOnTouchOutside
    }

    constructor(context: Context) : super(context, R.style.dialog) {
        init()
    }

    private fun init() {
        //        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_loading_dialog)
        setCanceledOnTouchOutside(cancelOnTouchOutside)
        //        WindowManager.LayoutParams lp = getWindow().getAttributes();
        //        lp.dimAmount = 0.0f;
        //        getWindow().setAttributes(lp);
        //        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        //        setCancelable(true);
        tvTitle = findViewById(R.id.tv_title)
        if (TextUtils.isEmpty(title)) {
            tvTitle!!.visibility = View.GONE
        } else {
            tvTitle!!.text = title
            tvTitle!!.visibility = View.VISIBLE
        }
    }


    fun setTitle(title: String) {
        if (TextUtils.isEmpty(title)) {
            tvTitle!!.visibility = View.GONE
        } else {
            tvTitle!!.text = title
            tvTitle!!.visibility = View.VISIBLE
        }
    }
}
