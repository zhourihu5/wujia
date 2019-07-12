package com.wujia.businesslib.dialog;

import android.app.Dialog;
import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.wujia.lib.uikit.R;

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17 21:30
 * description ：
 */
public abstract class CommDialog extends Dialog {

    protected Context mContext;

    public CommDialog(@NonNull Context context) {
        super(context);
        set(context);
    }

    public CommDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        set(context);
    }

    private void set(Context context) {
        mContext = context;
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

    public int getBaseWidthPx() {
        return 60;
    }

    protected abstract void init(Context context);

    protected abstract int getLayoutId();
}
