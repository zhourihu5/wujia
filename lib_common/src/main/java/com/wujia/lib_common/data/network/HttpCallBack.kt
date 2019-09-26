package com.wujia.lib_common.data.network

/**
 * Created by xmren on 2018/3/9.
 */

interface HttpCallBack<T : BaseResponse> {
    fun onStart()

    fun onFailed(code: String, message: String)

    fun onResponse(response: T)
}
