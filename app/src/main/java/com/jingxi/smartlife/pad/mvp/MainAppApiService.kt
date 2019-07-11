package com.jingxi.smartlife.pad.mvp


import com.jingxi.smartlife.pad.mvp.home.data.HomeRecBean
import com.jingxi.smartlife.pad.mvp.home.data.HomeUserInfoBean
import com.jingxi.smartlife.pad.mvp.home.data.LockADBean
import com.jingxi.smartlife.pad.mvp.home.data.WeatherInfoBean
import com.wujia.businesslib.data.ApiResponse
import com.wujia.businesslib.data.CardDetailBean
import com.wujia.businesslib.data.LoginDTO
import com.wujia.businesslib.data.MessageBean
import com.wujia.lib_common.base.RootResponse

import io.reactivex.Flowable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface MainAppApiService {

    @get:GET("/v1/card/all")
    val quickCard: Flowable<HomeRecBean>

    @get:GET("/v1/card/user")
    val userQuickCard: Flowable<HomeRecBean>

    @get:GET("/v1/weather/query")
    val weather: Flowable<WeatherInfoBean>

    @get:GET("/v1/screen/findAll")
    val screenSaverByCommunityId: Flowable<LockADBean>


    //    @FormUrlEncoded
    @GET("/login/checking")
    fun login(@Query("userName") mobile: String, @Query("smsCode") captcha: String, @Query("key") padSn: String): Flowable<LoginDTO>

    //    @FormUrlEncoded
    @GET("/login/sendMsg")
    fun getCode(@Query("userName") mobile: String, @Query("key") key: String): Flowable<RootResponse>

    @FormUrlEncoded
    @POST("/v1/card/save")
    fun addUserQuickCard(@Field("id") quickCardId: String): Flowable<RootResponse>

    @FormUrlEncoded
    @POST("/v1/card/remove")
    fun removeUserQuickCard(@Field("id") quickCardId: String): Flowable<RootResponse>

    @GET("/v1/user/findUserInfo")
    fun getHomeUserInfo(@Query("deviceKey") key: String): Flowable<HomeUserInfoBean>

    @Deprecated("")
    @GET("/openapi/v1/message/getPropertyMessageById")
    fun getPropertyMessageById(@Query("id") id: String): Flowable<MessageBean>

    @Deprecated("")
    @GET("/openapi/v1/message/getManagerMessageById")
    fun getManagerMessageById(@Query("id") id: String): Flowable<MessageBean>

    @FormUrlEncoded
    @POST("/v1/userFamily/addFamily")
    fun addFamilyMember(@Field("userName") userName: String, @Field("familyId") familyId: String): Flowable<ApiResponse<String>>

    @GET("/v1/userFamily/findFamilyUserList")
    fun getFamilyMemberList(@Query("familyId") familyId: String): Flowable<ApiResponse<List<HomeUserInfoBean.DataBean.UserInfoListBean>>>

    @GET("/v1/card/detail")
    fun getCardDetail(@Query("id") cardId: String): Flowable<ApiResponse<CardDetailBean>>
}
