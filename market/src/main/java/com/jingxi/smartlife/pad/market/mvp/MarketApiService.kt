package com.jingxi.smartlife.pad.market.mvp


import com.jingxi.smartlife.pad.market.mvp.data.ServiceDto
import com.wujia.businesslib.data.ApiResponse

import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query


interface MarketApiService {


    @GET("/v1/service/findListByType")
    fun getServiceList(@Query("type") type: String, @Query("pageNum") pageIndex: Int, @Query("pageSize") pageSize: Int): Flowable<ApiResponse<ServiceDto>>

}
