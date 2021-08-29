package com.jingxi.smartlife.pad.safe.mvp

import com.wujia.lib_common.base.RootResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * author ：shenbingkai@163.com
 * date ：2019-10-17
 * description ：
 */

interface SafeApiService {

    @GET("/v1/apply/openDoor")
    fun openDoor(@Query("fid") id: String): Flowable<RootResponse>
}