package com.wujia.businesslib.base

import com.wujia.lib_common.base.BasePresenter
import com.wujia.lib_common.base.BaseView

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * 基于Rx的Presenter封装,控制订阅的生命周期
 */
open class RxPresenter< T : BaseView> : BasePresenter<T> {

    protected var mView: T? = null
    protected var mCompositeDisposable: CompositeDisposable? = null

    protected fun unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable!!.clear()
            mCompositeDisposable = null
        }
    }

    protected fun addSubscribe(subscription: Disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable!!.add(subscription)
    }

    override fun attachView(view: BaseView) {
        this.mView = view as T
    }

    override fun detachView() {
        this.mView = null
        unSubscribe()
    }
}
