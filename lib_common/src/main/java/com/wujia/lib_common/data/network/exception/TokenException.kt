package com.wujia.lib_common.data.network.exception


class TokenException : RuntimeException {

    constructor(throwable: Throwable) : super(throwable.message, throwable) {}

    constructor(message: String) : super(message) {}


}

