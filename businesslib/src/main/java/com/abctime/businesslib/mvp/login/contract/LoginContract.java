package com.abctime.businesslib.mvp.login.contract;

import com.abctime.businesslib.data.ApiResponse;
import com.abctime.businesslib.data.UserEntity;
import com.abctime.lib_common.base.BasePresenter;
import com.abctime.lib_common.base.CommonDataLoadView;
import com.abctime.lib_common.base.IBaseModle;

import io.reactivex.Flowable;

/**
 * Created by xmren on 2018/5/18.
 */

public interface LoginContract {
    interface Model extends IBaseModle {
        public Flowable<ApiResponse<UserEntity>> guestLogin();

        public Flowable<ApiResponse<UserEntity>> doLogin(String phone, String code);
        public Flowable<ApiResponse<UserEntity>> doGuestRegister(String phone, String code);
        public Flowable<ApiResponse<UserEntity>> getUserInfo(String member_id);
    }

    interface View extends CommonDataLoadView {

    }

    interface Presenter extends BasePresenter<LoginContract.View> {
        public void doGuestLogin(boolean showLoading);
        public void doAutoLogin(boolean showLoading);

        public void doLogin(String phone, String code);


    }
}
