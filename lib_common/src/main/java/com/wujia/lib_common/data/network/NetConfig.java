package com.wujia.lib_common.data.network;

import okhttp3.CookieJar;
import okhttp3.Interceptor;

/**
 * Created by xmren on 2018/5/4.
 */

public class NetConfig {
    public Interceptor[] mInterceptors;
    public CookieJar mCookieJar;
    public long connectTimeoutMills;
    public long readTimeoutMills;
    public boolean isHasLog;
    public boolean isUseRx = true;
    public String baseURL = "";
    public boolean isUseMultiBaseURL = true;
    public static boolean debug;
    public static String signKey;

    /**
     * add okhttp Interceptors
     * @param configInterceptors
     * @return
     */
    public NetConfig configInterceptors(Interceptor[] configInterceptors) {
        this.mInterceptors = configInterceptors;
        return this;
    }

    public NetConfig configisUseMultiBaseURL(boolean isUseMultiBaseURL) {
        this.isUseMultiBaseURL = isUseMultiBaseURL;
        return this;
    }

    public NetConfig configBaseURL(String baseURL) {
        this.baseURL = baseURL;
        return this;
    }

    /**
     * config cookieManager
     * @param mCookieJar
     * @return
     */
    public NetConfig configCookieJar(CookieJar mCookieJar) {
        this.mCookieJar = mCookieJar;
        return this;
    }


    public NetConfig configConnectTimeoutMills(long connectTimeoutMills) {
        this.connectTimeoutMills = connectTimeoutMills;
        return this;
    }

    public NetConfig configReadTimeoutMills(long readTimeoutMills) {
        this.readTimeoutMills = readTimeoutMills;
        return this;
    }

    public NetConfig configLogEnable(boolean isHasLog) {
        this.isHasLog = isHasLog;
        return this;
    }

    public NetConfig configIsUseRx(boolean isUseRx) {
        this.isUseRx = isUseRx;
        return this;
    }


}
