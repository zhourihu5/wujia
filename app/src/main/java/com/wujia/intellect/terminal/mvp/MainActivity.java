package com.wujia.intellect.terminal.mvp;

import android.os.Bundle;

import com.wujia.intellect.terminal.R;
import com.wujia.intellect.terminal.family.FamilyFragment;
import com.wujia.intellect.terminal.property.ProperyFragment;
import com.wujia.lib.widget.VerticalTabBar;
import com.wujia.lib.widget.VerticalTabItem;
import com.wujia.lib_common.base.BaseActivity;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends BaseActivity {

    @BindView(R.id.main_tab_bar)
    VerticalTabBar mTabBar;
    private SupportFragment[] mFragments = new SupportFragment[4];

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

        SupportFragment firstFragment = findFragment(FamilyFragment.class);
        if (firstFragment == null) {
            mFragments[0] = FamilyFragment.newInstance();
            mFragments[1] = ProperyFragment.newInstance();

            loadMultipleRootFragment(R.id.container, 0,
                    mFragments[0],
                    mFragments[1]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用
            mFragments[0] = firstFragment;
            mFragments[1] = findFragment(ProperyFragment.class);
        }

        mTabBar.addItem(new VerticalTabItem(this, R.mipmap.bg_logo_color, R.string.intelligent_home))
                .addItem(new VerticalTabItem(this, R.mipmap.ic_launcher, R.string.property_service));


        mTabBar.setOnTabSelectedListener(new VerticalTabBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);
            }
        });
    }
}
