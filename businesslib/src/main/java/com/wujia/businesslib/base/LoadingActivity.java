package com.wujia.businesslib.base;

import com.wujia.businesslib.dialog.LoadingDialog;
import com.wujia.lib_common.base.BasePresenter;

/**
 * Created by yseerd on 2018/5/26.
 */

public abstract class LoadingActivity <T extends BasePresenter> extends MvpActivity<T>{

    private LoadingDialog loadingDialog;

    @Override
    public void showLoadingDialog(String text) {
        super.showLoadingDialog(text);
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
            loadingDialog.setCancelable(false);
        }
        loadingDialog.setTitle(text);
        loadingDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        super.hideLoadingDialog();
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();

        }
    }

    @Override
    public void showErrorMsg(String msg) {
        super.showErrorMsg(msg);
    }
}
