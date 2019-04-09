package com.jingxi.smartlife.pad.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jingxi.smartlife.pad.R;
import com.jingxi.smartlife.pad.mvp.setting.view.SettingHomeFragment;
import com.wujia.lib_common.base.BaseMainFragment;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12 20:06
 * description ：物业服务 home
 */
public class SettingFragment extends BaseMainFragment {



    public SettingFragment() {
    }

    public static SettingFragment newInstance() {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_setting;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        // 懒加载
        // 同级Fragment场景、ViewPager场景均适用
        if (findChildFragment(SettingHomeFragment.class) == null) {
            loadRootFragment(R.id.fl_first_container, SettingHomeFragment.newInstance());
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
