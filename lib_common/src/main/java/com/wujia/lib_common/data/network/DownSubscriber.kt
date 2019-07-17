package com.wujia.lib_common.data.network

import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

/**
 * Created by xmren on 2018/3/9.
 */

class DownSubscriber<ResponseBody>(internal var saveFilePath: String, internal var callBack: DownloadCallBack?) : Subscriber<ResponseBody> {


    override fun onError(e: Throwable) {
        if (callBack != null) {
            callBack!!.onError(e)
        }
    }

    override fun onComplete() {
        if (callBack != null) {
            callBack!!.onCompleted()
        }
    }

    override fun onSubscribe(s: Subscription) {
        if (callBack != null) {
            callBack!!.onStart()
        }
    }

    override fun onNext(responseBody: ResponseBody) {
        DownLoadManager.getInstance(callBack).writeResponseBodyToDisk(saveFilePath, responseBody as okhttp3.ResponseBody)
    }
}
