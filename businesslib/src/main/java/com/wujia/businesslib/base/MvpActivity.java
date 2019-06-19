package com.wujia.businesslib.base;

import android.content.Context;
import android.os.Build;

import com.wujia.businesslib.dialog.LoadingDialog;
import com.wujia.lib_common.base.BaseActivity;
import com.wujia.lib_common.base.BasePresenter;
import com.wujia.lib_common.base.BaseView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by xmren on 2017/7/31.
 */

public abstract class MvpActivity<T extends BasePresenter> extends BaseActivity implements BaseView {

    protected T mPresenter;
    private LoadingDialog mLoadingDialog;

    protected abstract T createPresenter();

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    protected CompositeDisposable mCompositeDisposable;

    protected void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
            mCompositeDisposable = null;
        }
    }

    protected void addSubscribe(Disposable subscription) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }


    @Override
    protected void onDestroy() {
        if (mPresenter != null)
            mPresenter.detachView();
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
        unSubscribe();
        super.onDestroy();
    }

    @Override
    public void showErrorMsg(String msg) {
        showToast(msg);
    }

    @Override
    public void showLoadingDialog(String text) {
        getLoadingDialog();
        if (mLoadingDialog != null) {
            mLoadingDialog.setTitle(text);
            mLoadingDialog.show();
        }
    }

    public void showLoadingDialog(String text, boolean canTouch) {

        getLoadingDialog();
        if (mLoadingDialog != null) {
            mLoadingDialog.setTitle(text);
            mLoadingDialog.show();
            mLoadingDialog.setCancelable(canTouch);
        }
    }

    private void getLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this);
        }
    }

    @Override
    public void hideLoadingDialog() {
        if (mLoadingDialog != null)
            mLoadingDialog.dismiss();
    }

    @Override
    public void onLoginStatusError() {
        if (Build.VERSION.SDK_INT >= 17 && isDestroyed())
            return;
        else if (isFinishing())
            return;
    }

    @Override
    public Context getContext() {
        return mContext;
    }
}
