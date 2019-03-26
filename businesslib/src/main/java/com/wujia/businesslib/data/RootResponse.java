package com.wujia.businesslib.data;

import com.wujia.businesslib.Constants;
import com.wujia.lib_common.data.network.BaseResponse;

import java.io.Serializable;

public class RootResponse extends BaseResponse implements Serializable {
    public int errcode;
    public int code;
    public String errmsg;

    @Override
    public boolean isSuccess() {
        return errcode == Constants.HTTP_SUCESS || code == Constants.HTTP_SUCESS;
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
