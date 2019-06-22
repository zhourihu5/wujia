package com.jingxi.smartlife.pad.family;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jingxi.smartlife.pad.family.mvp.FamilyHomeFragment;
import com.wujia.lib_common.base.BaseMainFragment;
import com.wujia.lib_common.utils.LogUtil;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12 20:06
 * description ：智能家居 home
 */
public class FamilyFragment extends BaseMainFragment {

    public FamilyFragment() {

    }

    public static FamilyFragment newInstance() {
        FamilyFragment fragment = new FamilyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        LogUtil.i("FamilyFragment getLayoutId");

        return R.layout.fragment_family;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        // 懒加载
        // 同级Fragment场景、ViewPager场景均适用
        LogUtil.i("FamilyFragment onLazyInitView");

        if (findChildFragment(FamilyHomeFragment.class) == null) {
            loadRootFragment(R.id.fl_first_container, FamilyHomeFragment.newInstance(currentTab));
        }
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        // 当对用户可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！
        LogUtil.i("FamilyFragment onSupportVisible");

    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        // 当对用户不可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！
        LogUtil.i("FamilyFragment onSupportInvisible");

    }
}
