package com.wujia.businesslib.base;

import com.wujia.lib_common.data.network.RequestParamsUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class BusinessInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        Map<String, String> tokenMap = getTokenMap();
        if (request.method().equals("GET")) {
            request = RequestParamsUtils.addGetParams(request, tokenMap);
        } else if (request.method().equals("POST")) {
            request = RequestParamsUtils.addPostParams(request, tokenMap);
        }
        return chain.proceed(request);
    }

    private Map<String, String> getTokenMap() {
        Map<String, String> map = new HashMap<>();
        //TODO test
        map.put("token", "");
        return map;
    }
}
