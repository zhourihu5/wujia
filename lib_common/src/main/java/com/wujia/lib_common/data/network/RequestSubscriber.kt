package com.wujia.lib_common.data.network

import android.text.TextUtils
import com.wujia.lib_common.base.BaseView
import com.wujia.lib_common.data.network.exception.ApiException
import com.wujia.lib_common.data.network.exception.ExceptionEngine
import com.wujia.lib_common.utils.AppContext
import com.wujia.lib_common.utils.NetworkUtil
import io.reactivex.subscribers.ResourceSubscriber
import java.net.ConnectException

abstract class RequestSubscriber<T> protected constructor(protected var view: BaseView?) : ResourceSubscriber<T>() {
    protected var actionConfig: SimpleRequestSubscriber.ActionConfig? = null

    override fun onStart() {
        if (!NetworkUtil.isNetAvailable(AppContext.get())) {//在无网络的时候直接抛出Error
            onError(ConnectException("网络连接失败，请检查网络"))
            onComplete()
            dispose()

            return
        }
        super.onStart()
        if (actionConfig!!.isShowLoading) {
            try {
                view!!.showLoadingDialog("")
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    override fun onNext(t: T?) {
        if (view == null) {
            return
        }
        if (t == null) {
            return
        }

        if (t is BaseResponse) {
            val data = t as BaseResponse?
            if (data!!.isSuccess!!) {
                onResponse(t)
            } else {
                val errorMsg = if (TextUtils.isEmpty(data.errorMsg)) "系统开小差，请稍后再试" else data.errorMsg
                onFailed(ApiException(ConnectException(), data.responseCode!!, errorMsg))
            }
        }
    }


    override fun onComplete() {
        if (view == null) {
            return
        }
        if (actionConfig!!.isShowLoading) {
            view!!.hideLoadingDialog()
        }
    }

    override fun onError(e: Throwable) {
        if (view == null) {
            return
        }
        handlerException(e)
    }

    /**
     * add by xmren at 09/29 to throw more error code to top level class
     */
    fun onException(apiException: ApiException) {
        onFailed(apiException)
    }


    abstract fun onFailed(apiException: ApiException)

    abstract fun onResponse(response: T)

    private fun handlerException(throwable: Throwable) {
        val apiException = ExceptionEngine.handleException(throwable)
        onException(apiException)
        onComplete()
    }

}
