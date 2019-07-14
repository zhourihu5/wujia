package com.wujia.lib_common.base;

import com.wujia.lib_common.BuildConfig;

/**
 * Created by xmren on 2018/5/4.
 */

public class Constants {
    public static final String BASE_URL;

    static {
        if(BuildConfig.DEBUG){
//            BASE_URL = "http://api.home-guard.cn";
            BASE_URL = "http://192.168.1.100:8081";
        }else {
//            BASE_URL = "http://39.97.186.122:8081"
            BASE_URL = "http://api.home-guard.cn";
        }
    }

    public static final String HTTP_SUCESS = "200";

    public static final String APPID = "18bf4301ea0f42a08d96fc665a5c8c85";
    public static final String SECRET = "16f2e25f4fe94e6ea27e645a33313011";
    public static final String COMMON_REQUEST_TOKEN = "accessToken";


    //    Intent key
    public static final String ARG_PARAM_1 = "ARG_PARAM_1";
    public static final String INTENT_KEY_1 = "INTENT_KEY_1";
    public static final String INTENT_KEY_2 = "INTENT_KEY_2";
    public static final String INTENT_KEY_3 = "INTENT_KEY_3";
    public static final String INTENT_KEY_4 = "INTENT_KEY_4";
    public static final String INTENT_KEY_5 = "INTENT_KEY_5";
    public static final String SP_KEY_USER = "SP_KEY_USER";
}
