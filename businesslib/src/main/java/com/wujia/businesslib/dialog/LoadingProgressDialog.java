package com.wujia.businesslib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.wujia.lib.uikit.R;


public class LoadingProgressDialog extends Dialog {
    private TextView tvTitle;
    String title = "下载中,请勿离开...";

    public LoadingProgressDialog(@NonNull Context context) {
        super(context, R.style.dialog);
        init();
    }

    public LoadingProgressDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    private void init() {
        setContentView(R.layout.layout_loading_dialog);
        setCanceledOnTouchOutside(false);
        tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(title);
        tvTitle.setVisibility(View.VISIBLE);
    }

    public void updateProgress(int per) {
        tvTitle.setText(title + per + "%");
    }

    public void setTvTitle(String title) {
        tvTitle.setText(title);
    }

}
