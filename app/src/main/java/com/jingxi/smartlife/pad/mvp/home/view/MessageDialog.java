package com.jingxi.smartlife.pad.mvp.home.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wujia.businesslib.DataBaseUtil;
import com.wujia.businesslib.data.DBMessage;
import com.wujia.businesslib.listener.OnDialogListener;
import com.jingxi.smartlife.pad.R;
import com.wujia.lib_common.utils.DateUtil;

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
public class MessageDialog extends Dialog {
    private Context mContext;
    private OnDialogListener listener;

    public MessageDialog(@NonNull Context context, final DBMessage message) {
        super(context);
        mContext = context;
        setContentView(R.layout.dialog_msg_layout);
        TextView btnCall = findViewById(R.id.btn1);
        TextView btnKnow = findViewById(R.id.btn2);

        TextView tvType = findViewById(R.id.tv1);
        TextView tvTitle = findViewById(R.id.tv2);
        TextView tvTime = findViewById(R.id.tv3);
        TextView tvDesc = findViewById(R.id.tv4);
        ImageView icon = findViewById(R.id.img1);

        if (message._type.equals(DBMessage.TYPE_NOTIFY)) {
            icon.setImageResource(R.mipmap.ic_msg_label_neighbour);
        } else if (message._type.equals(DBMessage.TYPE_PROPERTY)) {
            icon.setImageResource(R.mipmap.ic_msg_label_serve);
        }

        tvType.setText(message.typeText);
        tvTitle.setText(message.title);
        tvTime.setText(DateUtil.formatMsgDate(message.createDate));
        tvDesc.setText(message.pureText);

        if (message._read_state == 0) {//未读
            btnKnow.setBackgroundResource(R.drawable.btn_rect_accent_select);
        } else {
            btnKnow.setBackgroundResource(R.drawable.btn_rect_no_can);
        }
        btnKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (message._read_state == 0) {//未读
                    message._read_state = 1;
                    v.setBackgroundResource(R.drawable.btn_rect_no_can);
                    DataBaseUtil.update(message);
                    if (null != listener) {
                        listener.dialogSureClick();
                    }
                }
                dismiss();
            }
        });

    }

    public MessageDialog setListener(OnDialogListener listener) {
        this.listener = listener;
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
