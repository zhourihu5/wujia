package com.wujia.intellect.terminal.safe;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wujia.intellect.terminal.safe.mvp.SafeHomeFragment;
import com.wujia.intellect.terminal.safe.mvp.SafeOutsideFragment;
import com.wujia.lib_common.base.BaseFragment;
import com.wujia.lib_common.utils.LogUtil;

/**
* author ：shenbingkai@163.com
* date ：2019-01-12 20:06
* description ：可视安防 home
*/
public class SafeFragment extends BaseFragment {

    public SafeFragment() {
    }

    public static SafeFragment newInstance() {
        SafeFragment fragment = new SafeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        LogUtil.i("SafeFragment getLayoutId");
        return R.layout.fragment_safe;
    }

    @Override
    protected void initEventAndData() {

    }
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        // 懒加载
        // 同级Fragment场景、ViewPager场景均适用
        LogUtil.i("SafeFragment onLazyInitView");

        if (findChildFragment(SafeHomeFragment.class) == null) {
            loadRootFragment(R.id.fl_first_container, SafeHomeFragment.newInstance());
        }
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        // 当对用户可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！
        LogUtil.i("SafeFragment onSupportVisible");

    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        // 当对用户不可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！
        LogUtil.i("SafeFragment onSupportInvisible");

    }
}
