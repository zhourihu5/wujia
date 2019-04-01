package com.wujia.intellect.terminal.mvp.login.model;

import com.wujia.businesslib.BusAppApiService;
import com.wujia.businesslib.Constants;
import com.wujia.businesslib.base.BaseModel;
import com.wujia.businesslib.data.RootResponse;
import com.wujia.businesslib.data.TokenBean;
import com.wujia.businesslib.data.UserBean;
import com.wujia.intellect.terminal.mvp.MainAppApiService;
import com.wujia.intellect.terminal.mvp.login.contract.LoginContract;
import com.wujia.lib_common.data.network.RxUtil;

import io.reactivex.Flowable;
import retrofit2.http.Field;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-27
 * description ：
 */
public class LoginModel extends BaseModel implements LoginContract.Model {


    @Override
    public Flowable<TokenBean> getAccessToken() {
        return mHttpHelper.create(BusAppApiService.class).getAccessToken(Constants.APPID, Constants.SECRET).compose(RxUtil.<TokenBean>rxSchedulerHelper());
    }

    @Override
    public Flowable<UserBean> login(String mobile, String captcha, String padSn) {
        return mHttpHelper.create(MainAppApiService.class).login(mobile, captcha, padSn).compose(RxUtil.<UserBean>rxSchedulerHelper());
    }

    @Override
    public Flowable<RootResponse> login(String mobile) {
        return mHttpHelper.create(MainAppApiService.class).getCode(mobile).compose(RxUtil.<RootResponse>rxSchedulerHelper());
    }


}
