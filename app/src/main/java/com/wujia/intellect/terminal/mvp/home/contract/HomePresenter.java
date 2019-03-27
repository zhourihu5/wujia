package com.wujia.intellect.terminal.mvp.home.contract;

import com.wujia.businesslib.base.RxPresenter;
import com.wujia.businesslib.data.TokenBean;
import com.wujia.intellect.terminal.mvp.home.data.HomeRecBean;
import com.wujia.intellect.terminal.mvp.home.data.WeatherBean;
import com.wujia.lib_common.data.network.SimpleRequestSubscriber;
import com.wujia.lib_common.data.network.exception.ApiException;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-27
 * description ：
 */
public class HomePresenter extends RxPresenter<HomeContract.View> implements HomeContract.Presenter {

    public static final int REQUEST_CDOE_GET_CARD_MY=1;
    public static final int REQUEST_CDOE_GET_CARD_OTHER=2;
    public static final int REQUEST_CDOE_ADD_CARD=3;
    public static final int REQUEST_CDOE_REMOVE_CARD=4;
    public static final int REQUEST_CDOE_WEATHER=5;


    private HomeModel mModel;

    public HomePresenter() {
        this.mModel = new HomeModel();
    }

    @Override
    public void getQuickCard(String communityId) {

        addSubscribe(mModel.getQuickCard(communityId).subscribeWith(new SimpleRequestSubscriber<HomeRecBean>(mView, new SimpleRequestSubscriber.ActionConfig(true, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            @Override
            public void onResponse(HomeRecBean response) {
                super.onResponse(response);
                if (response.isSuccess()) {
                    mView.onDataLoadSucc(REQUEST_CDOE_GET_CARD_MY, response);
                }
            }

            @Override
            public void onFailed(ApiException apiException) {
                super.onFailed(apiException);
                mView.onDataLoadFailed(REQUEST_CDOE_GET_CARD_MY, apiException);
            }
        }));

    }

    @Override
    public void getWeather(String communityId) {
        addSubscribe(mModel.getWeather(communityId).subscribeWith(new SimpleRequestSubscriber<WeatherBean>(mView, new SimpleRequestSubscriber.ActionConfig(true, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            @Override
            public void onResponse(WeatherBean response) {
                super.onResponse(response);
                if (response.isSuccess()) {
                    mView.onDataLoadSucc(REQUEST_CDOE_WEATHER, response);
                }
            }

            @Override
            public void onFailed(ApiException apiException) {
                super.onFailed(apiException);
                mView.onDataLoadFailed(REQUEST_CDOE_WEATHER, apiException);
            }
        }));

    }

    @Override
    public void getUserQuickCard(String openid) {
        addSubscribe(mModel.getUserQuickCard(openid).subscribeWith(new SimpleRequestSubscriber<HomeRecBean>(mView, new SimpleRequestSubscriber.ActionConfig(true, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            @Override
            public void onResponse(HomeRecBean response) {
                super.onResponse(response);
                if (response.isSuccess()) {
                    mView.onDataLoadSucc(REQUEST_CDOE_GET_CARD_MY, response);
                }
            }

            @Override
            public void onFailed(ApiException apiException) {
                super.onFailed(apiException);
                mView.onDataLoadFailed(REQUEST_CDOE_GET_CARD_MY, apiException);
            }
        }));
    }

    @Override
    public void addUserQuickCard(String openid, String quickCardId) {

    }

    @Override
    public void removeUserQuickCard(String openid, String quickCardId) {

    }
}
