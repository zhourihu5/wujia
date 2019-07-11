package com.wujia.businesslib;


import com.wujia.businesslib.data.ApiResponse;
import com.wujia.businesslib.data.MsgDto;
import com.wujia.businesslib.data.VersionBean;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface BusApiService {

    @GET("/v1/message/findListByUserId")
    Flowable<ApiResponse<MsgDto>> getMsg(@Query("familyId") String familyId, @Query("type") String type, @Query("status") String status, @Query("pageNum") int pageNo, @Query("pageSize") int pageSize);

    @GET("/v1/message/findTopThreeByUserId")
    Flowable<ApiResponse<List<MsgDto.ContentBean>>> getTop3UnReadMsg(@Query("familyId")String familyId);

    @GET("/v1/message/isUnRead")
    Flowable<ApiResponse<Boolean>> isUnReadMsg();


    @FormUrlEncoded
    @POST("/v1/message/updateIsRead")
    Flowable<ApiResponse<Object>> readMsg(@Field("messageId") String id);

    @FormUrlEncoded
    @POST("/v1/service/subscribe")
    Flowable<ApiResponse<Object>> subscribe(@Field("serviceId") String serviceId, @Field("isSubscribe") String isSubscribe);

    @GET("/v1/system/version")
    Flowable<VersionBean> checkVersion();

    @FormUrlEncoded
    @POST("/system/updateVer")
    Flowable<ApiResponse<Object>> updateVer(@Field("versionName")String versionName,@Field("key") String key,@Field("versionCode") String versionCode);
//    Flowable<ApiResponse<Object>> updateVer(@Body VersionUpdate versionUpdate);
}
