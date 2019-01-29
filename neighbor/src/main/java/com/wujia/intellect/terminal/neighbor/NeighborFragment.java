package com.wujia.intellect.terminal.neighbor;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wujia.lib_common.base.BaseFragment;

/**
* author ：shenbingkai@163.com
* date ：2019-01-12 20:06
* description ：邻里 home
*/
public class NeighborFragment extends BaseFragment {

    public NeighborFragment() {
    }

    public static NeighborFragment newInstance() {
        NeighborFragment fragment = new NeighborFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_neighbor;
    }

    @Override
    protected void initEventAndData() {

    }
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        // 懒加载
        // 同级Fragment场景、ViewPager场景均适用

    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        // 当对用户可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！

    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        // 当对用户不可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！

    }
}