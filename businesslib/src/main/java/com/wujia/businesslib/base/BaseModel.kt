package com.wujia.businesslib.base

import com.wujia.lib_common.data.network.HttpHelper

/**
 * author ：shenbingkai
 * date ：2019-01-12
 * description ：
 */
open class BaseModel {

    protected lateinit var mHttpHelper: HttpHelper

    init {
        createHttp()
    }

    protected fun createHttp() {
        mHttpHelper = HttpHelper.Builder(NetConfigWrapper.create())
                .build()
    }
}
