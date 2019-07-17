package com.wujia.lib_common.base

import com.wujia.lib_common.BuildConfig

/**
 * Created by xmren on 2018/5/4.
 */

object Constants {
    val BASE_URL: String

    val HTTP_SUCESS = "200"

    val APPID = "18bf4301ea0f42a08d96fc665a5c8c85"
    val SECRET = "16f2e25f4fe94e6ea27e645a33313011"
    val COMMON_REQUEST_TOKEN = "accessToken"


    //    Intent key
    val ARG_PARAM_1 = "ARG_PARAM_1"
    val INTENT_KEY_1 = "INTENT_KEY_1"
    val INTENT_KEY_2 = "INTENT_KEY_2"
    val INTENT_KEY_3 = "INTENT_KEY_3"
    val INTENT_KEY_4 = "INTENT_KEY_4"
    val INTENT_KEY_5 = "INTENT_KEY_5"
    val SP_KEY_USER = "SP_KEY_USER"

    init {
        if (BuildConfig.DEBUG) {
            //            BASE_URL = "http://api.home-guard.cn";
            BASE_URL = "http://192.168.1.100:8081"
        } else {
            //            BASE_URL = "http://39.97.186.122:8081"
            BASE_URL = "http://api.home-guard.cn"
        }
    }
}
