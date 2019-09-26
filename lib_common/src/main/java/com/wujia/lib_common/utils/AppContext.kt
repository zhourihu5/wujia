package com.wujia.lib_common.utils

import android.content.Context

object AppContext {
    var mAppContext: Context? = null


    fun init(context: Context) {
        if (mAppContext == null) {
            mAppContext = context.applicationContext
        } else {
            throw IllegalStateException("set context duplicate")
        }
    }

    fun get(): Context {
        return if (mAppContext == null) {
            throw IllegalStateException("forget init?")
        } else {
            mAppContext!!
        }
    }
}
