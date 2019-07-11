package com.jingxi.smartlife.pad.mvp

import android.content.Context
import com.jingxi.smartlife.pad.BuildConfig
import com.wujia.businesslib.data.ApiResponse
import com.wujia.businesslib.model.BusModel
import com.wujia.lib_common.base.BaseView
import com.wujia.lib_common.data.network.SimpleRequestSubscriber
import com.wujia.lib_common.data.network.exception.ApiException
import com.wujia.lib_common.utils.LogUtil
import com.wujia.lib_common.utils.SystemUtil
import com.wujia.lib_common.utils.VersionUtil
import io.reactivex.disposables.Disposable
import org.json.JSONObject
import xcrash.ICrashCallback
import xcrash.TombstoneParser
import xcrash.XCrash
import java.io.File
import java.io.FileWriter

object Util {

    val updateVesion: Disposable
        get() {
            val versionName = VersionUtil.getVersionName()
            val key = SystemUtil.getSerialNum()
            val versionCode = VersionUtil.getVersionCode()
            return BusModel().updateVer(versionName, key, versionCode.toString() + "").subscribeWith(object : SimpleRequestSubscriber<ApiResponse<Any>>(object : BaseView {
                override fun showErrorMsg(msg: String) {

                }

                override fun showLoadingDialog(text: String) {

                }

                override fun hideLoadingDialog() {

                }

                override fun getContext(): Context? {
                    return null
                }

                override fun onLoginStatusError() {

                }
            }, ActionConfig(false, SHOWERRORMESSAGE)) {
                override fun onResponse(response: ApiResponse<Any>) {
                    super.onResponse(response)

                }

                override fun onFailed(apiException: ApiException) {
                    super.onFailed(apiException)
                }
            })
        }
    public fun initXcrash(context: Context) {
        if (BuildConfig.DEBUG) {
            // The callback when App process crashed.
            val callback = ICrashCallback { logPath, emergency ->
                LogUtil.e("logPath: " + (logPath ?: "(null)") + ", emergency: " + (emergency
                        ?: "(null)"))

                // Parse and save the crash info to a JSON file for debugging.
                var writer: FileWriter? = null
                try {
                    val debug = File(context.filesDir.toString() + "/tombstones/debug.json")
                    debug.createNewFile()
                    writer = FileWriter(debug, false)
                    writer.write(JSONObject(TombstoneParser.parse(logPath, emergency)).toString())
                } catch (e: Exception) {
                    LogUtil.t("debug failed", e)
                } finally {
                    if (writer != null) {
                        try {
                            writer.close()
                        } catch (ignored: Exception) {
                        }

                    }
                }
            }

            XCrash.init(context, XCrash.InitParameters()
                    .setAppVersion("1.2.3-beta456-patch789")
                    .setJavaRethrow(true)
                    .setJavaLogCountMax(10)
                    .setJavaDumpAllThreadsWhiteList(arrayOf("^main$", "^Binder:.*", ".*Finalizer.*"))
                    .setJavaDumpAllThreadsCountMax(10)
                    .setJavaCallback(callback)
                    .setNativeRethrow(true)
                    .setNativeLogCountMax(10)
                    .setNativeDumpAllThreadsWhiteList(arrayOf("^com\\.jingxi", "^Signal Catcher$", "^Jit thread pool$", ".*(R|r)ender.*", ".*Chrome.*"))
                    .setNativeDumpAllThreadsCountMax(10)
                    .setNativeCallback(callback)
                    .setPlaceholderCountMax(3)
                    .setPlaceholderSizeKb(512)
                    .setLogFileMaintainDelayMs(1000))
        }
    }

}
