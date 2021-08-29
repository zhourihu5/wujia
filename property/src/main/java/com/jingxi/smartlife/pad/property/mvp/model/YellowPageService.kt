package com.jingxi.smartlife.pad.property.mvp.model


import com.jingxi.smartlife.pad.property.mvp.data.YellowPage
import com.wujia.businesslib.data.ApiResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query


interface YellowPageService {


    @GET("/v1/communtityInfo/findList")
    fun getYellowPageList(@Query("communtityId") communityId: Int): Flowable<ApiResponse<List<YellowPage>>>

}

