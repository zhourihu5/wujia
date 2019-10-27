package com.wujia.businesslib.base


import android.app.Activity
import android.app.Application
import android.os.Bundle

import com.wujia.lib_common.utils.AppContext
import com.wujia.lib_common.utils.SystemUtil

import java.lang.ref.WeakReference
import java.util.concurrent.atomic.AtomicInteger

abstract class BaseApplication : Application() {
    internal var TAG = "wujia"

    override fun onCreate() {
        super.onCreate()
        instance = this
        AppContext.init(instance)
        initSDKManager()

        SystemUtil.init()


        registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            internal var mFinalCount = AtomicInteger(0)

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                currentActivity = WeakReference(activity)
            }

            override fun onActivityStarted(activity: Activity) {
                //说明从后台回到了前台
                if (mFinalCount.incrementAndGet() == 1) {
                    runInForeGround()
                }


            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {
                //说明从前台回到了后台
                if (mFinalCount.decrementAndGet() <= 0) {//fixme some times more than one,maybe we have start the same
                    runInbackGround()
                }
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

            }

            override fun onActivityDestroyed(activity: Activity) {

            }
        })
    }

    protected abstract fun runInbackGround()

    protected abstract fun runInForeGround()
    //    public static boolean isInbackground(){
    //        return mFinalCount<=0;
    //    }

    private fun initSDKManager() {
//        JXPadSdk.init(instance)
//        JXPadSdk.initDoorAccess()
        //        JXPadSdk.initPushManager();//todo replace it by our push，原来的push APPkey等信息是写在manifest里的，


        // 如果需要更改为我们自己的推送，用代码住处推送账号信息

        //        String appKey="8bc0518b5aa55c176ffbcbcb";
        //        String appSecret="52251687bfd0b5dcebda580d";
        //        PushServiceFactory.init(instance);
        //        CloudPushService pushService = PushServiceFactory.getCloudPushService();
        //        pushService.register(instance,appKey,appSecret, new CommonCallback() {
        //            @Override
        //            public void onSuccess(String response) {
        //                JXLogUtil.logW(TAG, "init success ");
        //            }
        //
        //            @Override
        //            public void onFailed(String errorCode, String errorMessage) {
        //                JXLogUtil.logW(TAG, "init faild errorCode = " + errorCode + " errorMessage = " + errorMessage);
        //            }
        //        });
    }

    companion object {

        protected lateinit var instance: BaseApplication
        //    private static volatile int mFinalCount;
        protected var currentActivity: WeakReference<Activity>? = null

        val currentAcitivity: Activity?
            get() = if (currentActivity != null) {
                currentActivity!!.get()
            } else null
    }
}
