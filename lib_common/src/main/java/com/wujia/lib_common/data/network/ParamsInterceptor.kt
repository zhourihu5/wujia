package com.wujia.lib_common.data.network

import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.util.*

class ParamsInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()


        val params = buildCommonParamsMap()
        if (request.method() == "GET") {
            request = RequestParamsUtils.addGetParams(request, params)
            //            request = addGetSignParam(request);
        } else if (request.method() == "POST") {
            request = RequestParamsUtils.addPostParams(request, params)
            //            request = addPostSignParam(request);
        }

        return chain.proceed(request)
    }


    private fun buildCommonParamsMap(): Map<String, String> {

        //        queryParamsMap.put(Constants.COMMON_REQUEST_APP_ID, Constants.APP_ID);
        //        queryParamsMap.put(Constants.COMMON_REQUEST_CLIENT_VERSION, AppUtil.getVersionCode(AppContext.get()) + "");
        //        queryParamsMap.put(Constants.COMMON_REQUEST_TIMESTAMP, RequestParamsUtils.getSecondTimestamp(new Date()) + "");
        //        queryParamsMap.put(Constants.COMMON_REQUEST_UUID, AppUtil.getDeviceInfo(AppContext.get()));
        return HashMap()
    }


}
