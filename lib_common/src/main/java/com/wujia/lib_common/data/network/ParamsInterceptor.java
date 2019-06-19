package com.wujia.lib_common.data.network;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ParamsInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();


        Map<String, String> params = buildCommonParamsMap();
        if (request.method().equals("GET")) {
            request = RequestParamsUtils.addGetParams(request, params);
//            request = addGetSignParam(request);
        } else if (request.method().equals("POST")) {
            request = RequestParamsUtils.addPostParams(request, params);
//            request = addPostSignParam(request);
        }

        return chain.proceed(request);
    }


    //get请求 添加公共参数 签名
    private Request addGetSignParam(Request request) {
        HttpUrl httpUrl = request.url().newBuilder().build();

        //添加签名
        Set<String> nameSet = httpUrl.queryParameterNames();
        ArrayList<String> nameList = new ArrayList<>();
        nameList.addAll(nameSet);
        Collections.sort(nameList);

        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < nameList.size(); i++) {
            buffer.append(nameList.get(i)).append(httpUrl.queryParameterValues(nameList.get(i)).get(0));
        }
//        httpUrl = httpUrl.newBuilder()
//                .addQueryParameter(Constants.COMMON_REQUEST_SIGN, RequestParamsUtils.getSignValue(buffer.toString()))
//                .build();
        request = request.newBuilder().url(httpUrl).build();
        return request;
    }

    //post 添加签名和公共参数
    private Request addPostSignParam(Request request) throws UnsupportedEncodingException {

        Request.Builder requestBuilder = request.newBuilder();
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        if (request.body() instanceof FormBody) {
            FormBody formBody = (FormBody) request.body();
            for (int i = 0; i < formBody.size(); i++) {
                bodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
            }
        }
        FormBody formBody = bodyBuilder.build();
        Map<String, String> bodyMap = new HashMap<>();
        List<String> nameList = new ArrayList<>();

        for (int i = 0; i < formBody.size(); i++) {
            nameList.add(formBody.name(i));
            bodyMap.put(formBody.name(i), formBody.value(i));
        }
        Collections.sort(nameList);

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < nameList.size(); i++) {
            builder.append(nameList.get(i))
                    .append(bodyMap.get(nameList.get(i)));
        }

//        formBody = bodyBuilder.
//                add(Constants.COMMON_REQUEST_SIGN, RequestParamsUtils.getSignValue(builder.toString()))
//                .build();
        request = requestBuilder.post(formBody).build();
        return request;
    }

    private Map<String, String> buildCommonParamsMap() {
        Map<String, String> queryParamsMap = new HashMap<>();

//        queryParamsMap.put(Constants.COMMON_REQUEST_APP_ID, Constants.APP_ID);
//        queryParamsMap.put(Constants.COMMON_REQUEST_CLIENT_VERSION, AppUtil.getVersionCode(AppContext.get()) + "");
//        queryParamsMap.put(Constants.COMMON_REQUEST_TIMESTAMP, RequestParamsUtils.getSecondTimestamp(new Date()) + "");
//        queryParamsMap.put(Constants.COMMON_REQUEST_UUID, AppUtil.getDeviceInfo(AppContext.get()));
        return queryParamsMap;
    }


}
