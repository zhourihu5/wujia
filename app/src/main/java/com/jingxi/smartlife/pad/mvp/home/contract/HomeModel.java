package com.jingxi.smartlife.pad.mvp.home.contract;

import com.wujia.businesslib.data.CardDetailBean;
import com.jingxi.smartlife.pad.mvp.home.data.HomeRecBean;
import com.jingxi.smartlife.pad.mvp.home.data.HomeUserInfoBean;
import com.jingxi.smartlife.pad.mvp.home.data.LockADBean;
import com.wujia.businesslib.base.BaseModel;
import com.wujia.businesslib.data.ApiResponse;
import com.wujia.businesslib.data.RootResponse;
import com.jingxi.smartlife.pad.mvp.MainAppApiService;
import com.wujia.businesslib.data.MessageBean;
import com.jingxi.smartlife.pad.mvp.home.data.WeatherInfoBean;
import com.wujia.lib_common.data.network.RxUtil;

import io.reactivex.Flowable;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-27
 * description ：
 */
public class HomeModel extends BaseModel implements HomeContract.Model {


    @Override
    public Flowable<HomeRecBean> getQuickCard() {
        return mHttpHelper.create(MainAppApiService.class).getQuickCard().compose(RxUtil.<HomeRecBean>rxSchedulerHelper());

    }
    public Flowable<ApiResponse<CardDetailBean>> getCardDetail(String cardId) {
        return mHttpHelper.create(MainAppApiService.class).getCardDetail(cardId).compose(RxUtil.<ApiResponse<CardDetailBean>>rxSchedulerHelper());

    }

    @Override
    public Flowable<HomeRecBean> getUserQuickCard() {
        return mHttpHelper.create(MainAppApiService.class).getUserQuickCard().compose(RxUtil.<HomeRecBean>rxSchedulerHelper());

    }

    @Override
    public Flowable<RootResponse> addUserQuickCard( String quickCardId) {
        return mHttpHelper.create(MainAppApiService.class).addUserQuickCard( quickCardId).compose(RxUtil.<RootResponse>rxSchedulerHelper());

    }

    @Override
    public Flowable<RootResponse> removeUserQuickCard( String quickCardId) {
        return mHttpHelper.create(MainAppApiService.class).removeUserQuickCard( quickCardId).compose(RxUtil.<RootResponse>rxSchedulerHelper());
    }

    @Override
    public Flowable<WeatherInfoBean> getWeather() {
        return mHttpHelper.create(MainAppApiService.class).getWeather().compose(RxUtil.<WeatherInfoBean>rxSchedulerHelper());
    }
    @Override
    public Flowable<HomeUserInfoBean> getHomeUserInfo(String key) {
        return mHttpHelper.create(MainAppApiService.class).getHomeUserInfo(key).compose(RxUtil.<HomeUserInfoBean>rxSchedulerHelper());
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
    public Flowable<LockADBean> getScreenSaverByCommunityId() {
        return mHttpHelper.create(MainAppApiService.class).getScreenSaverByCommunityId().compose(RxUtil.<LockADBean>rxSchedulerHelper());
    }


}
