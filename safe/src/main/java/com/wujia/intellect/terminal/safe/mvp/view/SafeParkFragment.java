package com.wujia.intellect.terminal.safe.mvp.view;

import android.os.Bundle;

import com.wujia.intellect.terminal.safe.R;
import com.wujia.lib_common.base.BaseFragment;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-02
 * description ：
 */
public class SafeParkFragment extends BaseFragment {

    public SafeParkFragment() {
    }

    public static SafeParkFragment newInstance() {
        SafeParkFragment fragment = new SafeParkFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_safe_park;
    }
}
