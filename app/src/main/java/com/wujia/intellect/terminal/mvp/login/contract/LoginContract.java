package com.wujia.intellect.terminal.mvp.login.contract;

import com.wujia.businesslib.data.ApiResponse;
import com.wujia.lib_common.base.BasePresenter;
import com.wujia.lib_common.base.BaseView;
import com.wujia.lib_common.base.IBaseModle;

import io.reactivex.Flowable;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-27
 * description ：
 */
public interface LoginContract {
    interface Model extends IBaseModle {

        Flowable<ApiResponse<String>> doGetTestNet();

    }

    interface View extends BaseView {
    }

    interface Presenter extends BasePresenter {
        void getTestNet();
    }
}
