package com.wujia.intellect.terminal.mvp.home.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;

import com.wujia.intellect.terminal.R;

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
public class MessageDialog extends Dialog {
    public MessageDialog(@NonNull Context context) {
        super(context);

        setContentView(R.layout.dialog_msg_layout);
    }
}
