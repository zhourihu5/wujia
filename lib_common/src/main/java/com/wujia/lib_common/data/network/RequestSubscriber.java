package com.wujia.lib_common.data.network;

import android.text.TextUtils;

import com.wujia.lib_common.base.BaseView;
import com.wujia.lib_common.data.network.exception.ApiException;
import com.wujia.lib_common.data.network.exception.ExceptionEngine;
import com.wujia.lib_common.utils.AppContext;
import com.wujia.lib_common.utils.sys.NetworkUtil;

import java.net.ConnectException;

import io.reactivex.subscribers.ResourceSubscriber;

public abstract class RequestSubscriber<T> extends ResourceSubscriber<T> {
    protected BaseView view;
    protected SimpleRequestSubscriber.ActionConfig actionConfig;


    protected RequestSubscriber(BaseView view) {
        this.view = view;
    }

    @Override
    protected void onStart() {
        if (!NetworkUtil.isNetAvailable(AppContext.get())) {//在无网络的时候直接抛出Error add by xmren
            onError(new ConnectException("网络连接失败，请检查网络"));
            onComplete();
            dispose();

            return;
        }
        super.onStart();
        if (actionConfig.isShowLoading) {
            try {
                view.showLoadingDialog("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onNext(T t) {
        if (view == null) {
            return;
        }
        if (t == null) {
            return;
        }

        if (t instanceof BaseResponse) {
            BaseResponse data = (BaseResponse) t;
            if (data.isSuccess()) {
                onResponse(t);
            } else {
                String errorMsg = TextUtils.isEmpty(data.getErrorMsg()) ? "系统开小差，请稍后再试" : data.getErrorMsg();
                onFailed(new ApiException(new ConnectException(), data.getResponseCode(), errorMsg));
            }
        }
    }


    @Override
    public void onComplete() {
        if (actionConfig.isShowLoading) {
            view.hideLoadingDialog();
        }
    }

    @Override
    public void onError(Throwable e) {
        if (view == null) {
            return;
        }
        handlerException(e);
    }

    /**
     * add by xmren at 09/29 to throw more error code to top level class
     */
    public void onException(ApiException apiException) {
        onFailed(apiException);
    }


    public abstract void onFailed(ApiException apiException);

    public abstract void onResponse(T response);

    private void handlerException(Throwable throwable) {
        ApiException apiException = ExceptionEngine.handleException(throwable);
        onException(apiException);
        onComplete();
    }

}
