package com.wujia.intellect.terminal.mvp;


import com.wujia.businesslib.data.ApiResponse;
import com.wujia.intellect.terminal.mvp.login.data.TokenBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface MainAppApiService {

    @GET("")
    Flowable<ApiResponse<String>> getTestNet();

    @GET("/openapi/v1/token")
    Flowable<TokenBean> getAccessToken(@Query("type") String type, @Query("appid") String appid, @Query("secret") String secret);

}
