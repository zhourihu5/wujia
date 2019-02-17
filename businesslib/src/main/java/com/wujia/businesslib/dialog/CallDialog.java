package com.wujia.businesslib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.wujia.businesslib.R;


/**
 * Created by shenbingkai on 19/1/25 下午5:48
 * Email shenbingkai@163.com
 */
public class CallDialog extends Dialog implements View.OnClickListener {

    public CallDialog(Context context) {
        super(context);
        //初始化布局
        setContentView(R.layout.layout_dialog_call);
        setCanceledOnTouchOutside(false);
        setCancelable(false);

        findViewById(R.id.btn1).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}
