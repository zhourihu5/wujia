package com.wujia.businesslib.base

import android.os.Build
import com.wujia.businesslib.dialog.LoadingDialog
import com.wujia.lib.widget.util.ToastUtil
import com.wujia.lib_common.base.BaseActivity
import com.wujia.lib_common.base.BasePresenter
import com.wujia.lib_common.base.BaseView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by xmren on 2017/7/31.
 */

abstract class MvpActivity<T : BasePresenter<BaseView>> : BaseActivity(), BaseView {

    protected var mPresenter: T? = null
    private var mLoadingDialog: LoadingDialog? = null

    protected var mCompositeDisposable: CompositeDisposable? = null

    protected abstract fun createPresenter(): T?

    override fun onViewCreated() {
        super.onViewCreated()
        mPresenter = createPresenter()
        if (mPresenter != null) {
            mPresenter!!.attachView(this)
        }
    }

    private fun unSubscribe() {
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


    override fun onDestroy() {
        if (mPresenter != null)
            mPresenter!!.detachView()
        if (mLoadingDialog != null) {
            mLoadingDialog!!.dismiss()
            mLoadingDialog = null
        }
        unSubscribe()
        super.onDestroy()
    }

    override fun showErrorMsg(msg: String) {
        ToastUtil.showShort(this, msg)
    }

    override fun showLoadingDialog(text: String) {
        getLoadingDialog()
        if (mLoadingDialog != null) {
            mLoadingDialog!!.setTitle(text)
            mLoadingDialog!!.show()
        }
    }

    fun showLoadingDialog(text: String, canTouch: Boolean) {

        getLoadingDialog()
        if (mLoadingDialog != null) {
            mLoadingDialog!!.setTitle(text)
            mLoadingDialog!!.show()
            mLoadingDialog!!.setCancelable(canTouch)
        }
    }

    private fun getLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog(this)
        }
    }

    override fun hideLoadingDialog() {
        if (mLoadingDialog != null)
            mLoadingDialog!!.dismiss()
    }

    override fun onLoginStatusError() {
        if (Build.VERSION.SDK_INT >= 17 && isDestroyed)
            return
        else if (isFinishing)
            return
    }

}
