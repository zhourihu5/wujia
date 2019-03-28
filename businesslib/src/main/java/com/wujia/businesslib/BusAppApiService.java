package com.wujia.businesslib;


import com.wujia.businesslib.data.TokenBean;

import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface BusAppApiService {

    @GET("/token/checkToken")
    Flowable<TokenBean> getAccessToken(@Query("appid") String appid, @Query("secret") String secret);


    @GET("/token/checkToken")
    Call<TokenBean> getAccessTokenCall(@Query("secret") String secret);

}
