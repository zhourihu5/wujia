package com.wujia.businesslib.base;

import android.text.TextUtils;

import com.wujia.businesslib.data.RootResponse;
import com.wujia.businesslib.util.LoginUtil;
import com.wujia.lib_common.data.network.exception.TokenException;
import com.wujia.lib_common.utils.GsonUtil;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Author: created by shenbingkai on 2019/3/28 13 42
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class TokenInterceptor implements Interceptor {


    private boolean isInWhiteList(Request request) {
        String path = request.url().encodedPath();
        if (!path.startsWith("/v1/")) {//服务器token filter(JwtFilter)拦截路径
            return true;
        }
        return false;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Response originalResponse = null;

        if (isInWhiteList(request)) {//服务器token filter(JwtFilter)拦截路径
            originalResponse = chain.proceed(request);
            return originalResponse;
        } else {
            String token = DataManager.getToken();
            if (TextUtils.isEmpty(token)) {
//                originalResponse= chain.proceed(request);
                LoginUtil.toLoginActivity();
                throw new TokenException("请先登录");
//                return originalResponse;
            }
            request = request.newBuilder()
                    .addHeader("Authorization", token)
                    .build();
            originalResponse = chain.proceed(request);

        }


        //获取返回的json，response.body().string();只有效一次，对返回数据进行转换
        ResponseBody responseBody = originalResponse.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        Charset charset = Charset.forName("UTF-8");
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            charset = contentType.charset(Charset.forName("UTF-8"));
        }
        String bodyString = buffer.clone().readString(charset);//首次请求返回的结果
//        boolean isTestToken= BuildConfig.DEBUG&&url.contains("checkVersion");//  测试token失效
        if (isTokenExpired(bodyString)) {//根据和服务端的约定判断token过期
            DataManager.saveToken("");
            LoginUtil.toLoginActivity();
            throw new TokenException("请重新登录");
        }

        return originalResponse;
    }


    /**
     * 根据Response约定，判断Token是否失效
     *
     * @param response
     */
    private boolean isTokenExpired(String response) {
        try {
            RootResponse token = GsonUtil.GsonToBean(response, RootResponse.class);
            if (Integer.valueOf(token.code) < 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}