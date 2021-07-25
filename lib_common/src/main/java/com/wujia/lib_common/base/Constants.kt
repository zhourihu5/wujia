package com.wujia.lib_common.base

import com.wujia.lib_common.BuildConfig

/**
 * Created by xmren on 2018/5/4.
 */

object Constants {
    val BASE_URL: String

    const val HTTP_SUCESS = "200"

    const val COMMON_REQUEST_TOKEN = "accessToken"


    //    Intent key
    const val ARG_PARAM_1 = "ARG_PARAM_1"
    const val INTENT_KEY_1 = "INTENT_KEY_1"
    const val INTENT_KEY_2 = "INTENT_KEY_2"
    const val INTENT_KEY_3 = "INTENT_KEY_3"
    const val INTENT_KEY_4 = "INTENT_KEY_4"
    const val INTENT_KEY_5 = "INTENT_KEY_5"
    const val SP_KEY_USER = "SP_KEY_USER"

    init {
        BASE_URL = if (BuildConfig.DEBUG) {
            "https://api.home-guard.cn"
    //            BASE_URL = "http://192.168.250.6:8081"
    //            BASE_URL = "http://192.168.250.16:8181"
    //            BASE_URL = "http://testapi.home-guard.cn"
        } else {
            "https://api.home-guard.cn"
    //            BASE_URL = "http://192.168.250.8:8081"
        }
    }
}
