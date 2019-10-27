package com.jingxi.smartlife.newsafe.mvp.view;

import android.os.Bundle;

import com.jingxi.smartlife.newsafe.R;
import com.wujia.lib_common.base.BaseFragment;

import androidx.annotation.Nullable;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12 20:06
 * description ：可视安防 外机
 */
public class SafeOutsideFragment extends BaseFragment  {



    public SafeOutsideFragment() {
    }

    public static SafeOutsideFragment newInstance() {
        SafeOutsideFragment fragment = new SafeOutsideFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_safe_outside;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
}
