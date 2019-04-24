package com.jingxi.smartlife.pad.market.mvp;


import com.jingxi.smartlife.pad.market.mvp.data.FindBannerBean;
import com.jingxi.smartlife.pad.market.mvp.data.ServiceBean;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface MarketApiService {

    @GET("/openapi/v1/wj/getServiceClassification")
    Flowable<ServiceBean> getServiceClassification(@Query("communityId") String communityId, @Query("category") String category, @Query("pageIndex") int pageIndex, @Query("pageIndex") int pageSize);


    @GET("/openapi/v1/wj/getAllService")
    Flowable<ServiceBean> getAllService(@Query("communityId") String communityId, @Query("pageIndex") int pageIndex, @Query("pageIndex") int pageSize);

    @FormUrlEncoded
    @POST("/api/v2/adServer/queryApiAdInfoByCommunityIdAndPlatform")
    Flowable<FindBannerBean> getBanner(@Field("class") String _class, @Field("communityId") String communityId);
}
