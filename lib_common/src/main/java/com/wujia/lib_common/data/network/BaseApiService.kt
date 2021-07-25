package com.wujia.lib_common.data.network

import com.google.gson.JsonObject

import io.reactivex.Flowable
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path
import retrofit2.http.QueryMap
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * Created by xmren on 2018/3/9.
 */

interface BaseApiService {


    @Multipart
    @POST("{url}")
    fun upLoadFile(
            @Path("url") url: JsonObject,
            @Part("image\"; filename=\"image.jpg") requestBody: RequestBody): Flowable<JsonObject>

    @Streaming
    @GET
    fun downloadFile(@Url fileUrl: String): Flowable<ResponseBody>
}
