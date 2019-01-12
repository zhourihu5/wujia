package com.wujia.businesslib.mvp.login.presenter;

import com.wujia.businesslib.mvp.login.contract.LoginContract;
import com.wujia.businesslib.mvp.login.model.LoginModel;
import com.wujia.businesslib.base.RxPresenter;

/**
 * Created by xmren on 2018/5/18.
 */

public class LoginPresenter extends RxPresenter<LoginContract.View> implements LoginContract.Presenter {
    LoginModel loginModel;


    public LoginPresenter() {
        this.loginModel = new LoginModel();
    }

}
