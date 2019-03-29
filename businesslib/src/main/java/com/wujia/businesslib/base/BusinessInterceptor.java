package com.wujia.businesslib.base;

import android.text.TextUtils;

import com.wujia.businesslib.Constants;
import com.wujia.lib_common.data.network.RequestParamsUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class BusinessInterceptor implements Interceptor {
    private static final String[] IGONE_REQUEST = {"/token/checkToken"};
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        //接口过滤
        for (String igone : IGONE_REQUEST) {
            if (request.url().toString().contains(igone)) {
                return chain.proceed(request);
            }
        }
        Map<String, String> tokenMap = getTokenMap();
        if (request.method().equals("GET")) {
            request = RequestParamsUtils.addGetParams(request, tokenMap);
        } else if (request.method().equals("POST")) {
            request = RequestParamsUtils.addPostParams(request, tokenMap);
        }
        return chain.proceed(request);
    }

    private Map<String, String> getTokenMap() {
        String token = DataManager.getToken();
        Map<String, String> map = new HashMap<>();
        if (!TextUtils.isEmpty(token)) {
            map.put(Constants.COMMON_REQUEST_TOKEN, token);
        }
        return map;
    }
}
