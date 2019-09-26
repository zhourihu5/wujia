package com.wujia.businesslib.base

import com.wujia.lib_common.base.Constants
import com.wujia.lib_common.data.network.NetConfig

import okhttp3.Interceptor


object NetConfigWrapper {

    @JvmOverloads
    fun create(baseUrl: String = Constants.BASE_URL): NetConfig {
        //        NetConfig.debug = BuildConfig.DEBUG;
        NetConfig.debug = true
        val config = NetConfig()
        config.configInterceptors(arrayOf<Interceptor>(TokenInterceptor()))
        config.baseURL = baseUrl
        return config
    }

}
