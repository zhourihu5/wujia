package com.wujia.intellect.terminal.mvp.home.contract;

import com.wujia.businesslib.Constants;
import com.wujia.businesslib.base.BaseModel;
import com.wujia.businesslib.data.RootResponse;
import com.wujia.businesslib.data.TokenBean;
import com.wujia.businesslib.data.UserBean;
import com.wujia.intellect.terminal.mvp.MainAppApiService;
import com.wujia.intellect.terminal.mvp.home.data.HomeRecBean;
import com.wujia.intellect.terminal.mvp.home.data.WeatherBean;
import com.wujia.intellect.terminal.mvp.login.contract.LoginContract;
import com.wujia.lib_common.data.network.RxUtil;

import io.reactivex.Flowable;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-27
 * description ：
 */
public class HomeModel extends BaseModel implements HomeContract.Model {


    @Override
    public Flowable<HomeRecBean> getQuickCard(String communityId) {
        return mHttpHelper.create(MainAppApiService.class).getQuickCard(communityId).compose(RxUtil.<HomeRecBean>rxSchedulerHelper());

    }

    @Override
    public Flowable<HomeRecBean> getUserQuickCard(String openid) {
        return mHttpHelper.create(MainAppApiService.class).getUserQuickCard(openid).compose(RxUtil.<HomeRecBean>rxSchedulerHelper());

    }

    @Override
    public Flowable<RootResponse> addUserQuickCard(String openid, String quickCardId) {
        return mHttpHelper.create(MainAppApiService.class).addUserQuickCard(openid,quickCardId).compose(RxUtil.<RootResponse>rxSchedulerHelper());

    }

    @Override
    public Flowable<RootResponse> removeUserQuickCard(String openid, String quickCardId) {
        return mHttpHelper.create(MainAppApiService.class).removeUserQuickCard(openid,quickCardId).compose(RxUtil.<RootResponse>rxSchedulerHelper());
    }

    @Override
    public Flowable<WeatherBean> getWeather(String communityId) {
        return mHttpHelper.create(MainAppApiService.class).getWeather(communityId).compose(RxUtil.<WeatherBean>rxSchedulerHelper());
    }
}
