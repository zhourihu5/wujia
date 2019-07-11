package com.jingxi.smartlife.pad.host

import android.content.Intent
import cn.jpush.android.api.JPushInterface
import com.jingxi.smartlife.pad.BuildConfig
import com.jingxi.smartlife.pad.mvp.FloatingButtonService
import com.jingxi.smartlife.pad.mvp.Util
import com.squareup.leakcanary.LeakCanary
import com.umeng.commonsdk.UMConfigure
import com.wujia.businesslib.HookUtil
import com.wujia.businesslib.base.BaseApplication
import com.wujia.lib_common.utils.NetworkUtil

/**
 * author ：shenbingkai
 * date ：2019-01-08
 * description ：
 */
class HostApp : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)

        NetworkUtil.getNetWork(BaseApplication.instance)
        HookUtil.hookWebView()
        //        HookUtil.fixFocusedViewLeak(this);
        JPushInterface.setDebugMode(BuildConfig.DEBUG)    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this)            // 初始化 JPush

        //方便错误统计，方便查看错误日志，便于修复bug
        UMConfigure.init(this, "5d0ddf284ca357c8dc000dd6", "mychanel", UMConfigure.DEVICE_TYPE_PHONE, null)//友盟统计，周日虎的账号
        Util.updateVesion

        Util.initXcrash(this)

    }



    override fun runInbackGround() {
        startService(Intent(this, FloatingButtonService::class.java))
    }

    override fun runInForeGround() {
        stopService(Intent(this, FloatingButtonService::class.java))
    }

}
