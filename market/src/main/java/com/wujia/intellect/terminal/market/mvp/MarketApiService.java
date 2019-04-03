package com.wujia.intellect.terminal.market.mvp;


import com.wujia.businesslib.data.TokenBean;
import com.wujia.intellect.terminal.market.mvp.data.ServiceBean;

import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface MarketApiService {

    @GET("/openapi/v1/wj/getServiceClassification")
    Flowable<ServiceBean> getServiceClassification(@Query("communityId") String communityId, @Query("category") String category, @Query("pageIndex") int pageIndex, @Query("pageIndex") int pageSize);


    @GET("/openapi/v1/wj/getAllService")
    Flowable<ServiceBean> getAllService(@Query("communityId") String communityId, @Query("pageIndex") int pageIndex, @Query("pageIndex") int pageSize);


}
