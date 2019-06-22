package com.jingxi.smartlife.pad.mvp.home;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jingxi.smartlife.pad.R;
import com.wujia.lib_common.base.BaseMainFragment;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12 20:06
 * description ： home
 */
public class HomeFragment extends BaseMainFragment {


    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_frame_layout;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        // 懒加载
        // 同级Fragment场景、ViewPager场景均适用
        if (findChildFragment(HomeHomeFragment.class) == null) {
            loadRootFragment(R.id.fl_first_container, HomeHomeFragment.newInstance());
        }
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
