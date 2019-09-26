package com.wujia.lib_common.data.network.exception


import android.net.ParseException
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.wujia.lib_common.base.RootResponse
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException


/**
 * Created by xmren on 2017/9/29.
 */

object ExceptionEngine {

    //对应HTTP的状态码
    private val UNAUTHORIZED = 401
    private val FORBIDDEN = 403
    private val NOT_FOUND = 404
    private val REQUEST_TIMEOUT = 408
    private val INTERNAL_SERVER_ERROR = 500
    private val BAD_GATEWAY = 502
    private val SERVICE_UNAVAILABLE = 503
    private val GATEWAY_TIMEOUT = 504

    fun handleException(e: Throwable): ApiException {


        var ex: ApiException
        if (e is HttpException) {             //HTTP错误
            var msg: String? = "系统开小差，请稍后再试"
            try {
                val body = e.response().errorBody()!!.string()
                val response = Gson().fromJson(body, RootResponse::class.java)
                msg = response.message
            } catch (e1: Exception) {
                e1.printStackTrace()
            }

            when (e.code()) {
                UNAUTHORIZED, FORBIDDEN, NOT_FOUND, REQUEST_TIMEOUT, GATEWAY_TIMEOUT, INTERNAL_SERVER_ERROR, BAD_GATEWAY, SERVICE_UNAVAILABLE -> ex = ApiException(e, ERROR.HTTP_ERROR, msg)
                else -> ex = ApiException(e, ERROR.HTTP_ERROR, msg)
            }
            return ex
        } else if (e is ApiJsonFormateException) {
//            val originData = e.originData
//            try {
//                val jsonObject = JSONObject(originData)
//                val code = jsonObject.optString("code")
//                val msg = jsonObject.optString("msg")
//                ex = ApiException(e, code, msg)
//            } catch (e1: JSONException) {
//                e1.printStackTrace()
//            }
            e.printStackTrace()
            ex = ApiException(e, ERROR.PARSE_ERROR, "数据解析异常")

            return ex
        } else if (e is JsonParseException
                || e is JSONException
                || e is ParseException) {
            ex = ApiException(e, ERROR.PARSE_ERROR, "数据解析异常")
            return ex
        } else if (e is ConnectException) {
            ex = ApiException(e, ERROR.NETWORK_CONNECT_ERROR, "网络未连接，请检查网络")
            return ex
        } else if (e is SocketTimeoutException) {//超时
            ex = ApiException(e, ERROR.NETWORK_TIMEOUT, "网络连接超时，请检查网络")
            return ex
        } else if (e is TokenException) {
            ex = ApiException(e, ERROR.HTTP_ERROR, e.message)
            return ex
        } else {
            ex = ApiException(e, ERROR.UNKNOWN, "未知错误")//未知错误
            return ex
        }
    }
}

