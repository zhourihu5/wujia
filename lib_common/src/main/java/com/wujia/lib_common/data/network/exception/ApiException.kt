package com.wujia.lib_common.data.network.exception


class ApiException : Exception {
    var code: String?=null

    val isNetworkConnectError: Boolean
        get() = code === ERROR.NETWORK_CONNECT_ERROR

    constructor(throwable: Throwable) : super(throwable.message, throwable) {}

    constructor(throwable: Throwable, code: String, message: String?) : super(message, throwable) {
        this.code = code
    }

}

