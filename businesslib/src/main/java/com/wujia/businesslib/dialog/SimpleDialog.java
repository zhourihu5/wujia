package com.wujia.businesslib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.wujia.businesslib.listener.OnDialogListener;
import com.wujia.lib.uikit.R;


/**
 * Created by shenbingkai on 19/1/25 下午5:48
 * Email shenbingkai@163.com
 * 简单dialog
 */
public class SimpleDialog extends Dialog implements View.OnClickListener {
    private OnDialogListener listener;

    public SimpleDialog(Context context, String title, String message, String confim) {
//        super(context, R.style.NormalDialogBottomStyle);
        super(context);
        //初始化布局
        setContentView(R.layout.layout_dialog_simple);
//        Window dialogWindow = getWindow();
//        dialogWindow.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialogWindow.setGravity(Gravity.CENTER);
//        setCanceledOnTouchOutside(true);
//        setCancelable(false);

        TextView titleTv = findViewById(R.id.tv1);
        TextView confimBtn = findViewById(R.id.btn2);

        if (!TextUtils.isEmpty(message)) {
            TextView messageTv = findViewById(R.id.tv2);
            messageTv.setText(message);
            messageTv.setVisibility(View.VISIBLE);
        }

        confimBtn.setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);

        if (!TextUtils.isEmpty(title)) {
            titleTv.setText(title);
        }
        if (!TextUtils.isEmpty(confim)) {
            confimBtn.setText(confim);
        }
    }

    @Override
    public void onClick(View v) {

        if (R.id.btn2 == v.getId()) {
            if (null != listener) {
                listener.dialogSureClick();
            }
        }

        dismiss();
    }

    private SimpleDialog setListener(OnDialogListener listener) {
        this.listener = listener;
        return this;
    }


    public static class Builder {
        private String title;
        private String message;
        private String confirm;
        private OnDialogListener listener;

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder confirm(String confirm) {
            this.confirm = confirm;
            return this;
        }

        public Builder listener(OnDialogListener listener) {
            this.listener = listener;
            return this;
        }

        public SimpleDialog build(Context context) {
            SimpleDialog dialog = new SimpleDialog(context, title, message, confirm);
            dialog.setListener(listener);

            return dialog;
        }
    }
}
