package com.wujia.lib_common.data.network

import com.wujia.lib_common.data.network.exception.ExceptionEngine
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription


/**
 * Created by xmren on 2018/3/9.
 */

class SimpleSubscriber<T : BaseResponse>(internal val callBack: HttpCallBack<T>?) : Subscriber<T> {

    override fun onSubscribe(s: Subscription) {
        callBack?.onStart()
    }

    override fun onNext(t: T?) {
        if (t != null) {
            if (t.isSuccess!!) {
                callBack?.onResponse(t)
            } else {
                callBack?.onFailed(t.responseCode!!, t.errorMsg!!)
            }
        }
    }

    override fun onError(t: Throwable) {
        val apiException = ExceptionEngine.handleException(t)
        callBack?.onFailed(apiException.code!!, apiException.message!!)
    }

    override fun onComplete() {

    }
}
