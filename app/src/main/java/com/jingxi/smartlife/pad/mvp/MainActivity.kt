package com.jingxi.smartlife.pad.mvp

import android.Manifest
import android.animation.ValueAnimator
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.os.PowerManager
import android.text.TextUtils
import android.widget.RelativeLayout
import androidx.core.app.ActivityCompat
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
import com.jingxi.smartlife.pad.mvp.home.HomeFragment
import com.jingxi.smartlife.pad.property.ProperyFragment
import com.jingxi.smartlife.pad.property.mvp.ProperyHomeFragment
import com.jingxi.smartlife.pad.safe.SafeFragment
import com.jingxi.smartlife.pad.safe.mvp.SafeHomeFragment
import com.jingxi.smartlife.pad.safe.mvp.view.VideoCallActivity
import com.jingxi.smartlife.pad.sdk.JXPadSdk
import com.jingxi.smartlife.pad.sdk.doorAccess.DoorAccessManager
import com.jingxi.smartlife.pad.sdk.doorAccess.base.DoorSecurityUtil
import com.jingxi.smartlife.pad.sdk.doorAccess.base.ui.DoorAccessListener
import com.wujia.businesslib.TabFragment
import com.wujia.businesslib.base.DataManager
import com.wujia.businesslib.base.MvpActivity
import com.wujia.businesslib.data.ApiResponse
import com.wujia.businesslib.event.*
import com.wujia.businesslib.model.BusModel
import com.wujia.businesslib.util.LoginUtil
import com.wujia.lib.widget.VerticalTabItem
import com.wujia.lib.widget.util.ToastUtil
import com.wujia.lib_common.base.BaseMainFragment
import com.wujia.lib_common.base.BasePresenter
import com.wujia.lib_common.base.BaseView
import com.wujia.lib_common.data.network.SimpleRequestSubscriber
import com.wujia.lib_common.utils.LogUtil
import com.wujia.lib_common.utils.ScreenUtil
import com.wujia.lib_common.utils.grant.PermissionsManager
import com.wujia.lib_common.utils.grant.PermissionsResultAction
import kotlinx.android.synthetic.main.activity_main.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator


class MainActivity : MvpActivity<BasePresenter<BaseView>>(), DoorAccessListener, DoorSecurityUtil.OnSecurityChangedListener {
    private var touchTime: Long = 0  //点击返回键时间


    private val mFragments = arrayOfNulls<SupportFragment>(8)
    private var itemHeight: Int = 0
    private var arrowHeight: Int = 0
    private var lastTop: Int = 0
    private var arrowLayoutParams: RelativeLayout.LayoutParams? = null
    private var manager: DoorAccessManager? = null
    private val eventMsg = EventMsg(IMiessageInvoke { setMessagePoint() })
    private var currentTab: Int = 0
    private var mWakelock: PowerManager.WakeLock? = null

    private fun setMessagePoint() {
        addSubscribe(BusModel().isUnReadMessage.subscribeWith(object : SimpleRequestSubscriber<ApiResponse<Boolean>>(this, ActionConfig(false, SHOWERRORMESSAGE)) {
            override fun onResponse(response: ApiResponse<Boolean>) {
                super.onResponse(response)
                val tab = main_tab_bar.getChildAt(POSITION_MESSAGE) as VerticalTabItem
                tab.setPoint(response.data)
            }
        }))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            currentTab = savedInstanceState.getInt(POSITION_KEY, currentTab)
        }
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

        val token = DataManager.getToken()
        if (TextUtils.isEmpty(token)) {
            LogUtil.i("before login")
            LoginUtil.toLoginActivity()
            return
        }
        initTab()
        initLockService()
        initSDKManager()
        initGrant()
        setMessagePoint()
        EventBusUtil.register(eventMsg)


//        Handler().postDelayed(Runnable() {
//            //todo test
//            onRinging("test")
//        }, 2000)
    }

    private fun initTab() {

        mFragments[0] = findFragment(HomeFragment::class.java)//restored from savedinstance
        mFragments[1] = findFragment(SafeFragment::class.java)
        mFragments[2] = findFragment(FamilyFragment::class.java)
        mFragments[3] = findFragment(ProperyFragment::class.java)
        mFragments[4] = findFragment(MessageFragment::class.java)
        mFragments[5] = findFragment(MarketFragment::class.java)
        mFragments[6] = findFragment(SettingFragment::class.java)
        if (mFragments[0] == null) {
            mFragments[0] = HomeFragment.newInstance()
            mFragments[1] = SafeFragment.newInstance()
            mFragments[2] = FamilyFragment.newInstance()
            mFragments[3] = ProperyFragment.newInstance()
            mFragments[4] = MessageFragment.newInstance()
            mFragments[5] = MarketFragment.newInstance()
            //            mFragments[6] = NeighborFragment.newInstance();
            mFragments[6] = SettingFragment.newInstance()

            loadMultipleRootFragment(R.id.container, currentTab,
                    mFragments[0],
                    mFragments[1],
                    mFragments[2],
                    mFragments[3],
                    mFragments[4],
                    mFragments[5],
                    mFragments[6])
        }

        main_tab_bar.addItem(VerticalTabItem(this, R.mipmap.icon_leftnav_home_default, R.mipmap.icon_leftnav_home_selected, R.string.home))
                .addItem(VerticalTabItem(this, R.mipmap.icon_leftnav_safe_default, R.mipmap.icon_leftnav_safe_selected, R.string.visual_security))
                .addItem(VerticalTabItem(this, R.mipmap.icon_leftnav_smart_default, R.mipmap.icon_leftnav_smart_selected, R.string.intelligent_home))
                .addItem(VerticalTabItem(this, R.mipmap.icon_leftnav_serve_default, R.mipmap.icon_leftnav_serve_selected, R.string.property_service))
                .addItem(VerticalTabItem(this, R.mipmap.icon_leftnav_news_deafult, R.mipmap.icon_leftnav_news_selected, R.string.message_notify))
                .addItem(VerticalTabItem(this, R.mipmap.icon_leftnav_market_default, R.mipmap.icon_leftnav_market_selected, R.string.market_service))
                .addItem(VerticalTabItem(this, R.mipmap.icon_leftnav_set_default, R.mipmap.icon_leftnav_set_selected, R.string.setting))

        main_tab_bar.setOnTabSelectedListener { position, prePosition ->
            showHideFragment(mFragments[position], mFragments[prePosition])
            moveArrow(position)
            currentTab = position
        }
        main_tab_bar.post {
            val tbHeight = main_tab_bar.height
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
        try {
            val ServiceManager = Class.forName("android.os.ServiceManager")
            val getService = ServiceManager.getDeclaredMethod("getService", String::class.java)
            val iBinder = getService.invoke(null, "dreams")
            val `IDreamManager$Stub` = Class.forName("android.service.dreams.IDreamManager\$Stub")
            val asInterface = `IDreamManager$Stub`.getDeclaredMethod("asInterface", IBinder::class.java)
            val mDreamManager = asInterface.invoke(null, iBinder)
            val componentName = ComponentName(packageName, LockService::class.java.name)
            val componentNames = arrayOf(componentName)
            val IDreamManager = Class.forName("android.service.dreams.IDreamManager")
            val setDreamComponents = IDreamManager.getDeclaredMethod("setDreamComponents", Array<ComponentName>::class.java)
            setDreamComponents.invoke(mDreamManager, componentNames as Any)
            LogUtil.i("initLockService success")
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun initGrant() {
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
        var dockeKey=""
        val buttonKey:String?
        try {
             dockeKey = DataManager.getDockKey()
            buttonKey= DataManager.getButtonKey()
        } catch (e: Exception) {
            LogUtil.t("获取dockKey失败", e)
            LoginUtil.toLoginActivity()
            return
        }
        manager!!.startFamily(dockeKey, buttonKey)
        //        manager.addSecurityListener(this);
        //        manager.querySecurityStatus(DataManager.getFid());
    }
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
    override fun onBackPressedSupport() {
        if (supportFragmentManager.backStackEntryCount > 1) {
            pop()
        } else {
            if (System.currentTimeMillis() - touchTime < WAIT_TIME) {
                ActivityCompat.finishAfterTransition(this)
            } else {
                touchTime = System.currentTimeMillis()
                ToastUtil.showShort(this@MainActivity, getString(R.string.press_again_exit))
            }
        }
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onRinging(sessionId: String) {
        LogUtil.i("onRinging")
        acquireWakeLock()
        EventBusUtil.post(EventWakeup())
        val intent = Intent(this, VideoCallActivity::class.java)
        intent.putExtra(VideoCallActivity.SESSION_ID, sessionId)
        startActivity(intent)
    }

    override fun onUnLock(sessionID: String) {
    }

    override fun onDeviceChanged(familyID: String, isDoorDeviceOnLine: Boolean, isUnitDeviceOnline: Boolean, isPropertyDeviceOnLine: Boolean) {
        LogUtil.i("doorOnline $isDoorDeviceOnLine unitOnline = $isUnitDeviceOnline isPropertyDeviceOnLine = $isPropertyDeviceOnLine")
    }

    override fun onBaseButtonClick(buttonKey: String, cmd: String, time: String) {//todo 底座按键回调
        LogUtil.i(String.format("buttonKey=%s,cmd=%s,time=%s", buttonKey, cmd, time))
        when (cmd) {
            IntercomConstants.kButtonMonitor -> {
                acquireWakeLock()
                switchHomeTab(POSITION_SAFE, 0)
            }
            IntercomConstants.kButtonUser -> {
                acquireWakeLock()
                switchHomeTab(POSITION_PROPERTY, 0)
            }
            IntercomConstants.kButtonUnlock -> {
                acquireWakeLock()
                EventBusUtil.post(EventBaseButtonClick(IntercomConstants.kButtonUnlock))
            }
            IntercomConstants.kButtonPickup -> {
                acquireWakeLock()
                EventBusUtil.post(EventBaseButtonClick(IntercomConstants.kButtonPickup))
            }
            IntercomConstants.kButtonHumanNear -> {
                LogUtil.i("kButtonHumanNear")
                acquireWakeLock()
            }
            IntercomConstants.kButtonHumanLeav -> {
                LogUtil.i("kButtonHumanLeav")
                releaseWakeLock()
            }
            IntercomConstants.kButtonSOS -> acquireWakeLock()
        }
    }

    private fun releaseWakeLock() {
        mWakelock?.release()
    }

    private fun acquireWakeLock() {
        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        if ( powerManager != null && !powerManager.isInteractive) {//灭屏时点亮
            mWakelock = powerManager.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP or PowerManager.SCREEN_DIM_WAKE_LOCK, packageName) // this target for tell OS which app
            mWakelock?.acquire(10*60*1000L /*10 minutes*/)//can not be reused,so create it every time
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
        var tabFragment: TabFragment? = null
        when (pos) {
            POSITION_MARKET -> tabFragment = mFragments[pos]!!.findChildFragment(MarketHomeFragment::class.java)
            POSITION_PROPERTY -> tabFragment = mFragments[pos]!!.findChildFragment(ProperyHomeFragment::class.java)
            POSITION_FAMILY -> tabFragment = mFragments[pos]!!.findChildFragment(FamilyHomeFragment::class.java)
            POSITION_SAFE -> tabFragment = mFragments[pos]!!.findChildFragment(SafeHomeFragment::class.java)
            POSITION_MESSAGE -> tabFragment = mFragments[pos]!!.findChildFragment(MessageHomeFragment::class.java)
        }
        tabFragment?.switchTab(childPos)?:(mFragments[pos] as? BaseMainFragment)?.switchTab(childPos)
//        if (null != tabFragment)
//            tabFragment.switchTab(childPos)
//        else {
//            val fragment = mFragments[pos] as BaseMainFragment
//            fragment.switchTab(childPos)
//        }
    }

    override fun createPresenter(): BasePresenter<BaseView>? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBusUtil.unregister(eventMsg)
        manager?.setDoorAccessListener(null)
        releaseWakeLock()
    }

    companion object {

        const val POSITION_SAFE = 1
        const val POSITION_FAMILY = 2
        const val POSITION_PROPERTY = 3
        const val POSITION_MESSAGE = 4
        const val POSITION_MARKET = 5

        // 再点一次退出程序时间设置
        private const val WAIT_TIME = 2000L
        internal const val POSITION_KEY = "position"
    }
}
