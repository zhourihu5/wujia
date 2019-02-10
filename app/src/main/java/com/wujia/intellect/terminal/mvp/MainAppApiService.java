package com.wujia.intellect.terminal.mvp;


import com.google.gson.JsonArray;
import com.wujia.businesslib.data.ApiResponse;

import io.reactivex.Flowable;
import retrofit2.http.GET;


public interface MainAppApiService {

    @GET("")
    Flowable<ApiResponse<String>> getTestNet();

}
