package com.wujia.businesslib


import com.wujia.businesslib.data.ApiResponse
import com.wujia.businesslib.data.MsgDto
import com.wujia.businesslib.data.VersionBean

import io.reactivex.Flowable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface BusApiService {

    @get:GET("/v1/message/isUnRead")
    val isUnReadMsg: Flowable<ApiResponse<Boolean>>

    @GET("/v1/message/findListByUserId")
    fun getMsg(@Query("familyId") familyId: String, @Query("type") type: String, @Query("status") status: String, @Query("pageNum") pageNo: Int, @Query("pageSize") pageSize: Int): Flowable<ApiResponse<MsgDto>>

    @GET("/v1/message/findTopThreeByUserId")
    fun getTop3UnReadMsg(@Query("familyId") familyId: String): Flowable<ApiResponse<List<MsgDto.ContentBean>>>


    @FormUrlEncoded
    @POST("/v1/message/updateIsRead")
    fun readMsg(@Field("messageId") id: String): Flowable<ApiResponse<Any>>

    @FormUrlEncoded
    @POST("/v1/service/subscribe")
    fun subscribe(@Field("serviceId") serviceId: String, @Field("isSubscribe") isSubscribe: String): Flowable<ApiResponse<Any>>

    @GET("/v1/system/version")
    fun checkVersion(): Flowable<VersionBean>

    @FormUrlEncoded
    @POST("/system/updateVer")
    fun updateVer(@Field("versionName") versionName: String, @Field("key") key: String, @Field("versionCode") versionCode: String): Flowable<ApiResponse<Any>>
    //    Flowable<ApiResponse<Object>> updateVer(@Body VersionUpdate versionUpdate);
}
