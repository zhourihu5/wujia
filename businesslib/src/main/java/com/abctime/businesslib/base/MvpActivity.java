package com.abctime.businesslib.base;

import android.content.Context;
import android.os.Build;

import com.abctime.businesslib.di.IDaggerListener;
import com.abctime.businesslib.di.module.ActivityModule;
import com.abctime.lib.widget.LoadingDialog;
import com.abctime.lib.widget.util.ToastUtil;
import com.abctime.lib_common.base.BaseActivity;
import com.abctime.lib_common.base.BasePresenter;
import com.abctime.lib_common.base.BaseView;
import com.abctime.lib_common.base.BizDialog;

import javax.inject.Inject;

/**
 * Created by xmren on 2017/7/31.
 */

public abstract class MvpActivity<T extends BasePresenter> extends BaseActivity implements BaseView, IDaggerListener {

    @Inject
    protected T mPresenter;
    private LoadingDialog mLoadingDialog;

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }


    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        initInject();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }


    @Override
    protected void onDestroy() {
        if (mPresenter != null)
            mPresenter.detachView();
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
        super.onDestroy();
    }

    @Override
    public void showErrorMsg(String msg) {
        ToastUtil.shortShow(this, msg);
    }

    @Override
    public void showLoadingDialog(String text) {
        getLoadingDialog();
        if (mLoadingDialog != null) {
            mLoadingDialog.setTitle(text);
            mLoadingDialog.show();
        }
    }

    public void showLoadingDialog(String text ,boolean canTouch) {

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
        BizDialog.showInstance("com.abctime.businesslib.dialog.LoginStatusErrorDialog", this);
    }

    @Override
    public Context getContext() {
        return mContext;
    }
}
