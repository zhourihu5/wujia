package com.wujia.lib_common.data.network.exception;


import android.net.ParseException;

import com.google.gson.JsonParseException;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import retrofit2.HttpException;


/**
 * Created by xmren on 2017/9/29.
 */

public class ExceptionEngine {

    //对应HTTP的状态码
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ApiException handleException(Throwable e) {


        ApiException ex;
        if (e instanceof HttpException) {             //HTTP错误
            HttpException httpException = (HttpException) e;
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ex = new ApiException(e, ERROR.HTTP_ERROR, "系统开小差，请稍后再试");
                    break;
            }
            return ex;
        } else if (e instanceof ApiJsonFormateException) {
            ApiJsonFormateException apiJsonFormateException = (ApiJsonFormateException) e;
            String originData = apiJsonFormateException.originData;
            try {
                JSONObject jsonObject = new JSONObject(originData);
                String code = jsonObject.optString("code");
                String msg = jsonObject.optString("msg");
                ex = new ApiException(e, code, msg);
            } catch (JSONException e1) {
                e1.printStackTrace();
                ex = new ApiException(e, ERROR.PARSE_ERROR, "数据解析异常");
            }
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ApiException(e, ERROR.PARSE_ERROR, "数据解析异常");
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ApiException(e, ERROR.NETWORK_CONNECT_ERROR, "网络未连接，请检查网络");
            return ex;
        } else if (e instanceof SocketTimeoutException) {//超时
            ex = new ApiException(e, ERROR.NETWORK_TIMEOUT, "网络连接超时，请检查网络");
            return ex;
        } else if (e instanceof TokenException) {
            ex = new ApiException(e, ERROR.HTTP_ERROR, e.getMessage());
            return ex;
        } else {
            ex = new ApiException(e, ERROR.UNKNOWN, "未知错误");//未知错误
            return ex;
        }
    }
}

