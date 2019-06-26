package com.jingxi.smartlife.pad.mvp.home.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jingxi.smartlife.pad.R;
import com.wujia.businesslib.data.MsgDto;

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
public class MessageDialog extends Dialog {

    public interface MsgReadCallBack {
        void updateMsgReadStatus(MsgDto.ContentBean msg);
    }

    MsgReadCallBack msgReadCallBack;

    public MessageDialog(@NonNull Context context, final MsgDto.ContentBean message) {
        super(context);
//        mContext = context;
        setContentView(R.layout.dialog_msg_layout);
        TextView btnCall = findViewById(R.id.btn1);
        TextView btnKnow = findViewById(R.id.btn2);

        TextView tvType = findViewById(R.id.tv1);
        TextView tvTitle = findViewById(R.id.tv2);
        TextView tvTime = findViewById(R.id.tv3);
        TextView tvDesc = findViewById(R.id.tv4);
        ImageView icon = findViewById(R.id.img1);

        if (message.getType().equals(MsgDto.TYPE_NOTIFY)) {
//            icon.setImageResource(R.mipmap.ic_msg_label_neighbour);
            icon.setImageResource(R.mipmap.ic_msg_label_neighbour2);
        } else if (message.getType().equals(MsgDto.TYPE_PROPERTY)) {
//            icon.setImageResource(R.mipmap.ic_msg_label_serve);
            icon.setImageResource(R.mipmap.ic_msg_label_serve2);
        } else if (message.getType().equals(MsgDto.TYPE_SYSTEM)) {
            icon.setImageResource(R.mipmap.icon_msg_label_system);
        }

        tvType.setText(MsgDto.getTypeText(message));
        tvTitle.setText(message.getTitle());
//        tvTime.setText(DateUtil.formatMsgDate(message.createDate));
        tvTime.setText(message.getCreateDate());
        tvDesc.setText(message.getContent());

        if (message.getIsRead() == MsgDto.STATUS_UNREAD) {//未读
            btnKnow.setBackgroundResource(R.drawable.btn_rect_accent_select);
        } else {
            btnKnow.setBackgroundResource(R.drawable.btn_rect_no_can);
        }
        btnKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (message.getIsRead() == MsgDto.STATUS_UNREAD) {//未读
                    v.setBackgroundResource(R.drawable.btn_rect_no_can);

                    if (null != msgReadCallBack) {
                        msgReadCallBack.updateMsgReadStatus(message);
                    }
                }
                dismiss();
            }
        });

    }

    public MessageDialog setListener(MsgReadCallBack listener) {
        this.msgReadCallBack = listener;
        return this;
    }

//    @Override
//    public void onClick(View v) {
//        if (v.getId() == R.id.btn1) {
//            dismiss();
//            new CallDialog(mContext).show();
//        } else if (v.getId() == R.id.btn2) {
//            dismiss();
//        }
//    }
}
