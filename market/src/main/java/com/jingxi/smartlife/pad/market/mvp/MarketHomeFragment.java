package com.jingxi.smartlife.pad.market.mvp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jingxi.smartlife.pad.market.R;
import com.jingxi.smartlife.pad.market.mvp.view.AllServiceFragment;
import com.jingxi.smartlife.pad.market.mvp.view.FindServiceFragment;
import com.wujia.businesslib.TabFragment;
import com.wujia.lib.widget.VerticalTabBar;
import com.wujia.lib.widget.VerticalTabItem;
import com.wujia.lib_common.utils.LogUtil;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
public class MarketHomeFragment extends TabFragment {


    private SupportFragment[] mFragments = new SupportFragment[2];

    public MarketHomeFragment() {

    }

    public static MarketHomeFragment newInstance(int pos) {
        MarketHomeFragment fragment = new MarketHomeFragment();
        fragment.currentTab=pos;
//        Bundle args = new Bundle();
//        args.putInt(Constants.ARG_PARAM_1, pos);
//        fragment.setArguments(args);
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
        mTabBar.addItem(new VerticalTabItem(mActivity, R.mipmap.icon_market_leftnav_my_default, R.mipmap.icon_market_leftnav_my_highlight, R.string.my_service))
                .addItem(new VerticalTabItem(mActivity, R.mipmap.icon_market_leftnav_find_default, R.mipmap.icon_market_leftnav_my_highlight, R.string.find_service))
                .addItem(new VerticalTabItem(mActivity, R.mipmap.icon_market_leftnav_government_default, R.mipmap.icon_market_leftnav_my_highlight, R.string.gov_service))
                .addItem(new VerticalTabItem(mActivity, R.mipmap.icon_market_leftnav_all_default, R.mipmap.icon_market_leftnav_my_highlight, R.string.all_service));

        if (currentTab >= mTabBar.getChildCount()) {
            currentTab = 0;
        }
        SupportFragment firstFragment = findFragment(AllServiceFragment.class);
        if (firstFragment == null) {
            String type = getServiceType(currentTab);
            mFragments[0] = AllServiceFragment.newInstance(type);
            mFragments[1] = FindServiceFragment.newInstance();
//            mFragments[2] = GovServiceFragment.newInstance();
//            mFragments[2] = AllServiceFragment.newInstance(AllServiceFragment.TYPE_GOV);
//            mFragments[3] = AllServiceFragment.newInstance(AllServiceFragment.TYPE_ALL);
            int fPosition=currentTab%2;
//            setFragmentType(currentTab);
            loadMultipleRootFragment(R.id.tab_content_container, fPosition, mFragments[0], mFragments[1]);
        }
        else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用
            mFragments[0] = firstFragment;
            mFragments[1] = findChildFragment(FindServiceFragment.class);
//            setFragmentType(currentTab);
        }
        LogUtil.i("markethomefragment,currentTab=="+currentTab);
        mTabBar.setOnTabSelectedListener(new VerticalTabBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                currentTab=position;
                if(position==1){
                    showHideFragment(mFragments[1], mFragments[0]);
                }else {
                    setFragmentType(position);
                }
                if(prePosition==1){
                    showHideFragment(mFragments[0], mFragments[1]);
                }
                parentSwitchTab();
            }
        });
        switchTab(currentTab);

    }

    protected void setFragmentType(int position) {
        AllServiceFragment allServiceFragment= (AllServiceFragment) mFragments[0];
        String type = getServiceType(position);
        allServiceFragment.setType(type);
    }

    @NonNull
    protected String getServiceType(int position) {
        String type= AllServiceFragment.TYPE_MY;
        switch (position){
            case 0:
                type=AllServiceFragment.TYPE_MY;
                break;
            case 2:
                type=AllServiceFragment.TYPE_GOV;
                break;
            case 3:
                type=AllServiceFragment.TYPE_ALL;
                break;
        }
        return type;
    }

//    @Override
//    public void onSupportVisible() {
//        super.onSupportVisible();
//        if (currentTab > 0) {
//            mTabBar.getChildAt(currentTab).performClick();
//        }
//    }

}
