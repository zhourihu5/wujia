package com.wujia.lib_common.data.network


import okhttp3.FormBody
import okhttp3.Request
import java.util.*

/**
 * Created by xmren on 2018/5/14.
 */

object RequestParamsUtils {

    fun addGetParams(request: Request, params: Map<String, String>?): Request {
        val requestBuilder = request.url()
                .newBuilder()
        if (params != null && params.isNotEmpty()) {
            for ((key, value) in params) {
                requestBuilder.addQueryParameter(key, value)
            }
        }
        return request.newBuilder().url(requestBuilder.build()).build()
    }

    fun addPostParams(request: Request, params: Map<String, String>?): Request {
        val requestBuilder = request.newBuilder()
        val bodyBuilder = FormBody.Builder()
        if (request.body() is FormBody) {
            val formBody = request.body() as FormBody?
            for (i in 0 until formBody!!.size()) {
                bodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i))
            }
        }
        if (params != null && params.isNotEmpty()) {
            for ((key, value) in params) {
                bodyBuilder.add(key, value)
            }
        }
        return requestBuilder.post(bodyBuilder.build()).build()
    }

}
