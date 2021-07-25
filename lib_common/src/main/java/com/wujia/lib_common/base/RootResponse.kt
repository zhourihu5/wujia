package com.wujia.lib_common.base

import com.wujia.lib_common.data.network.BaseResponse

import java.io.Serializable

open class RootResponse : BaseResponse(), Serializable {
    private val msg: String?=null
    var code: String?=null

    //    public String errcode;
    override val responseCode: String?
        get() = code
    override val errorMsg: String?
        get() = msg
    var message: String? = null

    override val isSuccess: Boolean
        get() = Constants.HTTP_SUCESS == responseCode
}
