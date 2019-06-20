package com.jingxi.smartlife.pad.market.mvp;


import com.jingxi.smartlife.pad.market.mvp.data.FindBannerBean;
import com.jingxi.smartlife.pad.market.mvp.data.ServiceBean;
import com.jingxi.smartlife.pad.market.mvp.data.ServiceDto;
import com.wujia.businesslib.data.ApiResponse;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface MarketApiService {

    @Deprecated
    @GET("/openapi/v1/wj/getServiceClassification")
    Flowable<ServiceBean> getServiceClassification(@Query("communityId") String communityId, @Query("category") String category, @Query("pageIndex") int pageIndex, @Query("pageIndex") int pageSize);


    @GET("/v1/service/findListByType")
    Flowable<ApiResponse<ServiceDto>> getServiceList(@Query("type") String type, @Query("pageNum") int pageIndex, @Query("pageSize") int pageSize);

    @Deprecated
    @FormUrlEncoded
    @POST("/api/v2/adServer/queryApiAdInfoByCommunityIdAndPlatform")
    Flowable<FindBannerBean> getBanner(@Field("class") String _class, @Field("communityId") String communityId);
}
