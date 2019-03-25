package com.wujia.intellect.terminal.mvp.login.presenter;

import com.wujia.businesslib.base.RxPresenter;
import com.wujia.businesslib.data.TokenBean;
import com.wujia.businesslib.data.UserBean;
import com.wujia.intellect.terminal.mvp.login.contract.LoginContract;
import com.wujia.intellect.terminal.mvp.login.model.LoginModel;
import com.wujia.lib_common.data.network.SimpleRequestSubscriber;
import com.wujia.lib_common.data.network.exception.ApiException;
import com.wujia.lib_common.utils.DateUtil;


/**
 * author ：shenbingkai@163.com
 * date ：2019-01-27
 * description ：
 */
public class LoginPresenter extends RxPresenter<LoginContract.View> implements LoginContract.Presenter {
    private LoginModel mModel;

    public LoginPresenter() {
        this.mModel = new LoginModel();
    }

    @Override
    public void doGetAccessToken() {
        addSubscribe(mModel.getAccessToken().subscribeWith(new SimpleRequestSubscriber<TokenBean>(mView, new SimpleRequestSubscriber.ActionConfig(true, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            @Override
            public void onResponse(TokenBean response) {
                super.onResponse(response);
                if (response.isSuccess()) {
                    mView.onDataLoadSucc(1, response);
                }
            }

            @Override
            public void onFailed(ApiException apiException) {
                super.onFailed(apiException);
                mView.onDataLoadFailed(1, apiException);
            }
        }));

    }

    @Override
    public void doTimeChange() {
        mView.timeChange(DateUtil.getCurrentTimeHHMM());
    }

    @Override
    public void doLogin(String mobile, String captcha, String padSn) {
        addSubscribe(mModel.login(mobile, captcha, padSn).subscribeWith(new SimpleRequestSubscriber<UserBean>(mView, new SimpleRequestSubscriber.ActionConfig(true, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            @Override
            public void onResponse(UserBean response) {
                super.onResponse(response);
                if (response.isSuccess()) {
                    mView.onDataLoadSucc(2, response);
                }
            }

            @Override
            public void onFailed(ApiException apiException) {
                super.onFailed(apiException);
                mView.onDataLoadFailed(2, apiException);
            }
        }));

    }
}
