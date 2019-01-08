package com.wujia.lib.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by xmren on 2018/5/29.
 */

public class AbcAlertDialog extends BaseDialog {
    public AbcAlertDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public float getLayoutWidth() {
        return 0;
    }

    @Override
    public int getLayoutPosition() {
        return 0;
    }

    @Override
    public int getAnimations() {
        return 0;
    }

    @Override
    public void initView(View dialogView) {

    }

    @Override
    public float getLayoutHeight() {
        return 0;
    }
}
