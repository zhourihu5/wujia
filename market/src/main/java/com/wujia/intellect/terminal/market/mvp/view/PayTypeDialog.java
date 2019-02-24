package com.wujia.intellect.terminal.market.mvp.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.wujia.intellect.terminal.market.R;


/**
 * Created by shenbingkai on 19/1/25 下午5:48
 * Email shenbingkai@163.com
 * 支付方式dialog
 */
public class PayTypeDialog extends Dialog implements View.OnClickListener {
    private PayListener listener;

    public PayTypeDialog(Context context) {
        super(context);
        //初始化布局
        setContentView(R.layout.dialog_pay_type_layout);

        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.btn1) {

        }
        if (null != listener)
            listener.choosePayType(0);
        dismiss();

    }

    public void setListener(PayListener listener) {
        this.listener = listener;
    }

    public interface PayListener {
        void choosePayType(int type);
    }
}
