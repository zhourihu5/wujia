package com.jingxi.smartlife.pad.mvp

import android.Manifest
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.os.PersistableBundle
import android.os.PowerManager
import android.support.v4.app.ActivityCompat
import android.view.View
import android.widget.RelativeLayout

import com.intercom.sdk.IntercomConstants
import com.intercom.sdk.SecurityMessage
import com.intercom.sdk.SmartHomeManager
import com.jingxi.smartlife.pad.R
import com.jingxi.smartlife.pad.family.FamilyFragment
import com.jingxi.smartlife.pad.family.mvp.FamilyHomeFragment
import com.jingxi.smartlife.pad.market.MarketFragment
import com.jingxi.smartlife.pad.market.mvp.MarketHomeFragment
import com.jingxi.smartlife.pad.message.MessageFragment
import com.jingxi.smartlife.pad.message.mvp.MessageHomeFragment
import com.jingxi.smartlife.pad.mvp.home.HomeHomeFragment
import com.jingxi.smartlife.pad.property.ProperyFragment
import com.jingxi.smartlife.pad.property.mvp.ProperyHomeFragment
import com.jingxi.smartlife.pad.safe.SafeFragment
import com.jingxi.smartlife.pad.safe.mvp.SafeHomeFragment
import com.jingxi.smartlife.pad.safe.mvp.view.VideoCallActivity
import com.jingxi.smartlife.pad.sdk.JXPadSdk
import com.jingxi.smartlife.pad.sdk.doorAccess.DoorAccessManager
import com.jingxi.smartlife.pad.sdk.doorAccess.base.DoorSecurityUtil
import com.jingxi.smartlife.pad.sdk.doorAccess.base.ui.DoorAccessListener
import com.wujia.businesslib.HookUtil
import com.wujia.businesslib.TabFragment
import com.wujia.businesslib.base.DataManager
import com.wujia.businesslib.base.MvpActivity
import com.wujia.businesslib.data.ApiResponse
import com.wujia.businesslib.event.EventBaseButtonClick
import com.wujia.businesslib.event.EventBusUtil
import com.wujia.businesslib.event.EventDoorDevice
import com.wujia.businesslib.event.EventMsg
import com.wujia.businesslib.event.EventSafeState
import com.wujia.businesslib.event.EventWakeup
import com.wujia.businesslib.event.IMiessageInvoke
import com.wujia.businesslib.model.BusModel
import com.wujia.businesslib.util.LoginUtil
import com.wujia.lib.widget.VerticalTabItem
import com.wujia.lib.widget.util.ToastUtil
import com.wujia.lib_common.base.BaseMainFragment
import com.wujia.lib_common.base.BasePresenter
import com.wujia.lib_common.base.BaseView
import com.wujia.lib_common.data.network.SimpleRequestSubscriber
import com.wujia.lib_common.data.network.exception.ApiException
import com.wujia.lib_common.utils.LogUtil
import com.wujia.lib_common.utils.ScreenUtil
import com.wujia.lib_common.utils.grant.PermissionsManager
import com.wujia.lib_common.utils.grant.PermissionsResultAction
import kotlinx.android.synthetic.main.activity_main.*

import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator//import android.os.ServiceManager;

//import android.service.dreams.IDreamManager;

class MainActivity : MvpActivity<BasePresenter<BaseView>>(), DoorAccessListener, DoorSecurityUtil.OnSecurityChangedListener {
    private var TOUCH_TIME: Long = 0  //点击返回键时间


    private val mFragments = arrayOfNulls<SupportFragment>(8)
    private var tbHeight: Int = 0
    private var itemHeight: Int = 0
    private var arrowHeight: Int = 0
    private var lastTop: Int = 0
    private var arrowLayoutParams: RelativeLayout.LayoutParams? = null

    private var manager: DoorAccessManager? = null
    private val eventMsg = EventMsg(IMiessageInvoke { setMessagePoint() })

    internal var busModel: BusModel? = null
    private var currentTab: Int = 0

    internal var dockeKey: String? = null
    internal var buttonKey: String? = null
    private var mWakelock: PowerManager.WakeLock? = null

    private fun setMessagePoint() {
        if (busModel == null) {
            busModel = BusModel()
        }
        addSubscribe(busModel!!.isUnReadMessage.subscribeWith(object : SimpleRequestSubscriber<ApiResponse<Boolean>>(this, SimpleRequestSubscriber.ActionConfig(false, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            override fun onResponse(response: ApiResponse<Boolean>) {
                super.onResponse(response)
                val tab = main_tab_bar.getChildAt(4) as VerticalTabItem
                tab.setPoint(response.data)
            }

            override fun onFailed(apiException: ApiException) {
                super.onFailed(apiException)
            }
        }))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            currentTab = savedInstanceState.getInt(POSITION_KEY, currentTab)
        }
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun initEventAndData(savedInstanceState: Bundle?) {

        LogUtil.i("ScreenUtil.getDialogWidth()  " + ScreenUtil.getDialogWidth())
        LogUtil.i("ScreenUtil.getLandscapeHeight()  " + ScreenUtil.getLandscapeHeight())
        LogUtil.i("ScreenUtil.getLandscapeWidth()  " + ScreenUtil.getLandscapeWidth())
        LogUtil.i("ScreenUtil.getDisplayHeight()  " + ScreenUtil.getDisplayHeight())
        LogUtil.i("ScreenUtil.getDialogWidth()  " + ScreenUtil.getDialogWidth())
        LogUtil.i("ScreenUtil.density()  " + ScreenUtil.density)
        LogUtil.i("ScreenUtil.densityDpi()  " + ScreenUtil.densityDpi)
        LogUtil.i("ScreenUtil.scaleDensity()  " + ScreenUtil.scaleDensity)

        //        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//todo

//        addSubscribe(Util.getUpdateVesion())

        initTab()

        initLockService()

        initSDKManager()

        initGrant()

        setMessagePoint()

        EventBusUtil.register(eventMsg)
        //        new Handler().postDelayed(new Runnable() {//todo test
        //            @Override
        //            public void run() {
        //                onRinging("test");//todo test
        //            }
        //        },2000);
    }

    private fun initTab() {

//        val firstFragment = findFragment(HomeFragment::class.java)
        val firstFragment = findFragment(HomeHomeFragment::class.java)
        if (firstFragment == null) {
            mFragments[0] = HomeHomeFragment.newInstance()
            mFragments[1] = SafeHomeFragment.newInstance(0)
            mFragments[2] = FamilyHomeFragment.newInstance(0)
            mFragments[3] = ProperyHomeFragment.newInstance(0)
            mFragments[4] = MessageHomeFragment.newInstance(0)
            mFragments[5] = MarketHomeFragment.newInstance(0)
            //            mFragments[6] = NeighborFragment.newInstance();
            mFragments[6] = SettingFragment.newInstance()

            loadMultipleRootFragment(R.id.container, currentTab,
                    mFragments[0],
                    mFragments[1],
                    mFragments[2],
                    mFragments[3],
                    mFragments[4],
                    mFragments[5],
                    //                    mFragments[6],
                    mFragments[6])
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用
            mFragments[0] = firstFragment
            mFragments[1] = findFragment(SafeFragment::class.java)
            mFragments[2] = findFragment(FamilyFragment::class.java)
            mFragments[3] = findFragment(ProperyFragment::class.java)
            mFragments[4] = findFragment(MessageFragment::class.java)
            mFragments[5] = findFragment(MarketFragment::class.java)
            //            mFragments[6] = findFragment(NeighborFragment.class);
            mFragments[6] = findFragment(SettingFragment::class.java)
        }

        main_tab_bar.addItem(VerticalTabItem(this, R.mipmap.icon_leftnav_home_default, R.mipmap.icon_leftnav_home_selected, R.string.home))
                .addItem(VerticalTabItem(this, R.mipmap.icon_leftnav_safe_default, R.mipmap.icon_leftnav_safe_selected, R.string.visual_security))
                .addItem(VerticalTabItem(this, R.mipmap.icon_leftnav_smart_default, R.mipmap.icon_leftnav_smart_selected, R.string.intelligent_home))
                .addItem(VerticalTabItem(this, R.mipmap.icon_leftnav_serve_default, R.mipmap.icon_leftnav_serve_selected, R.string.property_service))
                .addItem(VerticalTabItem(this, R.mipmap.icon_leftnav_news_deafult, R.mipmap.icon_leftnav_news_selected, R.string.message_notify))
                .addItem(VerticalTabItem(this, R.mipmap.icon_leftnav_market_default, R.mipmap.icon_leftnav_market_selected, R.string.market_service))
                //                .addItem(new VerticalTabItem(this, R.mipmap.icon_leftnav_neighbor_default, R.mipmap.icon_leftnav_neighbor_selected, R.string.neighbor))
                .addItem(VerticalTabItem(this, R.mipmap.icon_leftnav_set_default, R.mipmap.icon_leftnav_set_selected, R.string.setting))

        main_tab_bar.setOnTabSelectedListener { position, prePosition ->
            showHideFragment(mFragments[position], mFragments[prePosition])
            moveArrow(position)
            currentTab = position
        }
        main_tab_bar.post {
            tbHeight = main_tab_bar.height
            itemHeight = tbHeight / main_tab_bar.childCount
            arrowHeight = ScreenUtil.dip2px(46f)
            arrowLayoutParams = main_tab_arrow.layoutParams as RelativeLayout.LayoutParams
            lastTop = (itemHeight - arrowHeight) / 2
            arrowLayoutParams!!.topMargin = lastTop
            main_tab_arrow.layoutParams = arrowLayoutParams
            if (currentTab != 0) {
                main_tab_bar.getChildAt(currentTab).performClick()
            }
        }
    }

    private fun initLockService() {
        //import android.os.ServiceManager;
        //import android.service.dreams.IDreamManager;
        try {
            //            IDreamManager mDreamManager = IDreamManager.Stub.asInterface(
            //                    ServiceManager.getService("dreams"));
            val ServiceManager = Class.forName("android.os.ServiceManager")
            val getService = ServiceManager.getDeclaredMethod("getService", String::class.java)
            val iBinder = getService.invoke(null, "dreams")

            val `IDreamManager$Stub` = Class.forName("android.service.dreams.IDreamManager\$Stub")
            val asInterface = `IDreamManager$Stub`.getDeclaredMethod("asInterface", IBinder::class.java)
            val mDreamManager = asInterface.invoke(null, iBinder)



            LogUtil.i("main get pageage name =" + packageName + "  name =" + LockService::class.java.name)
            val componentName = ComponentName(packageName, LockService::class.java.name)
            val componentNames = arrayOf(componentName)
            //            mDreamManager.setDreamComponents(componentNames);

            val IDreamManager = Class.forName("android.service.dreams.IDreamManager")
            val setDreamComponents = IDreamManager.getDeclaredMethod("setDreamComponents", Array<ComponentName>::class.java)
            setDreamComponents.invoke(mDreamManager, componentNames as Any)
            LogUtil.i("initLockService success")
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun initGrant() {

        HookUtil.hookWebView()

        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(this@MainActivity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA),
                object : PermissionsResultAction() {
                    override fun onGranted() {

                    }

                    override fun onDenied(permission: String) {
                        showToast("未获得相应权限")
                    }
                })
    }

    private fun initSDKManager() {
        manager = JXPadSdk.getDoorAccessManager()
        manager!!.setDoorAccessListener(this)
        try {
            dockeKey = DataManager.getDockKey()
            buttonKey = DataManager.getButtonKey()
        } catch (e: Exception) {
            LogUtil.t("获取dockKey失败", e)
            LoginUtil.toLoginActivity()
            return
        }

        manager!!.startFamily(dockeKey, buttonKey)

        //        manager.addSecurityListener(this);
        //        manager.querySecurityStatus(DataManager.getFid());
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
    }

    @SuppressLint("MissingSuperCall")
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(POSITION_KEY, currentTab)
    }

    private fun moveArrow(pos: Int) {

        val newTop = (itemHeight - arrowHeight) / 2 + itemHeight * pos
        val animator = ValueAnimator.ofInt(lastTop, newTop).setDuration(300)
        animator.addUpdateListener { animation ->
            arrowLayoutParams!!.topMargin = animation.animatedValue as Int
            main_tab_arrow.layoutParams = arrowLayoutParams
        }
        animator.start()
        lastTop = newTop
    }

    fun showCover() {
        main_cover.visibility = View.VISIBLE
    }

    fun hideCover() {
        main_cover.visibility = View.GONE
    }

    override fun onBackPressedSupport() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            pop()
        } else {
            if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
                ActivityCompat.finishAfterTransition(this)
            } else {
                TOUCH_TIME = System.currentTimeMillis()
                ToastUtil.showShort(this@MainActivity, getString(R.string.press_again_exit))
            }
        }
    }
    //
    //    @Override
    //    public void onBackToFirstFragment() {
    //        main_tab_bar.setCurrentItem(0);
    //    }

    //设置所有Fragment的转场动画
    override fun onCreateFragmentAnimator(): FragmentAnimator {
        // 设置默认Fragment动画  默认竖向(和安卓5.0以上的动画相同)
        //        return super.onCreateFragmentAnimator();
        // 设置横向(和安卓4.x动画相同)
        return DefaultHorizontalAnimator()
        // 设置自定义动画
        //        return new FragmentAnimator(enter,exit,popEnter,popExit);
    }

    override fun onRinging(sessionId: String) {
        EventBusUtil.post(EventWakeup())

        val intent = Intent(this, VideoCallActivity::class.java)
        intent.putExtra("sessionId", sessionId)
        startActivity(intent)
    }

    override fun onUnLock(sessionID: String) {

    }

    override fun onDeviceChanged(familyID: String, isDoorDeviceOnLine: Boolean, isUnitDeviceOnline: Boolean, isPropertyDeviceOnLine: Boolean) {

        LogUtil.i("doorOnline $isDoorDeviceOnLine unitOnline = $isUnitDeviceOnline isPropertyDeviceOnLine = $isPropertyDeviceOnLine")
    }

    override fun onBaseButtonClick(buttonKey: String, cmd: String, time: String) {//todo 底座按键回调
        LogUtil.i(String.format("buttonKey=%s,cmd=%s,time=%s", buttonKey, cmd, time))
        if (com.intercom.sdk.IntercomConstants.kButtonMonitor == cmd) {
            switchHomeTab(POSITION_SAFE, 0)
        } else if (com.intercom.sdk.IntercomConstants.kButtonUser == cmd) {
            switchHomeTab(POSITION_PROPERTY, 0)
        } else if (com.intercom.sdk.IntercomConstants.kButtonUnlock == cmd) {
            EventBusUtil.post(EventBaseButtonClick(com.intercom.sdk.IntercomConstants.kButtonUnlock))
        } else if (com.intercom.sdk.IntercomConstants.kButtonPickup == cmd) {
            EventBusUtil.post(EventBaseButtonClick(com.intercom.sdk.IntercomConstants.kButtonPickup))
        } else if (com.intercom.sdk.IntercomConstants.kButtonHumanNear == cmd) {
            LogUtil.i("kButtonHumanNear")
            initWakeLock()
        } else if (IntercomConstants.kButtonHumanLeav == cmd) {
            LogUtil.i("kButtonHumanLeav")
            releaseWakeLock()
        }
    }

    protected fun releaseWakeLock() {
        if (mWakelock != null) {
            mWakelock!!.release()
            mWakelock = null
            LogUtil.i("mWakelock.release")
        }
    }

    protected fun initWakeLock() {

        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        if (Build.VERSION.SDK_INT >= 21 && powerManager != null && !powerManager.isInteractive) {//灭屏时点亮
//            if (mWakelock == null) {
                mWakelock = powerManager.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP or PowerManager.SCREEN_DIM_WAKE_LOCK, packageName) // this target for tell OS which app
//            }
            mWakelock!!.acquire()//can not be reused,so create it every time
            LogUtil.i("mWakelock.acquire")
        }
    }

    override fun onProxyOnlineStateChanged(familyID: String, proxyId: String, router: Int, online: Boolean) {
        LogUtil.i("onProxyOnlineStateChanged  familyID $familyID proxyId = $proxyId router = $router online = $online")
        EventBusUtil.post(EventSafeState(online))

    }

    override fun onSnapshotReady(familyID: String, sessionID: String, filePath: String) {

    }

    /**
     * 安防状态变更
     *
     * @param familyDockSn
     * @param state
     * @param isFromQuery
     */
    override fun onStateChanged(familyDockSn: String, state: Int, isFromQuery: Boolean) {
        LogUtil.i("安防状态变更： $familyDockSn 状态 ： $state isFromQuery = $isFromQuery")
        EventBusUtil.post(EventDoorDevice())
    }

    /**
     * 安防设备报警
     *
     * @param familyDockSn
     * @param stateBeans
     * @param device
     */
    override fun onAlarm(familyDockSn: String, stateBeans: List<SecurityMessage.StateBean>, device: SmartHomeManager.SecurityDevice) {
        LogUtil.i("安防设备报警 ： " + familyDockSn + " 设备 " + stateBeans[0].alias)

    }

    /**
     * 安防取消报警回调
     *
     * @param familyDockSn
     * @param device
     */
    override fun onCancelAlarm(familyDockSn: String, device: SmartHomeManager.SecurityDevice) {
        LogUtil.i("防区解除报警 ： $familyDockSn")
    }


    fun switchHomeTab(pos: Int, childPos: Int) {
        main_tab_bar.getChildAt(pos).performClick()
        val tabFragment = mFragments[pos]
        if (tabFragment is TabFragment)
            tabFragment.switchTab(childPos)
    }

    override fun createPresenter(): BasePresenter<BaseView>? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBusUtil.unregister(eventMsg)
        manager!!.setDoorAccessListener(null)
        //        manager.stopFamily(dockeKey);
        manager = null
        releaseWakeLock()
    }

    companion object {

        val POSITION_HOME = 0
        val POSITION_SAFE = 1
        val POSITION_FAMILY = 2
        val POSITION_PROPERTY = 3
        val POSITION_MESSAGE = 4
        val POSITION_MARKET = 5
        val POSITION_NEIGHBOR = 6
        val POSITION_SETTING = 7

        // 再点一次退出程序时间设置
        private val WAIT_TIME = 2000L
        internal val POSITION_KEY = "position"
    }
}
