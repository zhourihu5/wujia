package com.wujia.lib_common.base;

import android.content.Context;

/**
 * Created by xmren on 2018/5/4.
 */

public interface BaseView {
    void showErrorMsg(String msg);

    void showLoadingDialog(String text);

    void hideLoadingDialog();

    Context getContext();

    void onLoginStatusError();
}
