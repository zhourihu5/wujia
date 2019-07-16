package com.wujia.businesslib.dialog

import android.app.Dialog
import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.wujia.lib.uikit.R


/**
 * Created by shenbingkai on 19/1/25 下午5:48
 * Email shenbingkai@163.com
 * 简单dialog
 */
class SimpleDialog(context: Context, title: String?, message: String?, confim: String?) : Dialog(context), View.OnClickListener {
    private var listener: (() -> Unit)? = null

    init {
        //初始化布局
        setContentView(R.layout.layout_dialog_simple)
        //        Window dialogWindow = getWindow();
        //        dialogWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //        dialogWindow.setGravity(Gravity.CENTER);
        //        setCanceledOnTouchOutside(true);
        //        setCancelable(false);

        val titleTv = findViewById<TextView>(R.id.tv1)
        val confimBtn = findViewById<TextView>(R.id.btn2)

        if (!TextUtils.isEmpty(message)) {
            val messageTv = findViewById<TextView>(R.id.tv2)
            messageTv.text = message
            messageTv.visibility = View.VISIBLE
        }

        confimBtn.setOnClickListener(this)
        findViewById<View>(R.id.btn1).setOnClickListener(this)

        if (!TextUtils.isEmpty(title)) {
            titleTv.text = title
        }
        if (!TextUtils.isEmpty(confim)) {
            confimBtn.text = confim
        }
    }//        super(context, R.style.NormalDialogBottomStyle);

    override fun onClick(v: View) {

        if (R.id.btn2 == v.id) {
            listener?.invoke()
        }

        dismiss()
    }

    private fun setListener(listener: (() -> Unit)?): SimpleDialog {
        this.listener = listener
        return this
    }


    class Builder {
        private var title: String? = null
        private var message: String? = null
        private var confirm: String? = null
        private var listener: (()->Unit)? = null

        fun title(title: String): Builder {
            this.title = title
            return this
        }

        fun message(message: String): Builder {
            this.message = message
            return this
        }

        fun confirm(confirm: String): Builder {
            this.confirm = confirm
            return this
        }

        fun listener(listener: (()->Unit)?): Builder {
            this.listener = listener
            return this
        }

        fun build(context: Context): SimpleDialog {
            val dialog = SimpleDialog(context, title, message, confirm)
            dialog.setListener(listener)

            return dialog
        }
    }
}
