package com.wujia.intellect.terminal.mvp;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.intercom.sdk.SecurityMessage;
import com.intercom.sdk.SmartHomeManager;
import com.jingxi.smartlife.pad.sdk.JXPadSdk;
import com.jingxi.smartlife.pad.sdk.doorAccess.DoorAccessManager;
import com.jingxi.smartlife.pad.sdk.doorAccess.base.DoorSecurityUtil;
import com.jingxi.smartlife.pad.sdk.doorAccess.base.ui.DoorAccessListUI;
import com.jingxi.smartlife.pad.sdk.doorAccess.base.ui.DoorAccessListener;
import com.jingxi.smartlife.pad.sdk.neighbor.ui.fragments.NeighborMainFragment;
import com.wujia.businesslib.base.DataManager;
import com.wujia.intellect.terminal.R;
import com.wujia.intellect.terminal.family.FamilyFragment;
import com.wujia.intellect.terminal.market.MarketFragment;
import com.wujia.intellect.terminal.message.MessageFragment;
import com.wujia.intellect.terminal.mvp.home.HomeFragment;
import com.wujia.intellect.terminal.mvp.home.HomeHomeFragment;
import com.wujia.intellect.terminal.neighbor.NeighborFragment;
import com.wujia.intellect.terminal.property.ProperyFragment;
import com.wujia.intellect.terminal.safe.SafeFragment;
import com.wujia.intellect.terminal.safe.mvp.view.VideoCallActivity;
import com.wujia.lib.widget.VerticalTabBar;
import com.wujia.lib.widget.VerticalTabItem;
import com.wujia.lib.widget.util.ToastUtil;
import com.wujia.lib_common.base.BaseActivity;
import com.wujia.lib_common.utils.LogUtil;
import com.wujia.lib_common.utils.ScreenUtil;

import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class MainActivity extends BaseActivity implements DoorAccessListener, DoorAccessListUI,DoorSecurityUtil.OnSecurityChangedListener {

    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;  //点击返回键时间

    VerticalTabBar mTabBar;
    FrameLayout mainCover;
    ImageView mArrow;
    private SupportFragment[] mFragments = new SupportFragment[8];
    private int tbHeight, itemHeight, arrowHeight, lastTop;
    private RelativeLayout.LayoutParams arrowLayoutParams;

    private DoorAccessManager manager;


    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData(Bundle savedInstanceState) {

        LogUtil.i("ScreenUtil.getDialogWidth()  " + ScreenUtil.getDialogWidth());
        LogUtil.i("ScreenUtil.getLandscapeHeight()  " + ScreenUtil.getLandscapeHeight());
        LogUtil.i("ScreenUtil.getLandscapeWidth()  " + ScreenUtil.getLandscapeWidth());
        LogUtil.i("ScreenUtil.getDisplayHeight()  " + ScreenUtil.getDisplayHeight());
        LogUtil.i("ScreenUtil.getDialogWidth()  " + ScreenUtil.getDialogWidth());
        LogUtil.i("ScreenUtil.density()  " + ScreenUtil.density);
        LogUtil.i("ScreenUtil.densityDpi()  " + ScreenUtil.densityDpi);
        LogUtil.i("ScreenUtil.scaleDensity()  " + ScreenUtil.scaleDensity);

//        for (int i = 400; i <= 500; i++) {
//            LogUtil.i("<dimen name=\"px"+i+"\">"+Math.round(i/1.5)+"dp</dimen>");
//           LogUtil.i("<dimen name=\"text"+i+"\">"+Math.round(i/1.5)+"sp</dimen>");
//        }

        mTabBar = findViewById(R.id.main_tab_bar);
        mainCover = findViewById(R.id.main_cover);
        mArrow = findViewById(R.id.main_tab_arrow);


        FragmentManager fm = getSupportFragmentManager();
        SupportFragment firstFragment = findFragment(HomeHomeFragment.class);
        if (firstFragment == null) {
            mFragments[0] = HomeFragment.newInstance();
            mFragments[1] = SafeFragment.newInstance();
            mFragments[2] = FamilyFragment.newInstance();
            mFragments[3] = ProperyFragment.newInstance();
            mFragments[4] = MessageFragment.newInstance();
            mFragments[5] = MarketFragment.newInstance();
            mFragments[6] = new NeighborMainFragment();
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
            mFragments[6] = findFragment(NeighborMainFragment.class);
            mFragments[7] = findFragment(SettingFragment.class);
        }

        mTabBar.addItem(new VerticalTabItem(this, R.mipmap.icon_leftnav_home_default, R.mipmap.icon_leftnav_home_selected, R.string.home))
                .addItem(new VerticalTabItem(this, R.mipmap.icon_leftnav_safe_default, R.mipmap.icon_leftnav_safe_selected, R.string.visual_security))
                .addItem(new VerticalTabItem(this, R.mipmap.icon_leftnav_smart_default, R.mipmap.icon_leftnav_smart_selected, R.string.intelligent_home))
                .addItem(new VerticalTabItem(this, R.mipmap.icon_leftnav_serve_default, R.mipmap.icon_leftnav_serve_selected, R.string.property_service))
                .addItem(new VerticalTabItem(this, R.mipmap.icon_leftnav_news_deafult, R.mipmap.icon_leftnav_news_selected, R.string.message_notify))
                .addItem(new VerticalTabItem(this, R.mipmap.icon_leftnav_market_default, R.mipmap.icon_leftnav_market_selected, R.string.market_service))
                .addItem(new VerticalTabItem(this, R.mipmap.icon_leftnav_neighbor_default, R.mipmap.icon_leftnav_neighbor_selected, R.string.neighbor))
                .addItem(new VerticalTabItem(this, R.mipmap.icon_leftnav_set_default, R.mipmap.icon_leftnav_set_selected, R.string.setting));


        mTabBar.setOnTabSelectedListener(new VerticalTabBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);
                moveArrow(position);
            }

        });

        mTabBar.post(new Runnable() {
            @Override
            public void run() {

                tbHeight = mTabBar.getHeight();
                itemHeight = tbHeight / mTabBar.getChildCount();
                arrowHeight = ScreenUtil.dip2px(46);
                arrowLayoutParams = (RelativeLayout.LayoutParams) mArrow.getLayoutParams();
                lastTop = (itemHeight - arrowHeight) / 2;
                arrowLayoutParams.topMargin = lastTop;
                mArrow.setLayoutParams(arrowLayoutParams);

//                mTabBar.getChildAt(5).performClick();
            }
        });

        initSDKManager();
    }

    private void initSDKManager() {
        manager = JXPadSdk.getDoorAccessManager();
//        manager.startFamily("A000000000050000", "02");
//        manager.startFamily("001901109CDB0000", "01");
//        manager.startFamily(DataManager.getFamilyId(), "01");
        manager.setListUIListener(this);
        manager.setDoorAccessListener(this);
//        manager.setListUIListener(this);
//        manager.setDoorAccessListener(this);
        manager.addSecurityListener(this);
//        manager.querySecurityStatus(DataManager.getFamilyId());
        manager.querySecurityStatus("A000000000050000");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);

    }

    private void moveArrow(int pos) {

        int newTop = (itemHeight - arrowHeight) / 2 + itemHeight * pos;
        ValueAnimator animator = ValueAnimator.ofInt(lastTop, newTop).setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                arrowLayoutParams.topMargin = (int) animation.getAnimatedValue();
                mArrow.setLayoutParams(arrowLayoutParams);
            }
        });
        animator.start();
        lastTop = newTop;
    }

    public void showCover() {
        mainCover.setVisibility(View.VISIBLE);
    }

    public void hideCover() {
        mainCover.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
                ActivityCompat.finishAfterTransition(this);
            } else {
                TOUCH_TIME = System.currentTimeMillis();
                ToastUtil.showShort(MainActivity.this, getString(R.string.press_again_exit));
            }
        }
    }
//
//    @Override
//    public void onBackToFirstFragment() {
//        mTabBar.setCurrentItem(0);
//    }

    //设置所有Fragment的转场动画
    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置默认Fragment动画  默认竖向(和安卓5.0以上的动画相同)
//        return super.onCreateFragmentAnimator();
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
        // 设置自定义动画
        //        return new FragmentAnimator(enter,exit,popEnter,popExit);
    }

    @Override
    public void onRinging(String sessionId) {
        Intent intent = new Intent(this, VideoCallActivity.class);
        intent.putExtra("sessionId", sessionId);
        startActivity(intent);
    }

    @Override
    public void onUnLock(String sessionID) {

    }

    @Override
    public void onDeviceChanged(String familyID, boolean isDoorDeviceOnLine, boolean isUnitDeviceOnline, boolean isPropertyDeviceOnLine) {

        LogUtil.i("doorOnline " + isDoorDeviceOnLine + " unitOnline = " + isUnitDeviceOnline + " isPropertyDeviceOnLine = " + isPropertyDeviceOnLine);
    }

    @Override
    public void onBaseButtonClick(String buttonKey, String cmd, String time) {

    }

    @Override
    public void onProxyOnlineStateChanged(String familyID, String proxyId, int router, boolean online) {

    }

    @Override
    public void onSnapshotReady(String familyID, String sessionID, String filePath) {

    }

    @Override
    public void refreshList() {
        LogUtil.i("refreshList");
    }
    /**
     * 安防状态变更
     * @param familyDockSn
     * @param state
     * @param isFromQuery
     */
    @Override
    public void onStateChanged(String familyDockSn, int state, boolean isFromQuery) {
        LogUtil.i("安防状态变更： " + familyDockSn + " 状态 ： " + state + " isFromQuery = " + isFromQuery);
    }

    /**
     * 安防设备报警
     * @param familyDockSn
     * @param stateBeans
     * @param device
     */
    @Override
    public void onAlarm(String familyDockSn, List<SecurityMessage.StateBean> stateBeans, SmartHomeManager.SecurityDevice device) {
        LogUtil.i("安防设备报警 ： " + familyDockSn + " 设备 " + stateBeans.get(0).getAlias());

    }

    /**
     * 安防取消报警回调
     * @param familyDockSn
     * @param device
     */
    @Override
    public void onCancelAlarm(String familyDockSn, SmartHomeManager.SecurityDevice device) {
        LogUtil.i("防区解除报警 ： " + familyDockSn);
    }
}
