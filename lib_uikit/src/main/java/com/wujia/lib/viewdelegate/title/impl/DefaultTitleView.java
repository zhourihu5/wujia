package com.wujia.lib.viewdelegate.title.impl;

import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wujia.lib.uikit.R;
import com.wujia.lib.viewdelegate.title.TitleView;

/**
 * Created by KisenHuang on 2018/5/29.
 * 默认标题布局实现
 */

public class DefaultTitleView extends TitleView {

    private View mBackView;
    private TextView mTextView;
    private FrameLayout mMenuLayout;

    @Override
    public int getLayoutId() {
        return R.layout.common_title_layout;
    }

    @Override
    public void inflateFinished(View title) {
        mBackView = title.findViewById(R.id.btn_nav_bar_button);
        mTextView = title.findViewById(R.id.tv_nav_bar_text_view);
        mMenuLayout = title.findViewById(R.id.tv_nav_bar_menu_layout);
    }

    @Override
    public void inflateRightMenu(View group) {
        mMenuLayout.addView(group,
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
    }

    @NonNull
    @Override
    protected TextView getTitleTextView() {
        return mTextView;
    }

    @NonNull
    @Override
    protected View getBackView() {
        return mBackView;
    }
}
