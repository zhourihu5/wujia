package com.jingxi.smartlife.pad.market.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jingxi.smartlife.pad.market.mvp.view.AllServiceFragment;
import com.jingxi.smartlife.pad.market.mvp.view.FindServiceFragment;
import com.wujia.businesslib.Constants;
import com.jingxi.smartlife.pad.market.R;
import com.wujia.lib.widget.VerticalTabBar;
import com.wujia.lib.widget.VerticalTabItem;
import com.wujia.lib_common.base.TabFragment;
import com.wujia.lib_common.utils.LogUtil;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
public class MarketHomeFragment extends TabFragment {


    private VerticalTabBar mTabBar;
    private SupportFragment[] mFragments = new SupportFragment[6];

    public MarketHomeFragment() {

    }

    public static MarketHomeFragment newInstance(int pos) {
        MarketHomeFragment fragment = new MarketHomeFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.ARG_PARAM_1, pos);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        LogUtil.i("MarketHomeFragment getLayoutId");

        return R.layout.fragment_tab_home;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        // 懒加载
        // 同级Fragment场景、ViewPager场景均适用
        LogUtil.i("MarketHomeFragment onLazyInitView");
        mTabBar = $(R.id.tab_home_tab_bar);

        currentTab = getArguments().getInt(Constants.ARG_PARAM_1);

        if (currentTab >= mFragments.length) {
            currentTab = 0;
        }

        SupportFragment firstFragment = findFragment(AllServiceFragment.class);
        if (firstFragment == null) {
            mFragments[0] = AllServiceFragment.newInstance(AllServiceFragment.TYPE_MY);
            mFragments[1] = FindServiceFragment.newInstance();
//            mFragments[2] = GovServiceFragment.newInstance();
            mFragments[2] = AllServiceFragment.newInstance(AllServiceFragment.TYPE_GOV);
            mFragments[3] = AllServiceFragment.newInstance(AllServiceFragment.TYPE_ALL);

            loadMultipleRootFragment(R.id.tab_content_container, currentTab, mFragments[0], mFragments[1], mFragments[2], mFragments[3]);
        }
//        else {
//            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
//
//            // 这里我们需要拿到mFragments的引用
//            mFragments[0] = firstFragment;
//            mFragments[1] = findChildFragment(FindServiceFragment.class);
//            mFragments[2] = findChildFragment(GovServiceFragment.class);
//            mFragments[3] = findChildFragment(AllServiceFragment.class);
//
//        }

        mTabBar.addItem(new VerticalTabItem(mActivity, R.mipmap.icon_market_leftnav_my_default, R.mipmap.icon_market_leftnav_my_highlight, R.string.my_service))
                .addItem(new VerticalTabItem(mActivity, R.mipmap.icon_market_leftnav_find_default, R.mipmap.icon_market_leftnav_my_highlight, R.string.find_service))
                .addItem(new VerticalTabItem(mActivity, R.mipmap.icon_market_leftnav_government_default, R.mipmap.icon_market_leftnav_my_highlight, R.string.gov_service))
                .addItem(new VerticalTabItem(mActivity, R.mipmap.icon_market_leftnav_all_default, R.mipmap.icon_market_leftnav_my_highlight, R.string.all_service));

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
