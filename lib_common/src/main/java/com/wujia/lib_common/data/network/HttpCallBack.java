package com.wujia.lib_common.data.network;

/**
 * Created by xmren on 2018/3/9.
 */

public interface HttpCallBack<T extends BaseResponse> {
    public void onStart();

    public abstract void onFailed(String code, String message);

    public abstract void onResponse(T response);
}
