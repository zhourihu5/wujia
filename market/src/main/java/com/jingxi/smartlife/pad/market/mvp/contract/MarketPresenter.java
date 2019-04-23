package com.jingxi.smartlife.pad.market.mvp.contract;

import com.jingxi.smartlife.pad.market.mvp.data.FIndBannerBean;
import com.jingxi.smartlife.pad.market.mvp.data.ServiceBean;
import com.wujia.businesslib.base.RxPresenter;
import com.wujia.lib_common.data.network.SimpleRequestSubscriber;
import com.wujia.lib_common.data.network.exception.ApiException;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-27
 * description ：
 */
public class MarketPresenter extends RxPresenter<MarketContract.View> implements MarketContract.Presenter {

    public static final int REQUEST_CDOE_GET_SERVICE_FIND = 1;
    public static final int REQUEST_CDOE_GET_SERVICE_ALL = 2;
    public static final int REQUEST_CDOE_GET_BANNER = 3;


    private MarketModel mModel;

    public MarketPresenter() {
        this.mModel = new MarketModel();
    }

    @Override
    public void getServiceClassification(String communityId, String category, int pageIndex, int pageSize) {
        addSubscribe(mModel.getServiceClassification(communityId, category, pageIndex, pageSize).subscribeWith(new SimpleRequestSubscriber<ServiceBean>(mView, new SimpleRequestSubscriber.ActionConfig(true, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            @Override
            public void onResponse(ServiceBean response) {
                super.onResponse(response);
                if (response.isSuccess()) {
                    mView.onDataLoadSucc(REQUEST_CDOE_GET_SERVICE_FIND, response);
                }
            }

            @Override
            public void onFailed(ApiException apiException) {
                super.onFailed(apiException);
                mView.onDataLoadFailed(REQUEST_CDOE_GET_SERVICE_FIND, apiException);
            }
        }));
    }

    @Override
    public void getAllService(String communityId, int pageIndex, int pageSize) {
        addSubscribe(mModel.getAllService(communityId, pageIndex, pageSize).subscribeWith(new SimpleRequestSubscriber<ServiceBean>(mView, new SimpleRequestSubscriber.ActionConfig(true, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            @Override
            public void onResponse(ServiceBean response) {
                super.onResponse(response);
                if (response.isSuccess()) {
                    mView.onDataLoadSucc(REQUEST_CDOE_GET_SERVICE_ALL, response);
                }
            }

            @Override
            public void onFailed(ApiException apiException) {
                super.onFailed(apiException);
                mView.onDataLoadFailed(REQUEST_CDOE_GET_SERVICE_ALL, apiException);
            }
        }));
    }

    @Override
    public void getBanner(String communityId) {
        addSubscribe(mModel.getBanner(communityId).subscribeWith(new SimpleRequestSubscriber<FIndBannerBean>(mView, new SimpleRequestSubscriber.ActionConfig(true, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            @Override
            public void onResponse(FIndBannerBean response) {
                super.onResponse(response);
                if (response.isSuccess()) {
                    mView.onDataLoadSucc(REQUEST_CDOE_GET_BANNER, response);
                }
            }

            @Override
            public void onFailed(ApiException apiException) {
                super.onFailed(apiException);
                mView.onDataLoadFailed(REQUEST_CDOE_GET_BANNER, apiException);
            }
        }));
    }
}
