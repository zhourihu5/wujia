package com.jingxi.smartlife.pad.market.mvp


import com.jingxi.smartlife.pad.market.mvp.data.GroupBuyDetailVo
import com.jingxi.smartlife.pad.market.mvp.data.GroupBuyVo
import com.jingxi.smartlife.pad.market.mvp.data.ServiceDto
import com.wujia.businesslib.data.ApiResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query


interface MarketApiService {


    @GET("/v1/service/findListByType")
    fun getServiceList(@Query("type") type: String, @Query("pageNum") pageIndex: Int, @Query("pageSize") pageSize: Int): Flowable<ApiResponse<ServiceDto>>

    @GET("/v1/activity/findAll")
    fun getGroupBuyList(@Query("pageNum") pageIndex: Int, @Query("pageSize") pageSize: Int,@Query("communityId")communityId:Int):Flowable<ApiResponse<GroupBuyVo>>

    @GET("/v1/activity/findByActivityId")
    fun getGroupBuyDetail(@Query("activityId")id: String?): Flowable<ApiResponse<GroupBuyDetailVo>>

    @GET("/v1/activity/generateQrCode")
//    @GET("/test/generateQrCode")
    fun generateQrCode(@Query("activityId")id: String): Flowable<ApiResponse<String>>

}
