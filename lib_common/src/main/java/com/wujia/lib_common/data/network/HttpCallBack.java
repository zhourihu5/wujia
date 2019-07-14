package com.wujia.lib_common.data.network;

/**
 * Created by xmren on 2018/3/9.
 */

public interface HttpCallBack<T extends BaseResponse> {
    void onStart();

    void onFailed(String code, String message);

    void onResponse(T response);
}
