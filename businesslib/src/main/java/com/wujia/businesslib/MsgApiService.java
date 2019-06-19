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


public interface MsgApiService {

    @GET("/v1/message/list")
    Flowable<ApiResponse<MsgDto>> getMsg(@Query("pageNo") String pageNo,@Query("type") String type,@Query("status") String status);

    @GET("/v1/message/top3UnRead")
    Flowable<ApiResponse<List<MsgDto.ContentBean>>> getTop3UnReadMsg();

    @GET("/v1/message/isUnRead")
    Flowable<ApiResponse<Boolean>> isUnReadMsg();


    @FormUrlEncoded
    @POST("/v1/message/read")
    Flowable<ApiResponse<Object>> readMsg(@Field("id") String id);

//    @FormUrlEncoded
//    @POST("/openapi/v1/wj/userQuickCardRemove")
//    Flowable<RootResponse> removeUserQuickCard(@Field("openid") String openid, @Field("quickCardId") String quickCardId);

}
