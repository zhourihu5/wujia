package com.jingxi.smartlife.pad.mvp;

import android.Manifest;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.os.ServiceManager;
import android.service.dreams.IDreamManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.intercom.sdk.SecurityMessage;
import com.intercom.sdk.SmartHomeManager;
import com.jingxi.smartlife.pad.R;
import com.jingxi.smartlife.pad.family.FamilyFragment;
import com.jingxi.smartlife.pad.family.mvp.FamilyHomeFragment;
import com.jingxi.smartlife.pad.market.MarketFragment;
import com.jingxi.smartlife.pad.market.mvp.MarketHomeFragment;
import com.jingxi.smartlife.pad.message.MessageFragment;
import com.jingxi.smartlife.pad.message.mvp.MessageHomeFragment;
import com.jingxi.smartlife.pad.mvp.home.HomeFragment;
import com.jingxi.smartlife.pad.mvp.home.HomeHomeFragment;
import com.jingxi.smartlife.pad.property.ProperyFragment;
import com.jingxi.smartlife.pad.property.mvp.ProperyHomeFragment;
import com.jingxi.smartlife.pad.safe.SafeFragment;
import com.jingxi.smartlife.pad.safe.mvp.SafeHomeFragment;
import com.jingxi.smartlife.pad.safe.mvp.view.VideoCallActivity;
import com.jingxi.smartlife.pad.sdk.JXPadSdk;
import com.jingxi.smartlife.pad.sdk.doorAccess.DoorAccessManager;
import com.jingxi.smartlife.pad.sdk.doorAccess.base.DoorSecurityUtil;
import com.jingxi.smartlife.pad.sdk.doorAccess.base.ui.DoorAccessListener;
import com.wujia.businesslib.HookUtil;
import com.wujia.businesslib.base.DataManager;
import com.wujia.businesslib.base.MvpActivity;
import com.wujia.businesslib.data.ApiResponse;
import com.wujia.businesslib.event.EventBaseButtonClick;
import com.wujia.businesslib.event.EventBusUtil;
import com.wujia.businesslib.event.EventDoorDevice;
import com.wujia.businesslib.event.EventMsg;
import com.wujia.businesslib.event.EventSafeState;
import com.wujia.businesslib.event.EventWakeup;
import com.wujia.businesslib.event.IMiessageInvoke;
import com.wujia.businesslib.model.BusModel;
import com.wujia.businesslib.util.LoginUtil;
import com.wujia.lib.widget.VerticalTabBar;
import com.wujia.lib.widget.VerticalTabItem;
import com.wujia.lib.widget.util.ToastUtil;
import com.wujia.lib_common.base.BaseMainFragment;
import com.wujia.lib_common.base.BasePresenter;
import com.wujia.lib_common.base.TabFragment;
import com.wujia.lib_common.data.network.SimpleRequestSubscriber;
import com.wujia.lib_common.data.network.exception.ApiException;
import com.wujia.lib_common.utils.LogUtil;
import com.wujia.lib_common.utils.ScreenUtil;
import com.wujia.lib_common.utils.grant.PermissionsManager;
import com.wujia.lib_common.utils.grant.PermissionsResultAction;

import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class MainActivity extends MvpActivity implements DoorAccessListener, DoorSecurityUtil.OnSecurityChangedListener {

    public static final int POSITION_HOME = 0;
    public static final int POSITION_SAFE = 1;
    public static final int POSITION_FAMILY = 2;
    public static final int POSITION_PROPERTY = 3;
    public static final int POSITION_MESSAGE = 4;
    public static final int POSITION_MARKET = 5;
    public static final int POSITION_NEIGHBOR = 6;
    public static final int POSITION_SETTING = 7;

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
    private EventMsg eventMsg = new EventMsg(new IMiessageInvoke<EventMsg>() {
        @Override
        public void eventBus(EventMsg event) {
            setMessagePoint();
        }
    });

    BusModel busModel;

    private void setMessagePoint() {
        if (busModel == null) {
            busModel = new BusModel();
        }
        addSubscribe(busModel.isUnReadMessage().subscribeWith(new SimpleRequestSubscriber<ApiResponse<Boolean>>(this, new SimpleRequestSubscriber.ActionConfig(false, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            @Override
            public void onResponse(ApiResponse<Boolean> response) {
                super.onResponse(response);
                VerticalTabItem tab = (VerticalTabItem) mTabBar.getChildAt(4);
                tab.setPoint(response.data);
            }

            @Override
            public void onFailed(ApiException apiException) {
                super.onFailed(apiException);
            }
        }));
    }

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

        initTab();

        initLockService();

        initSDKManager();

        initGrant();

        setMessagePoint();

        EventBusUtil.register(eventMsg);
//        new Handler().postDelayed(new Runnable() {//todo test
//            @Override
//            public void run() {
//                onRinging("test");//todo test
//            }
//        },2000);
    }

    private void initTab() {
        mTabBar = $(R.id.main_tab_bar);
        mainCover = $(R.id.main_cover);
        mArrow = $(R.id.main_tab_arrow);


        FragmentManager fm = getSupportFragmentManager();
        SupportFragment firstFragment = findFragment(HomeHomeFragment.class);
        if (firstFragment == null) {
            mFragments[0] = HomeFragment.newInstance();
            mFragments[1] = SafeFragment.newInstance();
            mFragments[2] = FamilyFragment.newInstance();
            mFragments[3] = ProperyFragment.newInstance();
            mFragments[4] = MessageFragment.newInstance();
            mFragments[5] = MarketFragment.newInstance();
//            mFragments[6] = NeighborFragment.newInstance();
            mFragments[6] = SettingFragment.newInstance();

            loadMultipleRootFragment(R.id.container, 0,
                    mFragments[0],
                    mFragments[1],
                    mFragments[2],
                    mFragments[3],
                    mFragments[4],
                    mFragments[5],
//                    mFragments[6],
                    mFragments[6]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用
            mFragments[0] = firstFragment;
            mFragments[1] = findFragment(SafeFragment.class);
            mFragments[2] = findFragment(FamilyFragment.class);
            mFragments[3] = findFragment(ProperyFragment.class);
            mFragments[4] = findFragment(MessageFragment.class);
            mFragments[5] = findFragment(MarketFragment.class);
//            mFragments[6] = findFragment(NeighborFragment.class);
            mFragments[6] = findFragment(SettingFragment.class);
        }

        mTabBar.addItem(new VerticalTabItem(this, R.mipmap.icon_leftnav_home_default, R.mipmap.icon_leftnav_home_selected, R.string.home))
                .addItem(new VerticalTabItem(this, R.mipmap.icon_leftnav_safe_default, R.mipmap.icon_leftnav_safe_selected, R.string.visual_security))
                .addItem(new VerticalTabItem(this, R.mipmap.icon_leftnav_smart_default, R.mipmap.icon_leftnav_smart_selected, R.string.intelligent_home))
                .addItem(new VerticalTabItem(this, R.mipmap.icon_leftnav_serve_default, R.mipmap.icon_leftnav_serve_selected, R.string.property_service))
                .addItem(new VerticalTabItem(this, R.mipmap.icon_leftnav_news_deafult, R.mipmap.icon_leftnav_news_selected, R.string.message_notify))
                .addItem(new VerticalTabItem(this, R.mipmap.icon_leftnav_market_default, R.mipmap.icon_leftnav_market_selected, R.string.market_service))
//                .addItem(new VerticalTabItem(this, R.mipmap.icon_leftnav_neighbor_default, R.mipmap.icon_leftnav_neighbor_selected, R.string.neighbor))
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
    }

    private void initLockService() {
        try {
            IDreamManager mDreamManager = IDreamManager.Stub.asInterface(
                    ServiceManager.getService("dreams"));
            LogUtil.i("main get pageage name =" + getPackageName() + "  name =" + LockService.class.getName());
            ComponentName componentName = new ComponentName(getPackageName(), LockService.class.getName());
            ComponentName[] componentNames = {componentName};
            mDreamManager.setDreamComponents(componentNames);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initGrant() {

        HookUtil.hookWebView();

        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(MainActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.CAMERA},
                new PermissionsResultAction() {
                    @Override
                    public void onGranted() {

                    }

                    @Override
                    public void onDenied(String permission) {
                        showToast("未获得相应权限");
                    }
                });
    }

    private void initSDKManager() {
        manager = JXPadSdk.getDoorAccessManager();
        manager.setDoorAccessListener(this);
        String dockeKey = null;
        String buttonKey = null;
        try {
            dockeKey = DataManager.getDockKey();
            buttonKey = DataManager.getButtonKey();
        } catch (Exception e) {
            LogUtil.t("获取dockKey失败", e);
            LoginUtil.toLoginActivity();
            return;
        }
        manager.startFamily(dockeKey, buttonKey);

//        manager.addSecurityListener(this);
//        manager.querySecurityStatus(DataManager.getFid());
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
        EventBusUtil.post(new EventWakeup());

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
    public void onBaseButtonClick(String buttonKey, String cmd, String time) {//todo 底座按键回调
        LogUtil.i(String.format("buttonKey=%s,cmd=%s,time=%s",buttonKey,cmd,time));
        if(com.intercom.sdk.IntercomConstants.kButtonMonitor.equals(cmd)){
            switchHomeTab(POSITION_SAFE,0);
        }else if(com.intercom.sdk.IntercomConstants.kButtonUser.equals(cmd)){
            switchHomeTab(POSITION_PROPERTY,0);
        }else if(com.intercom.sdk.IntercomConstants.kButtonUnlock.equals(cmd)){
            EventBusUtil.post(new EventBaseButtonClick());
        }
    }

    @Override
    public void onProxyOnlineStateChanged(String familyID, String proxyId, int router, boolean online) {
        LogUtil.i("onProxyOnlineStateChanged  familyID " + familyID + " proxyId = " + proxyId + " router = " + router + " online = " + online);
        EventBusUtil.post(new EventSafeState(online));

    }

    @Override
    public void onSnapshotReady(String familyID, String sessionID, String filePath) {

    }

    /**
     * 安防状态变更
     *
     * @param familyDockSn
     * @param state
     * @param isFromQuery
     */
    @Override
    public void onStateChanged(String familyDockSn, int state, boolean isFromQuery) {
        LogUtil.i("安防状态变更： " + familyDockSn + " 状态 ： " + state + " isFromQuery = " + isFromQuery);
        EventBusUtil.post(new EventDoorDevice());
    }

    /**
     * 安防设备报警
     *
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
     *
     * @param familyDockSn
     * @param device
     */
    @Override
    public void onCancelAlarm(String familyDockSn, SmartHomeManager.SecurityDevice device) {
        LogUtil.i("防区解除报警 ： " + familyDockSn);
    }


    public void switchHomeTab(int pos, int childPos) {
        mTabBar.getChildAt(pos).performClick();
        TabFragment tabFragment = null;
        switch (pos) {
            case POSITION_MARKET:
                tabFragment = mFragments[pos].findChildFragment(MarketHomeFragment.class);
                break;
            case POSITION_PROPERTY:
                tabFragment = mFragments[pos].findChildFragment(ProperyHomeFragment.class);
                break;
            case POSITION_FAMILY:
                tabFragment = mFragments[pos].findChildFragment(FamilyHomeFragment.class);
                break;
            case POSITION_SAFE:
                tabFragment = mFragments[pos].findChildFragment(SafeHomeFragment.class);
                break;
            case POSITION_MESSAGE:
                tabFragment = mFragments[pos].findChildFragment(MessageHomeFragment.class);
                break;
        }
        if (null != tabFragment)
            tabFragment.switchTab(childPos);
        else {
            BaseMainFragment fragment = (BaseMainFragment) mFragments[pos];
            if (null != fragment) {
                fragment.switchTab(childPos);
            }
        }
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtil.unregister(eventMsg);
        manager.setDoorAccessListener(null);
        manager = null;
    }
}
