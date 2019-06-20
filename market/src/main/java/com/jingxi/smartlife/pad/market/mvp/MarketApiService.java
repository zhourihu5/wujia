package com.jingxi.smartlife.pad.market.mvp;


import com.jingxi.smartlife.pad.market.mvp.data.ServiceDto;
import com.wujia.businesslib.data.ApiResponse;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface MarketApiService {



    @GET("/v1/service/findListByType")
    Flowable<ApiResponse<ServiceDto>> getServiceList(@Query("type") String type, @Query("pageNum") int pageIndex, @Query("pageSize") int pageSize);

}
