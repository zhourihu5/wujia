package com.wujia.businesslib.dialog

import android.app.Dialog
import android.content.Context
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.TextView

import com.wujia.businesslib.listener.OnInputDialogListener
import com.wujia.lib.uikit.R
import com.wujia.lib.widget.util.ToastUtil


/**
 * Created by shenbingkai on 19/1/25 下午5:48
 * Email shenbingkai@163.com
 * 带输入框的dialog
 */
class InputDialog(private val mContext: Context, title: String?, hint: String?, confim: String?) : Dialog(mContext), View.OnClickListener {
    private val inputEt: EditText
    private var listener: OnInputDialogListener? = null

    init {
        //初始化布局
        setContentView(R.layout.layout_dialog_input)
        //        Window dialogWindow = getWindow();
        //        dialogWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //        dialogWindow.setGravity(Gravity.CENTER);
        //        setCanceledOnTouchOutside(true);
        //        setCancelable(false);

        val titleTv = findViewById<TextView>(R.id.dialog_input_title)
        inputEt = findViewById(R.id.dialog_input)
        val confimBtn = findViewById<TextView>(R.id.dialog_sure)

        confimBtn.setOnClickListener(this)
        findViewById<View>(R.id.dialog_cancel).setOnClickListener(this)

        titleTv.text = title
        inputEt.hint = hint
        confimBtn.text = confim
    }//        super(context, R.style.NormalDialogBottomStyle);

    override fun onClick(v: View) {

        val phone = inputEt.text.toString().trim { it <= ' ' }
        if (R.id.dialog_sure == v.id) {
            if (null != listener) {
                if (TextUtils.isEmpty(phone))
                    return
                if (phone.length != 11) {
                    ToastUtil.showShort(mContext, R.string.please_input_right_phone)
                    return
                }
                listener!!.dialogSureClick(phone)
            }
        }

        dismiss()
    }

    private fun setListener(listener: OnInputDialogListener?) {
        this.listener = listener
    }


    class Builder {
        private var title: String? = null
        private var hint: String? = null
        private var confirm: String? = null
        private var listener: OnInputDialogListener? = null

        fun title(title: String): Builder {
            this.title = title
            return this
        }

        fun hint(hint: String): Builder {
            this.hint = hint
            return this
        }

        fun confirm(confirm: String): Builder {
            this.confirm = confirm
            return this
        }

        fun listener(listener: OnInputDialogListener): Builder {
            this.listener = listener
            return this
        }

        fun build(context: Context): InputDialog {
            val dialog = InputDialog(context, title, hint, confirm)
            dialog.setListener(listener)

            return dialog
        }
    }
}
