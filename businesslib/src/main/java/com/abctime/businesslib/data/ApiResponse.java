package com.abctime.businesslib.data;

import com.abctime.businesslib.Constants;
import com.abctime.lib_common.data.network.BaseResponse;

/**
 * Created by xmren on 2018/5/4.
 */

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
