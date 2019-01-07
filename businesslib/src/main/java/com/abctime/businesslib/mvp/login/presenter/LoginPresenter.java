package com.abctime.businesslib.mvp.login.presenter;

import com.abctime.businesslib.data.ApiResponse;
import com.abctime.businesslib.data.DataManager;
import com.abctime.businesslib.data.UserEntity;
import com.abctime.businesslib.event.EventBusUtil;
import com.abctime.businesslib.event.impl.EventLogin;
import com.abctime.businesslib.mvp.login.contract.LoginContract;
import com.abctime.businesslib.mvp.login.model.LoginModel;
import com.abctime.businesslib.base.RxPresenter;
import com.abctime.lib_common.data.network.SimpleRequestSubscriber;
import com.abctime.lib_common.data.network.exception.ApiException;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import javax.inject.Inject;

/**
 * Created by xmren on 2018/5/18.
 */

public class LoginPresenter extends RxPresenter<LoginContract.View> implements LoginContract.Presenter {
    LoginModel loginModel;
    public static final int LOGIN_RREQUEST_CODE=0;
    public static final int GUEST_LOGIN_RREQUEST_CODE=1;
    public static final int GUEST_REGISTER_RREQUEST_CODE=2;

    @Inject
    public LoginPresenter(LoginModel loginModel) {
        this.loginModel = loginModel;
    }

    @Override
    public void doGuestLogin(boolean showLoading) {

        addSubscribe(loginModel.guestLogin().subscribeWith(new SimpleRequestSubscriber<ApiResponse<UserEntity>>(mView,new SimpleRequestSubscriber.ActionConfig(showLoading,SimpleRequestSubscriber.SHOWERRORMESSAGE)) {

            @Override
            public void onResponse(ApiResponse<UserEntity> response) {
                super.onResponse(response);
                if(response.isSuccess())
                {
                    if(response.data !=null)
                    {
                        DataManager.saveUserInfo(response.data);
                    }

                    mView.onDataLoadSucc(GUEST_LOGIN_RREQUEST_CODE,response.data);
                    EventBusUtil.post(new EventLogin(response.data, EventLogin.LOGIN_EVENT_STATE));
                } else {
                    mView.onDataLoadFailed(GUEST_LOGIN_RREQUEST_CODE, new ApiException(new RuntimeException("")));
                }
            }

            @Override
            public void onFailed(ApiException apiException) {
                super.onFailed(apiException);
                mView.onDataLoadFailed(GUEST_LOGIN_RREQUEST_CODE, apiException);
            }
        }));
    }

    @Override
    public void doAutoLogin(boolean showLoading) {
        addSubscribe(loginModel.getUserInfo(DataManager.getMemberid()).subscribeWith(new SimpleRequestSubscriber<ApiResponse<UserEntity>>(mView) {
            @Override
            public void onResponse(ApiResponse<UserEntity> response) {
                super.onResponse(response);
                if (response.isSuccess()) {
                    if (response.data != null) {
                        DataManager.saveUserInfo(response.data);
                    }
                    mView.onDataLoadSucc(10011,response.data);
                    EventBusUtil.post(new EventLogin(response.data, EventLogin.LOGIN_EVENT_STATE));
                } else {
                    mView.onDataLoadFailed(10011, new ApiException(new RuntimeException("")));
                }
            }

            @Override
            public void onFailed(ApiException apiException) {
                super.onFailed(apiException);
                mView.onDataLoadFailed(10011, apiException);
            }
        }));
    }

    @Override
    public void doLogin(String phone, String code) {
        addSubscribe(loginModel.doLogin(phone, code).subscribeWith(new SimpleRequestSubscriber<ApiResponse<UserEntity>>(mView,new SimpleRequestSubscriber.ActionConfig(true,SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            @Override
            public void onResponse(ApiResponse<UserEntity> response) {
                super.onResponse(response);
                if(response.isSuccess())
                {
                    if(response.data !=null)
                    {
                        DataManager.saveUserInfo(response.data);
                    }

                    mView.onDataLoadSucc(LOGIN_RREQUEST_CODE,response.data);
                    EventBusUtil.post(new EventLogin(response.data, EventLogin.LOGIN_EVENT_STATE));
                }
            }
            @Override
            public void onFailed(ApiException apiException) {
                super.onFailed(apiException);
                mView.onDataLoadFailed(LOGIN_RREQUEST_CODE,apiException);
            }
        }));
    }

    public void doGuestRegister(String phone, String code){
        addSubscribe(loginModel.doGuestRegister(phone, code).subscribeWith(new SimpleRequestSubscriber<ApiResponse<UserEntity>>(mView,new SimpleRequestSubscriber.ActionConfig(true,SimpleRequestSubscriber.ERROR_NONE)) {
            @Override
            public void onResponse(ApiResponse<UserEntity> response) {
                super.onResponse(response);
                if(response.isSuccess())
                {
                    if(response.data !=null)
                    {
                        DataManager.saveUserInfo(response.data);
                    }

                    mView.onDataLoadSucc(GUEST_REGISTER_RREQUEST_CODE,response.data);
                    EventBusUtil.post(new EventLogin(response.data, EventLogin.LOGIN_EVENT_STATE));
                }
            }
            @Override
            public void onFailed(ApiException apiException) {
                super.onFailed(apiException);
                mView.onDataLoadFailed(GUEST_REGISTER_RREQUEST_CODE,apiException);
            }
        }));
    }

    public boolean isPhoneAvailable(String phoneInput) {
            String regex = (DataManager.getConfigData().mobileRegular == null) ?
                    "^1((3[0-9]|4[57]|5[0-35-9]|6[6]|7[035678]|8[0-9]|9[89])\\d{8}$)":DataManager.getConfigData().mobileRegular;
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phoneInput);
            return m.matches();
    }
}
