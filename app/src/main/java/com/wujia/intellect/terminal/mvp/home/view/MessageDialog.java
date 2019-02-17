package com.wujia.intellect.terminal.mvp.home.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.wujia.businesslib.dialog.CallDialog;
import com.wujia.intellect.terminal.R;

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
public class MessageDialog extends Dialog implements View.OnClickListener {
    private Context mContext;

    public MessageDialog(@NonNull Context context) {
        super(context);
        mContext = context;
        setContentView(R.layout.dialog_msg_layout);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn1) {
            dismiss();
            new CallDialog(mContext).show();
        } else if (v.getId() == R.id.btn2) {
            dismiss();
        }
    }
}
