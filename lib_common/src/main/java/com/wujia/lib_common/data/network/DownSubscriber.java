package com.wujia.lib_common.data.network;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Created by xmren on 2018/3/9.
 */

public class DownSubscriber<ResponseBody> implements Subscriber<ResponseBody> {
    DownloadCallBack callBack;
    String saveFilePath;

    public DownSubscriber(String saveFilePath, DownloadCallBack callBack) {
        this.saveFilePath = saveFilePath;
        this.callBack = callBack;
    }


    @Override
    public void onError(Throwable e) {
        if (callBack != null) {
            callBack.onError(e);
        }
    }

    @Override
    public void onComplete() {
        if (callBack != null) {
            callBack.onCompleted();
        }
    }

    @Override
    public void onSubscribe(Subscription s) {
        if (callBack != null) {
            callBack.onStart();
        }
    }

    @Override
    public void onNext(ResponseBody responseBody) {
        DownLoadManager.getInstance(callBack).writeResponseBodyToDisk(saveFilePath, (okhttp3.ResponseBody) responseBody);
    }
}
