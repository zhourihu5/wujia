package com.wujia.businesslib.base;

import com.wujia.businesslib.Constants;
import com.wujia.lib_common.data.network.NetConfig;

import okhttp3.Interceptor;


public class NetConfigWrapper {

    public static NetConfig create() {

        return create(Constants.BASE_URL);
    }

    public static NetConfig create(String baseUrl) {
//        NetConfig.debug = BuildConfig.DEBUG;
        NetConfig.debug = true;
        NetConfig config = new NetConfig();
        config.configInterceptors(new Interceptor[]{ new TokenInterceptor()});
        config.baseURL = baseUrl;
        return config;
    }

}
