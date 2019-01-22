package com.wujia.intellect.terminal.mvp;

import android.os.Bundle;

import com.wujia.intellect.terminal.R;
import com.wujia.intellect.terminal.family.FamilyFragment;
import com.wujia.intellect.terminal.market.MarketFragment;
import com.wujia.intellect.terminal.message.MessageFragment;
import com.wujia.intellect.terminal.neighbor.NeighborFragment;
import com.wujia.intellect.terminal.property.ProperyFragment;
import com.wujia.intellect.terminal.safe.SafeFragment;
import com.wujia.lib.widget.VerticalTabBar;
import com.wujia.lib.widget.VerticalTabItem;
import com.wujia.lib_common.base.BaseActivity;
import com.wujia.lib_common.utils.LogUtil;
import com.wujia.lib_common.utils.ScreenUtil;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends BaseActivity {

    @BindView(R.id.main_tab_bar)
    VerticalTabBar mTabBar;
    private SupportFragment[] mFragments = new SupportFragment[8];

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

//        LogUtil.i("ScreenUtil.getDialogWidth()  "+ScreenUtil.getDialogWidth());
//        LogUtil.i("ScreenUtil.getLandscapeHeight()  "+ScreenUtil.getLandscapeHeight());
//        LogUtil.i("ScreenUtil.getLandscapeWidth()  "+ScreenUtil.getLandscapeWidth());
//        LogUtil.i("ScreenUtil.getDisplayHeight()  "+ScreenUtil.getDisplayHeight());
//        LogUtil.i("ScreenUtil.getDialogWidth()  "+ScreenUtil.getDialogWidth());
//        LogUtil.i("ScreenUtil.density()  "+ScreenUtil.density);
//        LogUtil.i("ScreenUtil.densityDpi()  "+ScreenUtil.densityDpi);
//        LogUtil.i("ScreenUtil.scaleDensity()  "+ScreenUtil.scaleDensity);

        for (int i = 400; i <= 500; i++) {
            LogUtil.i("<dimen name=\"px"+i+"\">"+Math.round(i/1.5)+"dp</dimen>");
//            LogUtil.i("<dimen name=\"text"+i+"\">"+Math.round(i/1.5)+"sp</dimen>");
        }

        SupportFragment firstFragment = findFragment(HomeFragment.class);
        if (firstFragment == null) {
            mFragments[0] = HomeFragment.newInstance();
            mFragments[1] = SafeFragment.newInstance();
            mFragments[2] = FamilyFragment.newInstance();
            mFragments[3] = ProperyFragment.newInstance();
            mFragments[4] = MessageFragment.newInstance();
            mFragments[5] = MarketFragment.newInstance();
            mFragments[6] = NeighborFragment.newInstance();
            mFragments[7] = SettingFragment.newInstance();

            loadMultipleRootFragment(R.id.container, 0,
                    mFragments[0],
                    mFragments[1],
                    mFragments[2],
                    mFragments[3],
                    mFragments[4],
                    mFragments[5],
                    mFragments[6],
                    mFragments[7]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用
            mFragments[0] = firstFragment;
            mFragments[1] = findFragment(SafeFragment.class);
            mFragments[2] = findFragment(FamilyFragment.class);
            mFragments[3] = findFragment(ProperyFragment.class);
            mFragments[4] = findFragment(MessageFragment.class);
            mFragments[5] = findFragment(MarketFragment.class);
            mFragments[6] = findFragment(NeighborFragment.class);
            mFragments[7] = findFragment(SettingFragment.class);
        }

        mTabBar.addItem(new VerticalTabItem(this, R.mipmap.icon_leftnav_home_default, R.string.home))
                .addItem(new VerticalTabItem(this, R.mipmap.icon_leftnav_safe_default, R.string.visual_security))
                .addItem(new VerticalTabItem(this, R.mipmap.icon_leftnav_smart_default, R.string.intelligent_home))
                .addItem(new VerticalTabItem(this, R.mipmap.icon_leftnav_serve_default, R.string.property_service))
                .addItem(new VerticalTabItem(this, R.mipmap.icon_leftnav_news_deafult, R.string.message_notify))
                .addItem(new VerticalTabItem(this, R.mipmap.icon_leftnav_market_default, R.string.market_service))
                .addItem(new VerticalTabItem(this, R.mipmap.icon_leftnav_neighbor_default, R.string.neighbor))
                .addItem(new VerticalTabItem(this, R.mipmap.icon_leftnav_set_default, R.string.setting));


        mTabBar.setOnTabSelectedListener(new VerticalTabBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);
            }
        });
    }
}
