package com.wujia.businesslib.data

import com.wujia.lib_common.base.Constants
import com.wujia.lib_common.data.network.BaseResponse

class ApiResponse<T> : BaseResponse() {
    override val isSuccess: Boolean?
        get() = Constants.HTTP_SUCESS == code
    override val responseCode: String?
        get() = code
    override val errorMsg: String?
        get() = msg
    var data: T? = null
    var code: String? = null
    var msg: String? = null

}
