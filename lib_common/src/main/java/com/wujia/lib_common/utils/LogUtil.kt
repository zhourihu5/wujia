package com.wujia.lib_common.utils

import android.util.Log

object LogUtil {


    private val TAG = "--NoTAG--"
    //    private static boolean logSwitch = BuildConfig.DEBUG;
    private val logSwitch = true


    fun i(message: Any) {
        if (logSwitch) {
            Log.i(TAG, message.toString())
        }
    }

    fun e(message: Any) {
        if (logSwitch) {
            Log.e(TAG, message.toString())
        }
    }

    fun debug(tag: String, message: Any) {
        if (logSwitch) {
            Log.d(tag, message.toString())
        }
    }

    fun warn(tag: String, message: Any) {
        if (logSwitch) {
            Log.w(tag, message.toString())
        }
    }

    fun info(tag: String, message: Any) {
        if (logSwitch) {
            Log.i(tag, message.toString())
        }
    }

    fun error(tag: String, message: Any) {
        if (logSwitch) {
            Log.e(tag, message.toString())
        }
    }

    fun t(message: Any, t: Throwable) {
        if (logSwitch) {
            Log.e(TAG, message.toString(), t)
        }
    }

    fun tr(tag: String, message: Any, t: Throwable) {
        if (logSwitch) {
            Log.e(tag, message.toString(), t)
        }
    }

    fun d(message: Any) {
        if (logSwitch) {
            Log.d(TAG, message.toString())
        }
    }

    fun w(message: Any) {
        if (logSwitch) {
            Log.w(TAG, message.toString())
        }
    }
}
