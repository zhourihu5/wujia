package com.wujia.intellect.terminal.mvp;


import com.wujia.businesslib.data.ApiResponse;
import com.wujia.businesslib.data.TokenBean;
import com.wujia.businesslib.data.UserBean;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface MainAppApiService {

    @GET("")
    Flowable<ApiResponse<String>> getTestNet();

    @GET("/openapi/v1/token")
    Flowable<TokenBean> getAccessToken(@Query("type") String type, @Query("appid") String appid, @Query("secret") String secret);

    @FormUrlEncoded
    @POST("/openapi/v1/user/padLogin")
    Flowable<UserBean> login(@Field("mobile") String mobile, @Field("captcha") String captcha, @Field("padSn") String padSn);

}
