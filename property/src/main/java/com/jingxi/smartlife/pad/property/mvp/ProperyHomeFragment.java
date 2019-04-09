package com.jingxi.smartlife.pad.property.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wujia.businesslib.Constants;
import com.jingxi.smartlife.pad.property.R;
import com.jingxi.smartlife.pad.property.mvp.view.SimpleFixFragment;
import com.jingxi.smartlife.pad.property.mvp.view.TelFragment;
import com.wujia.lib.widget.VerticalTabBar;
import com.wujia.lib.widget.VerticalTabItem;
import com.wujia.lib_common.base.TabFragment;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
public class ProperyHomeFragment extends TabFragment {


    private VerticalTabBar mTabBar;
    private SupportFragment[] mFragments = new SupportFragment[6];

    public ProperyHomeFragment() {

    }

    public static ProperyHomeFragment newInstance() {
        ProperyHomeFragment fragment = new ProperyHomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {

        return R.layout.fragment_tab_home;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mTabBar = $(R.id.tab_home_tab_bar);

        currentTab = getArguments().getInt(Constants.ARG_PARAM_1);

        if (currentTab >= mFragments.length) {
            currentTab = 0;
        }

        SupportFragment firstFragment = findFragment(SimpleFixFragment.class);
        if (firstFragment == null) {
            mFragments[0] = SimpleFixFragment.newInstance();
            mFragments[1] = TelFragment.newInstance();
            loadMultipleRootFragment(R.id.tab_content_container, currentTab, mFragments[0], mFragments[1]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用
            mFragments[0] = firstFragment;
            mFragments[1] = findChildFragment(TelFragment.class);
        }

        mTabBar.addItem(new VerticalTabItem(mActivity, R.mipmap.icon_serve_leftnav_service_default, R.mipmap.icon_serve_leftnav_service_selected, R.string.propery_report_fix))
                .addItem(new VerticalTabItem(mActivity, R.mipmap.icon_serve_leftnav_phone_default, R.mipmap.icon_serve_leftnav_phone_highlight, R.string.tel_select));

        mTabBar.setCurrentItem(currentTab);

        mTabBar.setOnTabSelectedListener(new VerticalTabBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);
            }
        });
    }

//    @Override
//    public void onSupportVisible() {
//        super.onSupportVisible();
//        if (currentTab > 0) {
//            mTabBar.getChildAt(currentTab).performClick();
//        }
//    }

    @Override
    public void switchTab(int pos) {
        mTabBar.getChildAt(pos).performClick();
    }
}
