package com.wujia.lib_common.base;

import android.text.TextUtils;

import com.wujia.lib_common.data.network.BaseResponse;

import java.io.Serializable;

public class RootResponse extends BaseResponse implements Serializable {
//    public String errcode;
    public String code;
    public String msg;
    public String message;

    @Override
    public boolean isSuccess() {
        return  Constants.HTTP_SUCESS.equals(code) ;
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
