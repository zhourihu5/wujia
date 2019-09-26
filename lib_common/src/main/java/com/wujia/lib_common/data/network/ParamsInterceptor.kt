package com.wujia.lib_common.data.network

import java.io.IOException
import java.io.UnsupportedEncodingException
import java.util.ArrayList
import java.util.Collections
import java.util.HashMap

import okhttp3.FormBody
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

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


    //get请求 添加公共参数 签名
    private fun addGetSignParam(request: Request): Request {
        var request = request
        val httpUrl = request.url().newBuilder().build()

        //添加签名
        val nameSet = httpUrl.queryParameterNames()
        val nameList = ArrayList<String>()
        nameList.addAll(nameSet)
        Collections.sort(nameList)

        val buffer = StringBuilder()
        for (i in nameList.indices) {
            buffer.append(nameList[i]).append(httpUrl.queryParameterValues(nameList[i])[0])
        }
        //        httpUrl = httpUrl.newBuilder()
        //                .addQueryParameter(Constants.COMMON_REQUEST_SIGN, RequestParamsUtils.getSignValue(buffer.toString()))
        //                .build();
        request = request.newBuilder().url(httpUrl).build()
        return request
    }

    //post 添加签名和公共参数
    @Throws(UnsupportedEncodingException::class)
    private fun addPostSignParam(request: Request): Request {
        var request = request

        val requestBuilder = request.newBuilder()
        val bodyBuilder = FormBody.Builder()
        if (request.body() is FormBody) {
            val formBody = request.body() as FormBody?
            for (i in 0 until formBody!!.size()) {
                bodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i))
            }
        }
        val formBody = bodyBuilder.build()
        val bodyMap = HashMap<String, String>()
        val nameList = ArrayList<String>()

        for (i in 0 until formBody.size()) {
            nameList.add(formBody.name(i))
            bodyMap[formBody.name(i)] = formBody.value(i)
        }
        Collections.sort(nameList)

        val builder = StringBuilder()
        for (i in nameList.indices) {
            builder.append(nameList[i])
                    .append(bodyMap[nameList[i]])
        }

        //        formBody = bodyBuilder.
        //                add(Constants.COMMON_REQUEST_SIGN, RequestParamsUtils.getSignValue(builder.toString()))
        //                .build();
        request = requestBuilder.post(formBody).build()
        return request
    }

    private fun buildCommonParamsMap(): Map<String, String> {

        //        queryParamsMap.put(Constants.COMMON_REQUEST_APP_ID, Constants.APP_ID);
        //        queryParamsMap.put(Constants.COMMON_REQUEST_CLIENT_VERSION, AppUtil.getVersionCode(AppContext.get()) + "");
        //        queryParamsMap.put(Constants.COMMON_REQUEST_TIMESTAMP, RequestParamsUtils.getSecondTimestamp(new Date()) + "");
        //        queryParamsMap.put(Constants.COMMON_REQUEST_UUID, AppUtil.getDeviceInfo(AppContext.get()));
        return HashMap()
    }


}
