package com.wujia.businesslib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.wujia.lib.uikit.R;


public class LoadingDialog extends Dialog {
    private TextView tvTitle;
    private String title = "加载中...";
    private boolean cancelOnTouchOutside = true;

    public void setCancelOnTouchOutside(boolean cancelOnTouchOutside) {
        this.cancelOnTouchOutside = cancelOnTouchOutside;
    }

    public LoadingDialog(@NonNull Context context) {
        super(context, R.style.dialog);
        init();
    }
    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    private void init() {
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_loading_dialog);
        setCanceledOnTouchOutside(cancelOnTouchOutside);
//        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        lp.dimAmount = 0.0f;
//        getWindow().setAttributes(lp);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//        setCancelable(true);
        tvTitle = findViewById(R.id.tv_title);
        if (TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setText(title);
            tvTitle.setVisibility(View.VISIBLE);
        }
    }


    public void setTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setText(title);
            tvTitle.setVisibility(View.VISIBLE);
        }
    }
}
