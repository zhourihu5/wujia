package com.abctime.lib_common.data.network;

import com.abctime.lib_common.data.network.exception.ApiException;
import com.abctime.lib_common.data.network.exception.ExceptionEngine;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;



/**
 * Created by xmren on 2018/3/9.
 */

public class SimpleSubscriber<T extends BaseResponse> implements Subscriber<T> {
    final HttpCallBack<T> callBack;

    public SimpleSubscriber(HttpCallBack<T> callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onSubscribe(Subscription s) {
        if (callBack != null) {
            callBack.onStart();
        }
    }

    @Override
    public void onNext(T t) {
        if (t != null) {
            if (t.isSuccess()) {
                if (callBack != null) {
                    callBack.onResponse(t);
                }
            } else {
                if (callBack != null) {
                    callBack.onFailed(t.getResponseCode(), t.getErrorMsg());
                }
            }
        }
    }

    @Override
    public void onError(Throwable t) {
        ApiException apiException = ExceptionEngine.handleException(t);
        if (callBack != null) {
            callBack.onFailed(apiException.code, apiException.getMessage());
        }
    }

    @Override
    public void onComplete() {

    }
}
