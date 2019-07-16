package com.jingxi.smartlife.pad.mvp.home.view

import android.content.Context
import android.text.TextUtils
import android.view.View
import com.jingxi.smartlife.pad.R
import com.jingxi.smartlife.pad.mvp.home.adapter.HomeInviteAdapter
import com.jingxi.smartlife.pad.mvp.home.data.HomeUserInfoBean
import com.wujia.businesslib.HookUtil
import com.wujia.businesslib.dialog.CommDialog
import com.wujia.businesslib.listener.OnInputDialogListener
import com.wujia.lib.widget.util.ToastUtil
import com.wujia.lib_common.base.view.VerticallDecoration
import kotlinx.android.synthetic.main.dialog_add_member_layout.*

/**
 * Author: created by shenbingkai on 2019/2/11 14 48
 * Email:  shenbingkai@gamil.com
 * Description:
 */
class AddMemberDialog(context: Context, internal var datas: List<HomeUserInfoBean.DataBean.UserInfoListBean>) : CommDialog(context, R.style.dialogStyle) {
    override val layoutId: Int
        get() = R.layout.dialog_add_member_layout
    private var listener: OnInputDialogListener? = null


    init {
        rv_dialog_invite!!.adapter = HomeInviteAdapter(context, datas)
    }
    override fun init(context: Context) {

        rv_dialog_invite!!.addItemDecoration(VerticallDecoration(24))
        datas?.let {rv_dialog_invite!!.adapter = HomeInviteAdapter(context, datas)  }

        findViewById<View>(R.id.btn_send_invite).setOnClickListener(View.OnClickListener {
            if (null != listener) {
                val phone = dialog_input!!.text.toString().trim { it <= ' ' }
                if (TextUtils.isEmpty(phone))
                    return@OnClickListener
                if (phone.length != 11) {
                    ToastUtil.showShort(mContext, R.string.please_input_right_phone)
                    return@OnClickListener
                }
                dismiss()
                listener!!.dialogSureClick(phone)
            }
        })
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        HookUtil.fixInputMethodManagerLeak(mContext)
    }


    fun setListener(listener: OnInputDialogListener): AddMemberDialog {
        this.listener = listener
        return this
    }

}
