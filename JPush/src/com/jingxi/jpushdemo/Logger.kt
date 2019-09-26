package com.jingxi.jpushdemo

import android.util.Log

import com.wujia.lib_common.utils.LogUtil

/**
 * Created by efan on 2017/4/13.
 */

object Logger {

    //设为false关闭日志
    private val LOG_ENABLE = true

    fun i(tag: String, msg: String) {
        if (LOG_ENABLE) {
            LogUtil.info(tag, msg)
        }
    }

    fun v(tag: String, msg: String) {
        if (LOG_ENABLE) {
            Log.v(tag, msg)
        }
    }

    fun d(tag: String, msg: String) {
        if (LOG_ENABLE) {
            LogUtil.debug(tag, msg)
        }
    }

    fun w(tag: String, msg: String) {
        if (LOG_ENABLE) {
            LogUtil.warn(tag, msg)
        }
    }

    fun e(tag: String, msg: String) {
        if (LOG_ENABLE) {
            LogUtil.error(tag, msg)
        }
    }

}
