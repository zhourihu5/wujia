package com.wujia.intellect.terminal.mvp.login.model;

import com.wujia.businesslib.base.BaseModel;
import com.wujia.businesslib.data.ApiResponse;
import com.wujia.intellect.terminal.mvp.login.contract.LoginContract;

import io.reactivex.Flowable;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-27
 * description ：
 */
public class LoginModel extends BaseModel implements LoginContract.Model {


    @Override
    public Flowable<ApiResponse<String>> doGetTestNet() {
        return null;
    }
}
