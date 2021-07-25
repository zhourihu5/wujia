package com.jingxi.jpushdemo

import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Looper
import android.text.TextUtils
import android.widget.Toast
import cn.jpush.android.api.JPushInterface
import java.util.regex.Pattern

object ExampleUtil {
    private const val KEY_APP_KEY = "JPUSH_APPKEY"

    /**
     * 只能以 “+” 或者 数字开头；后面的内容只能包含 “-” 和 数字。
     */
    private const val MOBILE_NUMBER_CHARS = "^[+0-9][-0-9]{1,}$"

    // 校验Tag Alias 只能是数字,英文字母和中文
    fun isValidTagAndAlias(s: String): Boolean {
        val p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_!@#$&*+=.|]+$")
        val m = p.matcher(s)
        return m.matches()
    }

    fun showToast(toast: String, context: Context) {
        Thread({
            Looper.prepare()
            Toast.makeText(context, toast, Toast.LENGTH_SHORT).show()
            Looper.loop()
        }).start()
    }

    fun isConnected(context: Context): Boolean {
        val conn = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = conn.activeNetworkInfo
        return info != null && info.isConnected
    }

//    fun getImei(context: Context, imei: String): String? {
//        var ret: String? = null
//        try {
//            val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
//            ret = telephonyManager.deviceId
//        } catch (e: Exception) {
//            Logger.e(ExampleUtil::class.java.simpleName, e.message)
//        }
//
//        return if (isReadableASCII(ret)) {
//            ret
//        } else {
//            imei
//        }
//    }

    fun getDeviceId(context: Context): String {
        return JPushInterface.getUdid(context)
    }
}
