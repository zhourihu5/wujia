package com.jingxi.smartlife.pad.mvp;


import com.jingxi.smartlife.pad.mvp.home.data.HomeRecBean;
import com.jingxi.smartlife.pad.mvp.home.data.WeatherBean;
import com.wujia.businesslib.data.ApiResponse;
import com.wujia.businesslib.data.RootResponse;
import com.wujia.businesslib.data.UserBean;
import com.jingxi.smartlife.pad.mvp.home.data.HomeRecBean;
import com.wujia.businesslib.data.MessageBean;
import com.jingxi.smartlife.pad.mvp.home.data.WeatherBean;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface MainAppApiService {

    @GET("")
    Flowable<ApiResponse<String>> getTestNet();

    @FormUrlEncoded
    @POST("/openapi/v1/user/padLogin")
    Flowable<UserBean> login(@Field("mobile") String mobile, @Field("captcha") String captcha, @Field("padSn") String padSn);

    @FormUrlEncoded
    @POST("/openapi/v1/user/sendCaptcha")
    Flowable<RootResponse> getCode(@Field("mobile") String mobile);

    @GET("/openapi/v1/wj/getQuickCard")
    Flowable<HomeRecBean> getQuickCard(@Query("communityId") String communityId);

    @GET("/openapi/v1/wj/getUserQuickCard")
    Flowable<HomeRecBean> getUserQuickCard(@Query("openid") String openid);

    @FormUrlEncoded
    @POST("/openapi/v1/wj/userQuickCardSubscription")
    Flowable<RootResponse> addUserQuickCard(@Field("openid") String openid, @Field("quickCardId") String quickCardId);

    @FormUrlEncoded
    @POST("/openapi/v1/wj/userQuickCardRemove")
    Flowable<RootResponse> removeUserQuickCard(@Field("openid") String openid, @Field("quickCardId") String quickCardId);

    @GET("/openapi/v1/community/getWeather")
    Flowable<WeatherBean> getWeather(@Query("communityId") String communityId);


    @GET("/openapi/v1/message/getPropertyMessageById")
    Flowable<MessageBean> getPropertyMessageById(@Query("id") String id);

    @GET("/openapi/v1/message/getManagerMessageById")
    Flowable<MessageBean> getManagerMessageById(@Query("id") String id);
}
