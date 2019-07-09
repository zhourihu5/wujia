package com.wujia.businesslib.data;

import com.wujia.lib_common.base.Constants;
import com.wujia.lib_common.data.network.BaseResponse;

public class ApiResponse<T> extends BaseResponse {
    public T data;
    public String code;
    public String msg;

    @Override
    public boolean isSuccess() {
        return Constants.HTTP_SUCESS.equals(code);
    }

    @Override
    public String getResponseCode() {
        return code;
    }

    @Override
    public String getErrorMsg() {
        return msg;
    }
}
