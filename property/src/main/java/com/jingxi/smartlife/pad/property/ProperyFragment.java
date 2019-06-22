package com.jingxi.smartlife.pad.property;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jingxi.smartlife.pad.property.mvp.ProperyHomeFragment;
import com.wujia.lib_common.base.BaseMainFragment;
import com.wujia.lib_common.utils.LogUtil;

/**
* author ：shenbingkai@163.com
* date ：2019-01-12 20:06
* description ：物业服务 home
*/
public class ProperyFragment extends BaseMainFragment {

    public ProperyFragment() {
    }

    public static ProperyFragment newInstance() {
        ProperyFragment fragment = new ProperyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        LogUtil.i("ProperyFragment getLayoutId");
        return R.layout.fragment_frame_layout;
    }

    @Override
    protected void initEventAndData() {

    }
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (findChildFragment(ProperyHomeFragment.class) == null) {
            loadRootFragment(R.id.fl_first_container, ProperyHomeFragment.newInstance(currentTab));
        }

    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        // 当对用户可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！
        LogUtil.i("ProperyFragment onSupportVisible");

    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        // 当对用户不可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！
        LogUtil.i("ProperyFragment onSupportInvisible");

    }
}
