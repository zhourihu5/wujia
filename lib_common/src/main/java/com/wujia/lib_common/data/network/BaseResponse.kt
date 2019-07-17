package com.wujia.lib_common.data.network

/**
 * Created by xmren on 2018/3/9.
 */

abstract class BaseResponse {
    abstract val isSuccess: Boolean?

    abstract val responseCode: String?

    abstract val errorMsg: String?

}
