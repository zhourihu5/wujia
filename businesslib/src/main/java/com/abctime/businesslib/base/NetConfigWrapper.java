package com.abctime.businesslib.base;

import com.abctime.businesslib.BuildConfig;
import com.abctime.lib_common.data.network.NetConfig;

import okhttp3.Interceptor;

/**
 * description:
 * author: KisenHuang
 * email: KisenHuang@163.com
 * time: 2018/7/2 下午5:11
 */

public class NetConfigWrapper {

    public static NetConfig create() {
        NetConfig.debug = BuildConfig.DEBUG;
        NetConfig.signKey = BuildConfig.PARAM_SIGN_KEY;
        NetConfig config = new NetConfig();
        config.configInterceptors(new Interceptor[]{new HttpLogInterceptor(), new BusinessInterceptor()});
        return config;
    }

}
