package com.jingxi.smartlife.pad.mvp.login.model;

import com.jingxi.smartlife.pad.mvp.login.contract.LoginContract;
import com.wujia.businesslib.BusAppApiService;
import com.wujia.businesslib.Constants;
import com.wujia.businesslib.base.BaseModel;
import com.wujia.businesslib.base.NetConfigWrapper;
import com.wujia.businesslib.data.RootResponse;
import com.wujia.businesslib.data.TokenBean;
import com.wujia.businesslib.data.UserBean;
import com.jingxi.smartlife.pad.mvp.MainAppApiService;
import com.jingxi.smartlife.pad.mvp.login.contract.LoginContract;
import com.wujia.lib_common.data.network.HttpHelper;
import com.wujia.lib_common.data.network.RxUtil;

import io.reactivex.Flowable;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-27
 * description ：
 */
public class LoginModel extends BaseModel implements LoginContract.Model {


    @Override
    public Flowable<TokenBean> getAccessToken() {
        return new HttpHelper.Builder(NetConfigWrapper.create(Constants.BASE_URL_TOKEN))
                .build().create(BusAppApiService.class).getAccessToken(Constants.SECRET).compose(RxUtil.<TokenBean>rxSchedulerHelper());
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
