package com.jingxi.smartlife.pad.mvp.home.view

import android.app.Dialog
import android.content.Context
import com.jingxi.smartlife.pad.R
import com.wujia.businesslib.data.MsgDto
import kotlinx.android.synthetic.main.dialog_msg_layout.*

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
class MessageDialog(context: Context, message: MsgDto.ContentBean) : Dialog(context) {

    internal var msgReadCallBack: MsgReadCallBack? = null
    internal var listner: ((msg: MsgDto.ContentBean)->Unit)? = null

    interface MsgReadCallBack {
        fun updateMsgReadStatus(msg: MsgDto.ContentBean)
    }

    init {
        setContentView(R.layout.dialog_msg_layout)

        if (message.type == MsgDto.TYPE_NOTIFY) {
            img1.setImageResource(R.mipmap.ic_msg_label_neighbour2)
        } else if (message.type == MsgDto.TYPE_PROPERTY) {
            img1.setImageResource(R.mipmap.ic_msg_label_serve2)
        } else if (message.type == MsgDto.TYPE_SYSTEM) {
            img1.setImageResource(R.mipmap.icon_msg_label_system)
        }

        tvType.text = MsgDto.getTypeText(message)
        tvTitle.text = message.title
        //        tvTime.setText(DateUtil.formatMsgDate(message.createDate));
        tvTime.text = message.createDate
        tvDesc.setText(message.content)

        if (message.isRead == MsgDto.STATUS_UNREAD) {//未读
            btnKnow.setBackgroundResource(R.drawable.btn_rect_accent_select)
        } else {
            btnKnow.setBackgroundResource(R.drawable.btn_rect_no_can)
        }
        btnKnow.setOnClickListener { v ->
            if (message.isRead == MsgDto.STATUS_UNREAD) {//未读
                v.setBackgroundResource(R.drawable.btn_rect_no_can)

                if (null != msgReadCallBack) {
                    msgReadCallBack!!.updateMsgReadStatus(message)
                }
                listner?.invoke(message)
            }
            dismiss()
        }

    }

    fun setListener(listener: MsgReadCallBack): MessageDialog {
        this.msgReadCallBack = listener
        return this
    }
    fun setListener(listener: ((msg: MsgDto.ContentBean)->Unit)?): MessageDialog {
        this.listner = listener
        return this
    }
}
