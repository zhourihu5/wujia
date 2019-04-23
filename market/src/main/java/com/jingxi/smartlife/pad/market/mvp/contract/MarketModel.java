package com.jingxi.smartlife.pad.market.mvp.contract;

import com.jingxi.smartlife.pad.market.mvp.MarketApiService;
import com.jingxi.smartlife.pad.market.mvp.data.FIndBannerBean;
import com.jingxi.smartlife.pad.market.mvp.data.ServiceBean;
import com.wujia.businesslib.base.BaseModel;
import com.wujia.lib_common.data.network.RxUtil;

import io.reactivex.Flowable;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-27
 * description ：
 */
public class MarketModel extends BaseModel implements MarketContract.Model {


    @Override
    public Flowable<ServiceBean> getServiceClassification(String communityId, String category, int pageIndex, int pageSize) {
        return mHttpHelper.create(MarketApiService.class).getServiceClassification(communityId, category, pageIndex, pageSize).compose(RxUtil.<ServiceBean>rxSchedulerHelper());

    }

    @Override
    public Flowable<ServiceBean> getAllService(String communityId, int pageIndex, int pageSize) {
        return mHttpHelper.create(MarketApiService.class).getAllService(communityId,  pageIndex, pageSize).compose(RxUtil.<ServiceBean>rxSchedulerHelper());
    }

    @Override
    public Flowable<FIndBannerBean> getBanner(String communityId) {
        return mHttpHelper.create(MarketApiService.class).getBanner("discover",communityId ).compose(RxUtil.<FIndBannerBean>rxSchedulerHelper());

    }
}
