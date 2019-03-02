package com.wujia.intellect.terminal.message.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wujia.intellect.terminal.message.R;
import com.wujia.intellect.terminal.message.mvp.view.AllMsgFragment;
import com.wujia.lib.widget.VerticalTabBar;
import com.wujia.lib.widget.VerticalTabItem;
import com.wujia.lib_common.base.BaseFragment;
import com.wujia.lib_common.utils.LogUtil;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
public class MessageHomeFragment extends BaseFragment {


    private VerticalTabBar mTabBar;
    private SupportFragment[] mFragments = new SupportFragment[6];

    public MessageHomeFragment() {

    }

    public static MessageHomeFragment newInstance() {
        MessageHomeFragment fragment = new MessageHomeFragment();
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

        SupportFragment firstFragment = findFragment(AllMsgFragment.class);
        if (firstFragment == null) {
            mFragments[0] = AllMsgFragment.newInstance();
            loadRootFragment(R.id.tab_content_container, mFragments[0]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用
            mFragments[0] = firstFragment;
        }

        mTabBar.addItem(new VerticalTabItem(mActivity, R.mipmap.icon_news_leftnav_all_default,R.mipmap.icon_news_leftnav_all_highlight, R.string.all_msg))
                .addItem(new VerticalTabItem(mActivity, R.mipmap.icon_news_leftnav_property_default,R.mipmap.icon_news_leftnav_property_highlight, R.string.wuye_notify))
                .addItem(new VerticalTabItem(mActivity, R.mipmap.icon_news_leftnav_community_default,R.mipmap.icon_news_leftnav_community_highlight, R.string.shequ_notify))
                .addItem(new VerticalTabItem(mActivity, R.mipmap.icon_news_leftnav_property_default, R.mipmap.icon_news_leftnav_property_highlight,R.string.app_notify));


        mTabBar.setOnTabSelectedListener(new VerticalTabBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[0], mFragments[prePosition]);
            }
        });
    }

}
