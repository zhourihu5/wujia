package com.abctime.businesslib.base;

import android.content.Context;
import android.support.annotation.Nullable;

import com.abctime.businesslib.di.IDaggerListener;
import com.abctime.businesslib.di.module.FragmentModule;
import com.abctime.lib.widget.LoadingDialog;
import com.abctime.lib_common.base.BaseFragment;
import com.abctime.lib_common.base.BasePresenter;
import com.abctime.lib_common.base.BaseView;
import com.abctime.lib_common.base.BizDialog;

import javax.inject.Inject;


/**
 * Created by xmren on 2017/8/1.
 * <p>
 */

public abstract class MvpFragment<T extends BasePresenter> extends BaseFragment implements BaseView, IDaggerListener {

    @Inject
    protected T mPresenter;
    private LoadingDialog mLoadingDialog;


    protected FragmentModule getFragmentModule() {
        return new FragmentModule(this);
    }


    @Override
    protected void interruptInject() {
        initInject();
        if (mPresenter != null)
            mPresenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
        if (mPresenter != null) mPresenter.detachView();
    }

    @Override
    public void showErrorMsg(String msg) {

    }

    @Override
    public void showLoadingDialog(String text) {
        getLoadingDialog();
        if (mLoadingDialog != null) {
            mLoadingDialog.setTitle(text);
            mLoadingDialog.show();
        }
    }

    private void getLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(mContext);
        }
    }

    @Override
    public void hideLoadingDialog() {
        if (mLoadingDialog != null)
            mLoadingDialog.dismiss();
    }

    @Nullable
    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void onLoginStatusError() {
        if (isHidden() || isDetached() || isRemoving() || !isAdded())
            return;
        BizDialog.showInstance("com.abctime.businesslib.dialog.LoginStatusErrorDialog", mContext);
    }

}


