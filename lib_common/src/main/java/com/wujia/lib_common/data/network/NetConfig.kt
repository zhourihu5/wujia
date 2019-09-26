package com.wujia.lib_common.data.network

import okhttp3.CookieJar
import okhttp3.Interceptor

/**
 * Created by xmren on 2018/5/4.
 */

class NetConfig {
    var mInterceptors: Array<Interceptor>?=null
    var mCookieJar: CookieJar?=null
    var connectTimeoutMills: Long = 0
    var readTimeoutMills: Long = 0
    var isHasLog: Boolean = false
    var isUseRx = true
    var baseURL = ""
    var isUseMultiBaseURL = true

    /**
     * add okhttp Interceptors
     *
     * @param configInterceptors
     * @return
     */
    fun configInterceptors(configInterceptors: Array<Interceptor>): NetConfig {
        this.mInterceptors = configInterceptors
        return this
    }

    fun configisUseMultiBaseURL(isUseMultiBaseURL: Boolean): NetConfig {
        this.isUseMultiBaseURL = isUseMultiBaseURL
        return this
    }

    fun configBaseURL(baseURL: String): NetConfig {
        this.baseURL = baseURL
        return this
    }

    /**
     * config cookieManager
     *
     * @param mCookieJar
     * @return
     */
    fun configCookieJar(mCookieJar: CookieJar): NetConfig {
        this.mCookieJar = mCookieJar
        return this
    }


    fun configConnectTimeoutMills(connectTimeoutMills: Long): NetConfig {
        this.connectTimeoutMills = connectTimeoutMills
        return this
    }

    fun configReadTimeoutMills(readTimeoutMills: Long): NetConfig {
        this.readTimeoutMills = readTimeoutMills
        return this
    }

    fun configLogEnable(isHasLog: Boolean): NetConfig {
        this.isHasLog = isHasLog
        return this
    }

    fun configIsUseRx(isUseRx: Boolean): NetConfig {
        this.isUseRx = isUseRx
        return this
    }

    companion object {
        var debug: Boolean = false
        var signKey: String? = null
    }


}
