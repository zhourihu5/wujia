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
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.jingxi.smartlife.pad.R
import com.jingxi.smartlife.pad.family.FamilyFragment
import com.jingxi.smartlife.pad.family.mvp.FamilyHomeFragment
import com.jingxi.smartlife.pad.market.MarketFragment
import com.jingxi.smartlife.pad.market.PromoteFragment
import com.jingxi.smartlife.pad.market.mvp.MarketHomeFragment
import com.jingxi.smartlife.pad.message.MessageFragment
import com.jingxi.smartlife.pad.message.mvp.MessageHomeFragment
import com.jingxi.smartlife.pad.mvp.home.HomeFragment
import com.jingxi.smartlife.pad.mvp.setting.view.SettingHomeFragment
import com.jingxi.smartlife.pad.property.ProperyFragment
import com.jingxi.smartlife.pad.property.mvp.ProperyHomeFragment
import com.jingxi.smartlife.pad.safe.SafeFragment
import com.jingxi.smartlife.pad.safe.mvp.SafeHomeFragment
import com.jingxi.smartlife.pad.safe.mvp.view.VideoCallActivity
import com.sipphone.sdk.SipCoreManager
import com.sipphone.sdk.SipService
import com.wujia.businesslib.TabFragment
import com.wujia.businesslib.base.DataManager
import com.wujia.businesslib.base.MvpActivity
import com.wujia.businesslib.data.ApiResponse
import com.wujia.businesslib.event.EventBusUtil
import com.wujia.businesslib.event.EventMsg
import com.wujia.businesslib.event.IMiessageInvoke
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
import org.linphone.core.*


class MainActivity : MvpActivity<BasePresenter<BaseView>>() {
    override val layout: Int
        get() = R.layout.activity_main
    private var touchTime: Long = 0  //?????????????????????


    private val mFragments = arrayOfNulls<SupportFragment>(8)
    private var itemHeight: Int = 0
    private var arrowHeight: Int = 0
    private var lastTop: Int = 0
    private var arrowLayoutParams: RelativeLayout.LayoutParams? = null
    private val eventMsg = EventMsg(object : IMiessageInvoke<EventMsg> {
        override fun eventBus(event: EventMsg) {
            setMessagePoint()
        }
    })
    private var currentTab: Int = 0
    private var mWakelock: PowerManager.WakeLock? = null

    private fun setMessagePoint() {
        addSubscribe(BusModel().isUnReadMessage.subscribeWith(object : SimpleRequestSubscriber<ApiResponse<Boolean>>(this@MainActivity, ActionConfig(false, SHOWERRORMESSAGE)) {
            override fun onResponse(response: ApiResponse<Boolean>) {
                super.onResponse(response)
                val tab = main_tab_bar.getChildAt(POSITION_MESSAGE) as VerticalTabItem
                response.data?.let { tab.setPoint(it) }
            }
        }))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            currentTab = savedInstanceState.getInt(POSITION_KEY, currentTab)
        }
    }

    override fun initEventAndData(savedInstanceState: Bundle?) {


        LogUtil.i("ScreenUtil.dialogWidth  " + ScreenUtil.dialogWidth)
        LogUtil.i("ScreenUtil.landscapeHeight  " + ScreenUtil.landscapeHeight)
        LogUtil.i("ScreenUtil.landscapeWidth  " + ScreenUtil.landscapeWidth)
        LogUtil.i("ScreenUtil.displayHeight  " + ScreenUtil.displayHeight)
        LogUtil.i("ScreenUtil.dialogWidth  " + ScreenUtil.dialogWidth)
        LogUtil.i("ScreenUtil.density()  " + ScreenUtil.density)
        LogUtil.i("ScreenUtil.densityDpi()  " + ScreenUtil.densityDpi)
        LogUtil.i("ScreenUtil.scaleDensity()  " + ScreenUtil.scaleDensity)

        val token = DataManager.token
        if (TextUtils.isEmpty(token)) {
            LogUtil.i("before login")
            LoginUtil.toLoginActivity()
            return
        }
        initTab()
        initLockService()
//        initSDKManager()


        initGrant()
        setMessagePoint()
        EventBusUtil.register(eventMsg)


//        Handler().postDelayed(Runnable() {
//            //todo test
////            onRinging("test")
//            startActivity(Intent(this,VideoCallActivity::class.java))
//        }, 2000)

        if (SipService.isReady()) {
            onServiceReady()
        } else {
            // ??????SipService
            startService(Intent(Intent.ACTION_MAIN).setClass(
                    this, SipService::class.java))
            ServiceWaitThread().start()
        }
//        var lockNumber: String? = DataManager.sip!!.sipAddr
//        var lockDisplayName: String? = DataManager.sip!!.sipDisplayname
//
//        try {
//            SipCoreManager.getInstance().newOutgoingCall(lockNumber, lockDisplayName)
//        } catch (e: LinphoneCoreException) {
//            SipCoreManager.getInstance().terminateCall()
//            LogUtil.e("???????????????")
//            e.printStackTrace()
//        }
    }

    /**
     * SipService?????????????????????????????????????????????Activity?????????????????????????????????
     */
    private fun onServiceReady() {
        SipService.instance().setActivityToLaunchOnIncomingReceived(this::class.java)
        // ??????Native????????????????????????
        val mListener = object : LinphoneCoreListenerBase() {
            override fun registrationState(lc: LinphoneCore?, proxy: LinphoneProxyConfig?,
                                           state: LinphoneCore.RegistrationState?, smessage: String?) {
                LogUtil.e("onServiceReady registrationState state = " + state!!.toString() + " message = " + smessage)
                // ??????????????????????????????????????????????
                if (state == LinphoneCore.RegistrationState.RegistrationCleared) {
                    if (lc != null) {
                        // ????????????????????????????????????
                        val authInfo = lc.findAuthInfo(proxy!!.identity, proxy.realm, proxy.domain)
                        // ???????????????????????????
                        if (authInfo != null)
                            lc.removeAuthInfo(authInfo)
                    }
                }

                // ?????????????????????Proxy?????????????????????proxy??????????????????????????????????????????????????????
                if (state == LinphoneCore.RegistrationState.RegistrationFailed) {
                    if (proxy!!.error === Reason.BadCredentials) {
                        displayCustomToast(getString(R.string.error_bad_credentials), Toast.LENGTH_LONG)
                    }
                    if (proxy!!.error === Reason.Unauthorized) {
                        displayCustomToast(getString(R.string.error_unauthorized), Toast.LENGTH_LONG)
                    }
                    if (proxy!!.error === Reason.IOError) {
                        displayCustomToast(getString(R.string.error_io_error), Toast.LENGTH_LONG)
                    }
                }
            }

            override fun callState(lc: LinphoneCore?, call: LinphoneCall?, state: LinphoneCall.State?, message: String?) {
                LogUtil.e("onServiceReady callState = ${state},message=${message}")
                if (state === LinphoneCall.State.IncomingReceived) {    // ??????CallIncomingActivity
                    if (!VideoCallActivity.started) {
                        VideoCallActivity.started = true
//                        EventBusUtil.post(EventWakeup())
                        acquireWakeLock()
                        releaseWakeLock()
                        startActivity(Intent(this@MainActivity, VideoCallActivity::class.java))
                    }

                } else if (state === LinphoneCall.State.OutgoingInit || state === LinphoneCall.State.OutgoingProgress) {
                    // ??????CallOutgoingActivity
//                    startActivity(Intent(this@MainActivity, CallOutgoingActivity::class.java))
                } else if (state === LinphoneCall.State.CallEnd || state === LinphoneCall.State.Error || state === LinphoneCall.State.CallReleased) {
                    if (message != null && call!!.errorInfo.reason === Reason.Declined) {    // ??????
//                        displayCustomToast(getString(R.string.error_call_declined), Toast.LENGTH_SHORT)
                    } else if (message != null && call!!.reason === Reason.NotFound) {    // ?????????????????????
                        displayCustomToast(getString(R.string.error_user_not_found), Toast.LENGTH_SHORT)
                    } else if (message != null && call!!.reason === Reason.Media) {    // ????????????????????????????????????
                        displayCustomToast(getString(R.string.error_incompatible_media), Toast.LENGTH_SHORT)
                    } else if (message != null && state === LinphoneCall.State.Error) {    // ???????????????
//                        displayCustomToast(getString(R.string.error_unknown) + " - " + message, Toast.LENGTH_SHORT)
                        displayCustomToast(getString(R.string.error_busy),Toast.LENGTH_LONG)
                    }
                }
            }
        }

        // ????????????????????????Native??????LinhoneCore?????????????????????
        val lc = SipCoreManager.getLcIfManagerNotDestroyedOrNull()
        lc?.addListener(mListener)
    }

    private fun displayCustomToast(string: String, lengthLong: Int) {
        ToastUtil.showShort(this, string)
    }

    /**
     * ??????SipService??????????????????SipService????????????????????????????????????
     * @author Administrator
     */
    private inner class ServiceWaitThread : Thread() {
        override fun run() {
            while (!SipService.isReady()) {
                try {
                    sleep(30)
                } catch (e: InterruptedException) {
                    throw RuntimeException("waiting thread sleep() " + "has been interrupted")
                }

            }
            runOnUiThread { onServiceReady() }
        }
    }

    private fun initTab() {

        mFragments[0] = findFragment(HomeFragment::class.java)//restored from savedinstance
        mFragments[1] = findFragment(SafeFragment::class.java)
        mFragments[2] = findFragment(FamilyFragment::class.java)
        mFragments[3] = findFragment(ProperyFragment::class.java)
        mFragments[4] = findFragment(MessageFragment::class.java)
        mFragments[5] = findFragment(MarketFragment::class.java)
        mFragments[6] = findFragment(PromoteFragment::class.java)
        mFragments[7] = findFragment(SettingFragment::class.java)
        if (mFragments[0] == null) {
            mFragments[0] = HomeFragment.newInstance()
            mFragments[1] = SafeFragment.newInstance()
//            mFragments[2] = FamilyFragment.newInstance()
            mFragments[2] = ProperyFragment.newInstance()
            mFragments[3] = PromoteFragment.newInstance()
            mFragments[4] = MessageFragment.newInstance()
//            mFragments[6] = MarketFragment.newInstance()
            mFragments[5] = SettingFragment.newInstance()

            loadMultipleRootFragment(R.id.container, currentTab,
                    mFragments[0],
                    mFragments[1],
//                    mFragments[2],????????????
                    mFragments[2],
                    mFragments[3],
                    mFragments[4],
//                    mFragments[6], ????????????
                    mFragments[5]
            )
        }

        main_tab_bar.addItem(VerticalTabItem(this, R.mipmap.icon_leftnav_home_default, R.mipmap.icon_leftnav_home_selected, R.string.home))
                .addItem(VerticalTabItem(this, R.mipmap.icon_leftnav_safe_default, R.mipmap.icon_leftnav_safe_selected, R.string.visual_security))
//                .addItem(VerticalTabItem(this, R.mipmap.icon_leftnav_smart_default, R.mipmap.icon_leftnav_smart_selected, R.string.intelligent_home))
                .addItem(VerticalTabItem(this, R.mipmap.icon_leftnav_serve_default, R.mipmap.icon_leftnav_serve_selected, R.string.property_service))

                .addItem(VerticalTabItem(this, R.mipmap.promotion, R.mipmap.promotion_highlight, R.string.message_promote))
                .addItem(VerticalTabItem(this, R.mipmap.icon_leftnav_news_deafult, R.mipmap.icon_leftnav_news_selected, R.string.message_notify))
//                .addItem(VerticalTabItem(this, R.mipmap.icon_leftnav_market_default, R.mipmap.icon_leftnav_market_selected, R.string.market_service))
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
        PermissionsManager.instance.requestPermissionsIfNecessaryForResult(this@MainActivity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA),
                object : PermissionsResultAction() {
                    override fun onGranted() {
                    }

                    override fun onDenied(permission: String) {
                        showToast("?????????????????????")
                    }
                })
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

    private fun releaseWakeLock() {
        mWakelock?.release()
    }

    private fun acquireWakeLock() {
        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        LogUtil.i("powerManager.isInteractive " + powerManager.isInteractive)
        if (powerManager != null && powerManager.isInteractive) {//???????????????
            mWakelock = powerManager.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP or PowerManager.SCREEN_DIM_WAKE_LOCK, packageName) // this target for tell OS which app
            mWakelock?.acquire(10 * 60 * 1000L /*10 minutes*/)//can not be reused,so create it every time
            LogUtil.i("mWakelock.acquire")
        }
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
        tabFragment?.switchTab(childPos)
                ?: (mFragments[pos] as? BaseMainFragment)?.switchTab(childPos)
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
        releaseWakeLock()
    }

    companion object {

        const val POSITION_SAFE = 1
        const val POSITION_FAMILY = 2
        const val POSITION_PROPERTY = 2
        const val POSITION_MESSAGE = 4
        const val POSITION_MARKET = 5

        // ????????????????????????????????????
        private const val WAIT_TIME = 2000L
        internal const val POSITION_KEY = "position"
    }
}
