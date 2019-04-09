package com.jingxi.smartlife.pad.market.mvp.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.jingxi.smartlife.pad.market.R;


/**
 * Created by shenbingkai on 19/1/25 下午5:48
 * Email shenbingkai@163.com
 * 支付二维码dialog
 */
public class PayQRcodeDialog extends Dialog implements View.OnClickListener {
    private PayListener listener;

    public PayQRcodeDialog(Context context) {
        super(context);
        //初始化布局
        setContentView(R.layout.dialog_qrcode_layout);

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
