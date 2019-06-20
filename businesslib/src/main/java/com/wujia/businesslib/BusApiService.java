package com.wujia.businesslib;


import com.wujia.businesslib.data.ApiResponse;
import com.wujia.businesslib.data.LoginDTO;
import com.wujia.businesslib.data.MessageBean;
import com.wujia.businesslib.data.MsgDto;
import com.wujia.businesslib.data.RootResponse;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface BusApiService {

    @GET("/v1/message/findListByUserId")
    Flowable<ApiResponse<MsgDto>> getMsg(@Query("type") String type,@Query("status") String status,@Query("pageNum") int pageNo,@Query("pageSize")int pageSize);

    @GET("/v1/message/findTopThreeByUserId")
    Flowable<ApiResponse<List<MsgDto.ContentBean>>> getTop3UnReadMsg();

    @GET("/v1/message/isUnRead")
    Flowable<ApiResponse<Boolean>> isUnReadMsg();


    @FormUrlEncoded
    @POST("/v1/message/updateIsRead")
    Flowable<ApiResponse<Object>> readMsg(@Field("messageId") String id);

    @FormUrlEncoded
    @POST("/v1/service/subscribe")
    Flowable<ApiResponse<Object>> subscribe(@Field("serviceId")String serviceId, @Field("isSubscribe") String isSubscribe);


}
