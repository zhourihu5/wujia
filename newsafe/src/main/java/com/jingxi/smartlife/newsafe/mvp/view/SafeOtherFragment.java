package com.jingxi.smartlife.newsafe.mvp.view;

import android.os.Bundle;

import com.jingxi.smartlife.newsafe.R;
import com.wujia.lib_common.base.BaseFragment;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-02
 * description ：
 */
public class SafeOtherFragment extends BaseFragment {

    public SafeOtherFragment() {
    }

    public static SafeOtherFragment newInstance() {
        SafeOtherFragment fragment = new SafeOtherFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_safe_other;
    }

    @Override
    public void onLazyInitView(Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
}
