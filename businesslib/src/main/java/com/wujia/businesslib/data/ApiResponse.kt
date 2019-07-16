package com.wujia.businesslib.data

import com.wujia.lib_common.base.Constants
import com.wujia.lib_common.data.network.BaseResponse

class ApiResponse<T> : BaseResponse() {
    var data: T? = null
    var code: String? = null
    var msg: String? = null

    override fun isSuccess(): Boolean {
        return Constants.HTTP_SUCESS == code
    }

    override fun getResponseCode(): String? {
        return code
    }

    override fun getErrorMsg(): String? {
        return msg
    }
}
