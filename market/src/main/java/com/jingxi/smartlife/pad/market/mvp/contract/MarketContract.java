package com.jingxi.smartlife.pad.market.mvp.contract;

import com.jingxi.smartlife.pad.market.mvp.data.FindBannerBean;
import com.jingxi.smartlife.pad.market.mvp.data.ServiceBean;
import com.wujia.lib_common.base.BasePresenter;
import com.wujia.lib_common.base.CommonDataLoadView;
import com.wujia.lib_common.base.IBaseModle;

import io.reactivex.Flowable;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-27
 * description ：
 */
public interface MarketContract {
    interface Model extends IBaseModle {

        Flowable<ServiceBean> getServiceClassification(String communityId, String category, int pageIndex, int pageSize);

        Flowable<ServiceBean> getAllService(String communityId, int pageIndex, int pageSize);

        Flowable<FindBannerBean> getBanner(String communityId);

    }

    interface View extends CommonDataLoadView {
    }

    interface Presenter extends BasePresenter<View> {

        void getServiceClassification(String communityId, String category, int pageIndex, int pageSize);

        void getAllService(String communityId, int pageIndex, int pageSize);

        void getBanner(String communityId);

    }
}
