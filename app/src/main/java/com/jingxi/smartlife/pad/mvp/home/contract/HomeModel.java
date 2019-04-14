package com.jingxi.smartlife.pad.mvp.home.contract;

import com.jingxi.smartlife.pad.mvp.home.data.HomeRecBean;
import com.jingxi.smartlife.pad.mvp.home.data.LockADBean;
import com.wujia.businesslib.base.BaseModel;
import com.wujia.businesslib.data.RootResponse;
import com.jingxi.smartlife.pad.mvp.MainAppApiService;
import com.jingxi.smartlife.pad.mvp.home.data.HomeRecBean;
import com.wujia.businesslib.data.MessageBean;
import com.jingxi.smartlife.pad.mvp.home.data.WeatherBean;
import com.wujia.lib_common.data.network.RxUtil;

import io.reactivex.Flowable;
import retrofit2.http.Query;

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
        return mHttpHelper.create(MainAppApiService.class).addUserQuickCard(openid, quickCardId).compose(RxUtil.<RootResponse>rxSchedulerHelper());

    }

    @Override
    public Flowable<RootResponse> removeUserQuickCard(String openid, String quickCardId) {
        return mHttpHelper.create(MainAppApiService.class).removeUserQuickCard(openid, quickCardId).compose(RxUtil.<RootResponse>rxSchedulerHelper());
    }

    @Override
    public Flowable<WeatherBean> getWeather(String communityId) {
        return mHttpHelper.create(MainAppApiService.class).getWeather(communityId).compose(RxUtil.<WeatherBean>rxSchedulerHelper());
    }

    @Override
    public Flowable<MessageBean> getPropertyMessageById(String id) {
        return mHttpHelper.create(MainAppApiService.class).getPropertyMessageById(id).compose(RxUtil.<MessageBean>rxSchedulerHelper());
    }

    @Override
    public Flowable<MessageBean> getManagerMessageById(String id) {
        return mHttpHelper.create(MainAppApiService.class).getManagerMessageById(id).compose(RxUtil.<MessageBean>rxSchedulerHelper());
    }

    @Override
    public Flowable<LockADBean> getScreenSaverByCommunityId(String communityId) {
        return mHttpHelper.create(MainAppApiService.class).getScreenSaverByCommunityId(communityId).compose(RxUtil.<LockADBean>rxSchedulerHelper());
    }
}
