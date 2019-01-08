package com.wujia.lib_common.data.network;

/**
 * Created by xmren on 2018/3/9.
 */

public abstract class BaseResponse {
    public abstract boolean isSuccess();

    public abstract int getResponseCode();

    public abstract String getErrorMsg();

}
