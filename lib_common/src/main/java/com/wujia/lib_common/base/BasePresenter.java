package com.wujia.lib_common.base;

/**
 * Created by xmren on 2017/8/1.
 */

public interface BasePresenter<T extends BaseView> {
    void attachView(T view);

    void detachView();
}
