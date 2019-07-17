package com.wujia.lib_common.data.network


import java.util.Date

import okhttp3.FormBody
import okhttp3.HttpUrl
import okhttp3.Request

/**
 * Created by xmren on 2018/5/14.
 */

object RequestParamsUtils {

    fun getSignValue(paramStr: String): String {
//        if (null != paramStr && !"".equals(paramStr)) {
        //            try {
        //                return URLEncoder.encode(Encrypt.SHA256(paramStr + NetConfig.signKey), "utf-8");
        //            } catch (UnsupportedEncodingException e) {
        //                e.printStackTrace();
        //            }
        //        }
        return ""
    }

    fun getSecondTimestamp(date: Date?): Int {
        if (null == date) {
            return 0
        }
        val timestamp = (date.time / 1000).toString()
        return Integer.valueOf(timestamp)
    }

    fun addGetParams(request: Request, params: Map<String, String>?): Request {
        val requestBuilder = request.url()
                .newBuilder()
        if (params != null && params.size > 0) {
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
        if (params != null && params.size > 0) {
            for ((key, value) in params) {
                bodyBuilder.add(key, value)
            }
        }
        return requestBuilder.post(bodyBuilder.build()).build()
    }

}
