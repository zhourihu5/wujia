package com.wujia.intellect.terminal.safe.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wujia.intellect.terminal.safe.R;
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
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
    }
}
