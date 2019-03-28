package com.wujia.businesslib.base;

import com.wujia.businesslib.BuildConfig;
import com.wujia.businesslib.Constants;
import com.wujia.lib_common.data.network.NetConfig;

import okhttp3.Interceptor;


public class NetConfigWrapper {

    public static NetConfig create() {
        NetConfig.debug = BuildConfig.DEBUG;
//        NetConfig.signKey = BuildConfig.PARAM_SIGN_KEY;
        NetConfig config = new NetConfig();
        config.configInterceptors(new Interceptor[]{new BusinessInterceptor()});
        config.baseURL = Constants.BASE_URL;
        return config;
    }

}
