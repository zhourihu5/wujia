package com.wujia.intellect.terminal.market.mvp.contract;

import com.wujia.businesslib.base.BaseModel;
import com.wujia.intellect.terminal.market.mvp.MarketApiService;
import com.wujia.intellect.terminal.market.mvp.data.ServiceBean;
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
}
