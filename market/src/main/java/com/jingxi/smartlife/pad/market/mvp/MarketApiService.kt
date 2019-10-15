package com.jingxi.smartlife.pad.market.mvp


import com.jingxi.smartlife.pad.market.mvp.data.*
import com.wujia.businesslib.data.ApiResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface MarketApiService {


    @GET("/v1/service/findListByType")
    fun getServiceList(@Query("type") type: String, @Query("pageNum") pageIndex: Int, @Query("pageSize") pageSize: Int): Flowable<ApiResponse<ServiceDto>>

    @GET("/v1/activity/findAll")
    fun getGroupBuyList(@Query("pageNum") pageIndex: Int, @Query("pageSize") pageSize: Int,@Query("communityId")communityId:Int):Flowable<ApiResponse<GroupBuyVo>>

    @GET("/v1/activity/findOtherList")
    fun getGroupBuyOtherList( communtityId: Int): Flowable<ApiResponse<GroupBuyVo>>

    @GET("/v1/activity/findByActivityId")
    fun getGroupBuyDetail(@Query("activityId")id: String?): Flowable<ApiResponse<GroupBuyDetailVo>>

    @GET("/v1/activity/generateQrCode")
//    @GET("/test/generateOrderConfirmQrCode")
    fun generateQrCodeOrderConfirm(@Query("activityId")id: String): Flowable<ApiResponse<String>>

    @GET("/v1/order/generateQrCode")
//    @GET("/test/generateOrderConfirmQrCode")
    fun generateQrCodeOrderDetail(@Query("id")id: String): Flowable<ApiResponse<String>>


    @GET("/v1/order/findList")
     fun getOrderList(@Query("status")status: String, @Query("pageNum")page: Int,@Query("pageSize") pageSize: Int): Flowable<ApiResponse<OrderVo>>
    @GET("/v1/order/findOrderDetail")
    fun getOrderDetail(@Query("orderId")id: String?): Flowable<ApiResponse<OrderDetailVo>>

    @POST("/v1/order/receiveOrderPad")
    fun rereiveOrder(@Query("id")id: String?): Flowable<ApiResponse<Any>>

    @POST("/v1/order/cancelOrderPad")
    abstract fun cancelOrder(@Query("id")id: String?): Flowable<ApiResponse<Any>>



}
