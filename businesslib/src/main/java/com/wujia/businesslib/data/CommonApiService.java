package com.wujia.businesslib.data;

//import com.abctime.businesslib.mvp.preview.data.BookInfo;
//import com.abctime.businesslib.mvp.preview.data.TopicData;
//import com.abctime.businesslib.mvp.preview.data.TopicData;


import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by xmren on 2018/5/18.
 */

public interface CommonApiService {
    @FormUrlEncoded
    @POST("/v2/member/passport/login")
    Flowable<ApiResponse<UserEntity>> doLogin(@Field("phone") String phone, @Field("code") String code);

    @POST("/v2/member/passport/guest-login")
    Flowable<ApiResponse<UserEntity>> doGuestLogin();

    @GET("/v2/member/member/info")
    Flowable<ApiResponse<UserEntity>> requestUserInfo(@Query("member_id") String memberId);


    @FormUrlEncoded
    @POST("/v2/member/message/send ")
    Flowable<ApiResponse<List<String>>> requestCode(@Field("phone")String phoneNum);

    @FormUrlEncoded
    @POST("/v2/member/message/check")
    Flowable<ApiResponse> verifyCode(@Field("phone")String phoneNum,@Field("code")String code);

    @FormUrlEncoded
    @POST("v2/lesson/lesson/list")
    Flowable<ApiResponse> requestTvList();

//    @GET("/v2/book/book/info")
//    Flowable<ApiResponse<BookInfo>> getBookInfo(@Query("id") int id, @Query("member_id") int memid, @Query("type") int type);
//
//
//
//    @GET("/v2/topic/topic/book-topic")
//    Flowable<ApiResponse<ArrayList<TopicData>>> getTopicData(@Query("book_id") int book_id, @Query("member_id") int memid);

    @FormUrlEncoded
    @POST("/v2/member/passport/guest-register")
    Flowable<ApiResponse<UserEntity>> doGuestRegister(@Field("phone") String phone,@Field("code") String code);
}
