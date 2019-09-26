package com.wujia.lib_common.base

import com.wujia.lib_common.data.network.BaseResponse

import java.io.Serializable

open class RootResponse : BaseResponse(), Serializable {
    private val msg: String?=null
    var code: String?=null

    //    public String errcode;
    override var responseCode: String? = null
        get() = code
    override var errorMsg: String? = null
        get() = msg
    var message: String? = null

    override val isSuccess: Boolean
        get() = Constants.HTTP_SUCESS == responseCode
}
