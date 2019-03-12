package com.wujia.businesslib.data;

import com.wujia.businesslib.Constants;
import com.wujia.lib_common.data.network.BaseResponse;

public class ApiResponse<T> extends BaseResponse {
    public T data;
    public int code;
    public String msg;

    @Override
    public boolean isSuccess() {
        return code == Constants.HTTP_SUCESS;
    }

    @Override
    public int getResponseCode() {
        return code;
    }

    @Override
    public String getErrorMsg() {
        return msg;
    }
}
