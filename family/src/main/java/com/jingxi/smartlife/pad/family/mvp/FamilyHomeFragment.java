package com.jingxi.smartlife.pad.family.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jingxi.smartlife.pad.family.R;
import com.wujia.lib.widget.VerticalTabBar;
import com.wujia.lib.widget.VerticalTabItem;
import com.wujia.lib_common.base.BaseFragment;
import com.wujia.lib_common.utils.LogUtil;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12 20:06
 * description ：智能家居 home
 */
public class FamilyHomeFragment extends BaseFragment {
    private VerticalTabBar mTabBar;
    private SupportFragment[] mFragments = new SupportFragment[6];

    public FamilyHomeFragment() {

    }

    public static FamilyHomeFragment newInstance() {
        FamilyHomeFragment fragment = new FamilyHomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        LogUtil.i("FamilyFragment getLayoutId");

        return R.layout.fragment_tab_home;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        // 懒加载
        // 同级Fragment场景、ViewPager场景均适用
        LogUtil.i("FamilyFragment onLazyInitView");
        mTabBar = $(R.id.tab_home_tab_bar);

        SupportFragment firstFragment = findFragment(AllFragment.class);
        if (firstFragment == null) {
            mFragments[0] = AllFragment.newInstance();
            loadRootFragment(R.id.tab_content_container, mFragments[0]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用
            mFragments[0] = firstFragment;
        }

        mTabBar.addItem(new VerticalTabItem(mActivity, R.mipmap.icon_smart_leftnav_all_default, R.mipmap.icon_smart_leftnav_all_selected, R.string.home))
                .addItem(new VerticalTabItem(mActivity, R.mipmap.icon_smart_leftnav_living_default, R.mipmap.icon_smart_leftnav_living_selected, R.string.drawing_room))
                .addItem(new VerticalTabItem(mActivity, R.mipmap.icon_smart_leftnav_bed2_default, R.mipmap.icon_smart_leftnav_bed2_selected, R.string.master_room))
                .addItem(new VerticalTabItem(mActivity, R.mipmap.icon_smart_leftnav_bed2_default, R.mipmap.icon_smart_leftnav_bed2_selected, R.string.second_room))
                .addItem(new VerticalTabItem(mActivity, R.mipmap.icon_smart_leftnav_kitchen_default, R.mipmap.icon_smart_leftnav_kitchen_selected, R.string.kitchen))
                .addItem(new VerticalTabItem(mActivity, R.mipmap.icon_smart_leftnav_bath_default, R.mipmap.icon_smart_leftnav_bath_selected, R.string.washroom));


        mTabBar.setOnTabSelectedListener(new VerticalTabBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[0], mFragments[prePosition]);
            }
        });
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
