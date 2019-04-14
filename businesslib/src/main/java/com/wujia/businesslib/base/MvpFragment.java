package com.wujia.businesslib.base;

import android.content.Context;
import android.support.annotation.Nullable;

import com.wujia.businesslib.dialog.LoadingDialog;
import com.wujia.lib.widget.util.ToastUtil;
import com.wujia.lib_common.base.BaseFragment;
import com.wujia.lib_common.base.BasePresenter;
import com.wujia.lib_common.base.BaseView;
import com.wujia.lib_common.utils.AppContext;


/**
 * Created by xmren on 2017/8/1.
 * <p>
 */

public abstract class MvpFragment<T extends BasePresenter> extends BaseFragment implements BaseView {

    protected T mPresenter;
    private LoadingDialog mLoadingDialog;


    protected abstract T createPresenter();

    @Override
    protected void interruptInject() {
        mPresenter = createPresenter();
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
        ToastUtil.showShort(AppContext.get(), msg);
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
    }

}


