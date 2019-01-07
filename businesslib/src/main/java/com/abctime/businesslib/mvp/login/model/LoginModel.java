package com.abctime.businesslib.mvp.login.model;

import com.abctime.businesslib.data.ApiResponse;
import com.abctime.businesslib.data.CommonApiService;
import com.abctime.businesslib.data.UserEntity;
import com.abctime.businesslib.mvp.login.contract.LoginContract;
import com.abctime.lib_common.data.network.HttpHelper;
import com.abctime.lib_common.data.network.RxUtil;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by xmren on 2018/5/18.
 */

public class LoginModel implements LoginContract.Model {
    HttpHelper httpHelper;

    @Inject
    public LoginModel(HttpHelper httpHelper) {
        this.httpHelper = httpHelper;
    }

    @Override
    public Flowable<ApiResponse<UserEntity>> guestLogin() {
        return httpHelper.create(CommonApiService.class).doGuestLogin().compose(RxUtil.<ApiResponse<UserEntity>>rxSchedulerHelper());
    }

    @Override
    public Flowable<ApiResponse<UserEntity>> doLogin(String phone, String code) {
        return httpHelper.create(CommonApiService.class).doLogin(phone,code).compose(RxUtil.<ApiResponse<UserEntity>>rxSchedulerHelper());
    }

    @Override
    public Flowable<ApiResponse<UserEntity>> doGuestRegister(String phone, String code) {
        return httpHelper.create(CommonApiService.class).doGuestRegister(phone,code).compose(RxUtil.<ApiResponse<UserEntity>>rxSchedulerHelper());
    }

    @Override
    public Flowable<ApiResponse<UserEntity>> getUserInfo(String member_id) {
        return httpHelper.create(CommonApiService.class).requestUserInfo(member_id).compose(RxUtil.<ApiResponse<UserEntity>>rxSchedulerHelper());
    }


}
