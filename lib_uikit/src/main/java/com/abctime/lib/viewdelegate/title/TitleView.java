package com.abctime.lib.viewdelegate.title;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

/**
 * Created by KisenHuang on 2018/5/29.
 * 标题抽象类
 */

public abstract class TitleView implements ITitle {

    @Override
    public void setTitle(String text) {
        getTitleTextView().setText(text);
    }

    @Override
    public void setTitle(int resId) {
        getTitleTextView().setText(resId);
    }

    @Override
    public void setBackListener(View.OnClickListener listener) {
        getBackView().setOnClickListener(listener);
    }

    @NonNull
    protected abstract TextView getTitleTextView();

    @NonNull
    protected abstract View getBackView();

}
