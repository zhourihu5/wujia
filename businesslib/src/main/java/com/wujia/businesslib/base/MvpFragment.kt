package com.wujia.businesslib.base

import android.content.Context
import android.widget.TextView

import com.wujia.businesslib.R
import com.wujia.businesslib.dialog.LoadingDialog
import com.wujia.lib.widget.util.ToastUtil
import com.wujia.lib_common.base.BaseFragment
import com.wujia.lib_common.base.BasePresenter
import com.wujia.lib_common.base.BaseView
import com.wujia.lib_common.utils.AppContext

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


/**
 * Created by xmren on 2017/8/1.
 *
 *
 */

abstract class MvpFragment<T : BasePresenter<BaseView>> : BaseFragment(), BaseView {

    protected var mPresenter: T? = null
    protected  var layout_right_btn: TextView?=null
    protected  var layout_back_btn: TextView?=null
    protected  var layout_title_tv: TextView?=null


    private var mLoadingDialog: LoadingDialog? = null

    protected var mCompositeDisposable: CompositeDisposable? = null


    protected abstract fun createPresenter(): T?

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

    override fun interruptInject() {
        layout_right_btn = mView.findViewById(R.id.layout_right_btn)
        layout_back_btn = mView.findViewById(R.id.layout_back_btn)
        layout_title_tv = mView.findViewById(R.id.layout_title_tv)

        mPresenter = createPresenter()
        if (mPresenter != null)
            mPresenter!!.attachView(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mLoadingDialog != null) {
            mLoadingDialog!!.dismiss()
            mLoadingDialog = null
        }
        if (mPresenter != null) mPresenter!!.detachView()
        unSubscribe()
    }

    override fun showErrorMsg(msg: String) {
        ToastUtil.showShort(AppContext.get(), msg)
    }

    override fun showLoadingDialog(text: String) {
        if (mActivity is MvpActivity<*>) {
            val mvpActivity = mActivity as MvpActivity<*>
            mvpActivity.showLoadingDialog(text)
        } else {
            getLoadingDialog()
            if (mLoadingDialog != null) {
                mLoadingDialog!!.setTitle(text)
                mLoadingDialog!!.show()
            }
        }
    }

    private fun getLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog(mContext)
        }
    }

    override fun hideLoadingDialog() {
        if (mActivity is MvpActivity<*>) {
            val mvpActivity = mActivity as MvpActivity<*>
            mvpActivity.hideLoadingDialog()
        } else {
            if (mLoadingDialog != null)
                mLoadingDialog!!.dismiss()
        }
    }

    override fun getContext(): Context? {
        return mContext
    }

    override fun onLoginStatusError() {
        if (isHidden || isDetached || isRemoving || !isAdded)
            return
    }

}


