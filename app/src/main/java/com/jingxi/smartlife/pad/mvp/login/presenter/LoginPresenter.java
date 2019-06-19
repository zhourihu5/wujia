package com.jingxi.smartlife.pad.mvp.login.presenter;

import com.jingxi.smartlife.pad.mvp.login.contract.LoginContract;
import com.wujia.businesslib.base.RxPresenter;
import com.wujia.businesslib.data.LoginDTO;
import com.wujia.businesslib.data.RootResponse;
import com.wujia.businesslib.data.TokenBean;
import com.wujia.businesslib.data.UserBean;
import com.jingxi.smartlife.pad.mvp.login.model.LoginModel;
import com.wujia.lib_common.data.network.SimpleRequestSubscriber;
import com.wujia.lib_common.data.network.exception.ApiException;
import com.wujia.lib_common.utils.DateUtil;


/**
 * author ：shenbingkai@163.com
 * date ：2019-01-27
 * description ：
 */
public class LoginPresenter extends RxPresenter<LoginContract.View> implements LoginContract.Presenter {
    public static final int REQUEST_CDOE_LOGIN = 1;
    public static final int REQUEST_CDOE_GET_CODE = 2;
    public static final int REQUEST_CDOE_TIMER = 3;
    public static final int REQUEST_CDOE_TOKEN = 4;
    private LoginModel mModel;

    public LoginPresenter() {
        this.mModel = new LoginModel();
    }


    @Override
    public void doTimeChange() {
        mView.timeChange(DateUtil.getCurrentTimeHHMM());
    }

    @Override
    public void doLogin(String mobile, String captcha, String padSn) {
        addSubscribe(mModel.login(mobile, captcha, padSn).subscribeWith(new SimpleRequestSubscriber<LoginDTO>(mView, new SimpleRequestSubscriber.ActionConfig(true, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            @Override
            public void onResponse(LoginDTO response) {
                super.onResponse(response);
                if (response.isSuccess()) {
                    mView.onDataLoadSucc(REQUEST_CDOE_LOGIN, response);
                }
            }

            @Override
            public void onFailed(ApiException apiException) {
                super.onFailed(apiException);
                mView.onDataLoadFailed(REQUEST_CDOE_LOGIN, apiException);
            }
        }));

    }

    @Override
    public void doGetCode(String mobile) {
        addSubscribe(mModel.getCode(mobile).subscribeWith(new SimpleRequestSubscriber<RootResponse>(mView, new SimpleRequestSubscriber.ActionConfig(true, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            @Override
            public void onResponse(RootResponse response) {
                super.onResponse(response);
                if (response.isSuccess()) {
                    mView.onDataLoadSucc(REQUEST_CDOE_GET_CODE, response);
                }
            }

            @Override
            public void onFailed(ApiException apiException) {
                super.onFailed(apiException);
                mView.onDataLoadFailed(REQUEST_CDOE_GET_CODE, apiException);
            }
        }));

    }
}
