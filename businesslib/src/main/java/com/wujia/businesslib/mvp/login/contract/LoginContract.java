package com.wujia.businesslib.mvp.login.contract;

import com.wujia.businesslib.data.ApiResponse;
import com.wujia.businesslib.data.UserEntity;
import com.wujia.lib_common.base.BasePresenter;
import com.wujia.lib_common.base.CommonDataLoadView;
import com.wujia.lib_common.base.IBaseModle;

import io.reactivex.Flowable;

/**
 * Created by xmren on 2018/5/18.
 */

public interface LoginContract {
    interface Model extends IBaseModle {

    }

    interface View extends CommonDataLoadView {

    }

    interface Presenter extends BasePresenter<LoginContract.View> {
    }
}
