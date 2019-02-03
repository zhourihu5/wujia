package com.wujia.intellect.terminal.mvp;


import com.google.gson.JsonArray;
import com.wujia.businesslib.data.ApiResponse;

import io.reactivex.Flowable;
import retrofit2.http.GET;


public interface MainAppApiService {

    @GET("/v2/home/home/conf?token=5c5292a25c9eb&member_id=43017&timestamp=1548915362&source_id=2&source=3&uuid=7b46789f45429120&version=18&sign=9f2383019a3ab8bdc9e37175730fed04f55e6ba5cec2a85d1d5d083e80d9f77a")
    Flowable<ApiResponse<String>> getTestNet();

}
