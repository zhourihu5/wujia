package com.wujia.businesslib.data;

import com.wujia.businesslib.Constants;
import com.wujia.lib_common.data.network.BaseResponse;

import java.io.Serializable;

public class RootResponse extends BaseResponse implements Serializable {
    public String errcode;
    public String code;
    public String errmsg;

    @Override
    public boolean isSuccess() {
        return Constants.HTTP_SUCESS.equals(errcode) || Constants.HTTP_SUCESS.equals(code);
    }

    @Override
    public String getResponseCode() {
        return errcode;
    }

    @Override
    public String getErrorMsg() {
        return errmsg;
    }
}
