package com.wujia.lib_common.base

/**
 * Created by xmren on 2017/8/1.
 */

interface BasePresenter<out T : BaseView> {
    fun attachView(view: BaseView)

    fun detachView()
}
