package com.jingxi.smartlife.pad.mvp.home.contract;

import com.jingxi.smartlife.pad.mvp.home.data.HomeRecBean;
import com.jingxi.smartlife.pad.mvp.home.data.HomeUserInfoBean;
import com.jingxi.smartlife.pad.mvp.home.data.WeatherInfoBean;
import com.wujia.businesslib.base.RxPresenter;
import com.wujia.businesslib.data.RootResponse;
import com.wujia.lib_common.data.network.SimpleRequestSubscriber;
import com.wujia.lib_common.data.network.exception.ApiException;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-27
 * description ：
 */
public class HomePresenter extends RxPresenter<HomeContract.View> implements HomeContract.Presenter {

    public static final int REQUEST_CDOE_GET_CARD_MY = 1;
    public static final int REQUEST_CDOE_GET_CARD_OTHER = 2;
    public static final int REQUEST_CDOE_ADD_CARD = 3;
    public static final int REQUEST_CDOE_REMOVE_CARD = 4;
    public static final int REQUEST_CDOE_WEATHER = 5;
    public static final int REQUEST_CDOE_MESSAGE = 6;
    public static final int REQUEST_CDOE_SCREEN_AD = 7;
    public static final int REQUEST_CDOE_HOME_USER = 8;


    private HomeModel mModel;

    public HomePresenter() {
        this.mModel = new HomeModel();
    }

    @Override
    public void getQuickCard() {

        addSubscribe(mModel.getQuickCard().subscribeWith(new SimpleRequestSubscriber<HomeRecBean>(mView, new SimpleRequestSubscriber.ActionConfig(true, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            @Override
            public void onResponse(HomeRecBean response) {
                super.onResponse(response);
                if (response.isSuccess()) {
                    mView.onDataLoadSucc(REQUEST_CDOE_GET_CARD_OTHER, response);
                }
            }

            @Override
            public void onFailed(ApiException apiException) {
                super.onFailed(apiException);
                mView.onDataLoadFailed(REQUEST_CDOE_GET_CARD_OTHER, apiException);
            }
        }));

    }

    @Override
    public void getWeather() {
        addSubscribe(Flowable.interval(0, 60 * 60, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        addSubscribe(mModel.getWeather().subscribeWith(new SimpleRequestSubscriber<WeatherInfoBean>(mView, new SimpleRequestSubscriber.ActionConfig(false, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
                            @Override
                            public void onResponse(WeatherInfoBean response) {
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
                }));

    }

    @Override
    public void getHomeUserInfo(final String key) {
        addSubscribe(mModel.getHomeUserInfo(key).subscribeWith(new SimpleRequestSubscriber<HomeUserInfoBean>(mView, new SimpleRequestSubscriber.ActionConfig(false, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            @Override
            public void onResponse(HomeUserInfoBean response) {
                super.onResponse(response);
                if (response.isSuccess()) {
                    mView.onDataLoadSucc(REQUEST_CDOE_HOME_USER, response);
                }
            }

            @Override
            public void onFailed(ApiException apiException) {
                super.onFailed(apiException);
                mView.onDataLoadFailed(REQUEST_CDOE_HOME_USER, apiException);
            }
        }));

    }

    @Override
    public void getUserQuickCard() {
        addSubscribe(mModel.getUserQuickCard().subscribeWith(new SimpleRequestSubscriber<HomeRecBean>(mView, new SimpleRequestSubscriber.ActionConfig(true, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
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
    public void addUserQuickCard(String quickCardId) {
        addSubscribe(mModel.addUserQuickCard(quickCardId).subscribeWith(new SimpleRequestSubscriber<RootResponse>(mView, new SimpleRequestSubscriber.ActionConfig(true, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            @Override
            public void onResponse(RootResponse response) {
                super.onResponse(response);
                if (response.isSuccess()) {
                    mView.onDataLoadSucc(REQUEST_CDOE_ADD_CARD, response);
                }
            }

            @Override
            public void onFailed(ApiException apiException) {
                super.onFailed(apiException);
                mView.onDataLoadFailed(REQUEST_CDOE_ADD_CARD, apiException);
            }
        }));
    }

    @Override
    public void removeUserQuickCard(String quickCardId) {
        addSubscribe(mModel.removeUserQuickCard(quickCardId).subscribeWith(new SimpleRequestSubscriber<RootResponse>(mView, new SimpleRequestSubscriber.ActionConfig(true, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            @Override
            public void onResponse(RootResponse response) {
                super.onResponse(response);
                if (response.isSuccess()) {
                    mView.onDataLoadSucc(REQUEST_CDOE_REMOVE_CARD, response);
                }
            }

            @Override
            public void onFailed(ApiException apiException) {
                super.onFailed(apiException);
                mView.onDataLoadFailed(REQUEST_CDOE_REMOVE_CARD, apiException);
            }
        }));
    }

}
