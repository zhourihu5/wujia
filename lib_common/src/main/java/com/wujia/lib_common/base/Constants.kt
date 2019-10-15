package com.wujia.lib_common.base

import com.wujia.lib_common.BuildConfig

/**
 * Created by xmren on 2018/5/4.
 */

object Constants {
    val BASE_URL: String

    val HTTP_SUCESS = "200"

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
//            BASE_URL = "https://api.home-guard.cn"
//            BASE_URL = "http://192.168.250.6:8081"
            BASE_URL = "http://192.168.250.16:8181"
//            BASE_URL = "http://testapi.home-guard.cn"
        } else {
            BASE_URL = "https://api.home-guard.cn"
//            BASE_URL = "http://192.168.250.8:8081"
        }
    }
}
