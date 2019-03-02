package com.wujia.intellect.terminal.property.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wujia.intellect.terminal.property.R;
import com.wujia.intellect.terminal.property.mvp.view.FixFragment;
import com.wujia.intellect.terminal.property.mvp.view.TelFragment;
import com.wujia.lib.widget.VerticalTabBar;
import com.wujia.lib.widget.VerticalTabItem;
import com.wujia.lib_common.base.BaseFragment;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
public class ProperyHomeFragment extends BaseFragment {


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

        SupportFragment firstFragment = findFragment(FixFragment.class);
        if (firstFragment == null) {
            mFragments[0] = FixFragment.newInstance();
            mFragments[1] = TelFragment.newInstance();
            loadMultipleRootFragment(R.id.tab_content_container, 0, mFragments[0], mFragments[1]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用
            mFragments[0] = firstFragment;
            mFragments[1] = TelFragment.newInstance();
        }

        mTabBar.addItem(new VerticalTabItem(mActivity, R.mipmap.icon_serve_leftnav_phone_default, R.mipmap.icon_serve_leftnav_phone_highlight, R.string.propery_report_fix))
                .addItem(new VerticalTabItem(mActivity, R.mipmap.icon_serve_leftnav_service_default, R.mipmap.icon_serve_leftnav_service_selected, R.string.tel_select));


        mTabBar.setOnTabSelectedListener(new VerticalTabBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);
            }
        });
    }

}
