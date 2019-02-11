package com.wujia.businesslib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wujia.lib.uikit.R;


public abstract class CommDialog extends Dialog {

    public CommDialog(@NonNull Context context) {
        super(context);
        set(context);
    }

    public CommDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        set(context);
    }

    private void set(Context context) {
        setContentView(R.layout.layout_comm_dialog);
        FrameLayout cont = findViewById(R.id.dialog_comm_container);
        cont.addView(LayoutInflater.from(context).inflate(getLayoutId(), null));

        findViewById(R.id.dialog_comm_close_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        init(context);
    }


    protected abstract void init(Context context);

    protected abstract int getLayoutId();
}
