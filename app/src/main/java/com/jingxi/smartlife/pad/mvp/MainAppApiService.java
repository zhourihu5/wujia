package com.jingxi.smartlife.pad.mvp;


import com.jingxi.smartlife.pad.mvp.home.data.HomeRecBean;
import com.jingxi.smartlife.pad.mvp.home.data.HomeUserInfoBean;
import com.jingxi.smartlife.pad.mvp.home.data.LockADBean;
import com.jingxi.smartlife.pad.mvp.home.data.WeatherInfoBean;
import com.wujia.businesslib.data.ApiResponse;
import com.wujia.businesslib.data.CardDetailBean;
import com.wujia.businesslib.data.LoginDTO;
import com.wujia.businesslib.data.MessageBean;
import com.wujia.lib_common.base.RootResponse;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface MainAppApiService {


    //    @FormUrlEncoded
    @GET("/login/checking")
    Flowable<LoginDTO> login(@Query("userName") String mobile, @Query("smsCode") String captcha, @Query("key") String padSn);

    //    @FormUrlEncoded
    @GET("/login/sendMsg")
    Flowable<RootResponse> getCode(@Query("userName") String mobile, @Query("key") String key);

    @GET("/v1/card/all")
    Flowable<HomeRecBean> getQuickCard();

    @GET("/v1/card/user")
    Flowable<HomeRecBean> getUserQuickCard();

    @FormUrlEncoded
    @POST("/v1/card/save")
    Flowable<RootResponse> addUserQuickCard(@Field("id") String quickCardId);

    @FormUrlEncoded
    @POST("/v1/card/remove")
    Flowable<RootResponse> removeUserQuickCard(@Field("id") String quickCardId);

    @GET("/v1/weather/query")
    Flowable<WeatherInfoBean> getWeather();

    @GET("/v1/user/findUserInfo")
    Flowable<HomeUserInfoBean> getHomeUserInfo(@Query("deviceKey") String key);

    @Deprecated
    @GET("/openapi/v1/message/getPropertyMessageById")
    Flowable<MessageBean> getPropertyMessageById(@Query("id") String id);

    @Deprecated
    @GET("/openapi/v1/message/getManagerMessageById")
    Flowable<MessageBean> getManagerMessageById(@Query("id") String id);

    @GET("/v1/screen/findAll")
    Flowable<LockADBean> getScreenSaverByCommunityId();

    @FormUrlEncoded
    @POST("/v1/userFamily/addFamily")
    Flowable<ApiResponse<String>> addFamilyMember(@Field("userName") String userName, @Field("familyId") String familyId);

    @GET("/v1/userFamily/findFamilyUserList")
    Flowable<ApiResponse<List<HomeUserInfoBean.DataBean.UserInfoListBean>>> getFamilyMemberList(@Query("familyId") String familyId);

    @GET("/v1/card/detail")
    Flowable<ApiResponse<CardDetailBean>> getCardDetail(@Query("id") String cardId);
}
