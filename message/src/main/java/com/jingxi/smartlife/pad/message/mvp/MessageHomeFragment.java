package com.jingxi.smartlife.pad.message.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jingxi.smartlife.pad.message.mvp.view.AllMsgFragment;
import com.wujia.businesslib.data.DBMessage;
import com.jingxi.smartlife.pad.message.R;
import com.wujia.lib.widget.VerticalTabBar;
import com.wujia.lib.widget.VerticalTabItem;
import com.wujia.lib_common.base.BaseFragment;
import com.wujia.lib_common.base.TabFragment;
import com.wujia.lib_common.utils.LogUtil;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
public class MessageHomeFragment extends TabFragment {


    private VerticalTabBar mTabBar;
    private AllMsgFragment msgFragment;


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
            msgFragment = AllMsgFragment.newInstance();
            loadRootFragment(R.id.tab_content_container, msgFragment);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用
            msgFragment = (AllMsgFragment) firstFragment;
        }

        mTabBar.addItem(new VerticalTabItem(mActivity, R.mipmap.icon_news_leftnav_all_default, R.mipmap.icon_news_leftnav_all_highlight, R.string.all_msg))
                .addItem(new VerticalTabItem(mActivity, R.mipmap.icon_news_leftnav_property_default, R.mipmap.icon_news_leftnav_property_highlight, R.string.wuye_notify))
                .addItem(new VerticalTabItem(mActivity, R.mipmap.icon_news_leftnav_community_default, R.mipmap.icon_news_leftnav_community_highlight, R.string.shequ_notify))
                .addItem(new VerticalTabItem(mActivity, R.mipmap.icon_news_leftnav_system_default, R.mipmap.icon_news_leftnav_system_highlight,R.string.app_notify));

        mTabBar.setOnTabSelectedListener(new VerticalTabBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                String type = "";
                switch (position) {
                    case 1:
                        type = "1";
                        break;
                    case 2:
                        type ="2";
                        break;
                    case 3:
                        type="0";//系统消息
                        break;
                }
                msgFragment.setType(type);
            }
        });


    }

    @Override
    public void switchTab(int pos) {
        mTabBar.getChildAt(pos).performClick();
    }
}
