package com.jingxi.smartlife.pad.mvp;


import com.jingxi.smartlife.pad.mvp.home.data.HomeRecBean;
import com.jingxi.smartlife.pad.mvp.home.data.HomeUserInfoBean;
import com.jingxi.smartlife.pad.mvp.home.data.LockADBean;
import com.jingxi.smartlife.pad.mvp.home.data.WeatherInfoBean;
import com.wujia.businesslib.data.ApiResponse;
import com.wujia.businesslib.data.LoginDTO;
import com.wujia.businesslib.data.RootResponse;
import com.wujia.businesslib.data.MessageBean;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface MainAppApiService {

    @GET("")
    Flowable<ApiResponse<String>> getTestNet();

//    @FormUrlEncoded
    @GET("/login/checking")
    Flowable<LoginDTO> login(@Query("userName") String mobile, @Query("smsCode") String captcha, @Query("key") String padSn);

//    @FormUrlEncoded
    @GET("/login/sendMsg")
    Flowable<RootResponse> getCode(@Query("userName") String mobile,@Query("key")String key);

    @GET("/v1/card/all")
    Flowable<HomeRecBean> getQuickCard();

    @GET("/v1/card/user")
    Flowable<HomeRecBean> getUserQuickCard();

    @FormUrlEncoded
    @POST("/openapi/v1/wj/userQuickCardSubscription")
    Flowable<RootResponse> addUserQuickCard(@Field("openid") String openid, @Field("quickCardId") String quickCardId);

    @FormUrlEncoded
    @POST("/openapi/v1/wj/userQuickCardRemove")
    Flowable<RootResponse> removeUserQuickCard(@Field("openid") String openid, @Field("quickCardId") String quickCardId);

    @GET("/v1/weather/query")
    Flowable<WeatherInfoBean> getWeather();

    @GET("/v1/user/findUserInfo")
    Flowable<HomeUserInfoBean> getHomeUserInfo(@Query("deviceKey") String key);


    @GET("/openapi/v1/message/getPropertyMessageById")
    Flowable<MessageBean> getPropertyMessageById(@Query("id") String id);

    @GET("/openapi/v1/message/getManagerMessageById")
    Flowable<MessageBean> getManagerMessageById(@Query("id") String id);

    @GET("/v1/screen/findAll")
    Flowable<LockADBean> getScreenSaverByCommunityId();
}
