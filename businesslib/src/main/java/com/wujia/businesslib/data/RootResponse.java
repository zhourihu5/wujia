package com.wujia.businesslib.data;

import com.wujia.businesslib.Constants;
import com.wujia.lib_common.data.network.BaseResponse;

public class RootResponse extends BaseResponse {
    public int errcode;
    public String errmsg;

    @Override
    public boolean isSuccess() {
        return errcode == Constants.HTTP_SUCESS;
    }

    @Override
    public int getResponseCode() {
        return errcode;
    }

    @Override
    public String getErrorMsg() {
        return errmsg;
    }
}
