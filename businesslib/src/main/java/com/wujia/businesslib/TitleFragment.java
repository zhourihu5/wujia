package com.wujia.businesslib;

import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.TextView;

import com.wujia.lib_common.base.BaseFragment;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-26
 * description ：
 */
public abstract class TitleFragment extends BaseFragment {
    protected TextView mTitleTv;
    protected TextView mBackBtn;


    @Override
    protected void initEventAndData() {
        super.initEventAndData();

        mTitleTv = $(R.id.layout_title_tv);
        mTitleTv.setText(getTitle());

    }


    public abstract @StringRes int getTitle();

    public void showBack() {
        mBackBtn = $(R.id.layout_back_btn);
        mBackBtn.setVisibility(View.VISIBLE);
        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TitleFragment.this.pop();
            }
        });
    }
}
