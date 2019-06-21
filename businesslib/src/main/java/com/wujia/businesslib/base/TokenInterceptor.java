package com.wujia.businesslib.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.wujia.businesslib.BuildConfig;
import com.wujia.businesslib.Constants;
import com.wujia.businesslib.data.RootResponse;
import com.wujia.lib_common.data.network.exception.ApiException;
import com.wujia.lib_common.data.network.exception.TokenException;
import com.wujia.lib_common.utils.AppContext;
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


    private boolean isInWhiteList(Request request){
        String path=request.url().encodedPath();
        if(!path.startsWith("/v1/")) {//服务器token filter(JwtFilter)拦截路径
            return true;
        }
        return false;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Response originalResponse =null;

        if(isInWhiteList(request)){//服务器token filter(JwtFilter)拦截路径
            originalResponse= chain.proceed(request);
            return originalResponse;
        }else {
            String token= DataManager.getToken();
            if(TextUtils.isEmpty(token)){
//                originalResponse= chain.proceed(request);
                toLoginActivity();
                throw new TokenException("请先登录");
//                return originalResponse;
            }
            request=request.newBuilder()
                    .addHeader("Authorization",token)
            .build();

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
        if(isTokenExpired(bodyString)){//根据和服务端的约定判断token过期
            DataManager.saveToken("");
            toLoginActivity();
            throw new TokenException("请重新登录");
        }
        originalResponse=chain.proceed(request);
        return originalResponse;
    }

    private void toLoginActivity() {
        try {
            Activity currentActivity = BaseApplication.getCurrentAcitivity();
            Context context = AppContext.get();
//            if (currentActivity != null) {
//                context = currentActivity;
//            }

            Intent intent = new Intent();
            intent.setClassName(context, "com.jingxi.smartlife.pad.mvp.login.LoginActivity");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);//applicationcontext 启动只能以newtask
            context.startActivity(intent);
            if (currentActivity != null) {
                currentActivity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据Response约定，判断Token是否失效
     *
     * @param response
     */
    private boolean isTokenExpired(String response) {
        try {
            RootResponse token = GsonUtil.GsonToBean(response, RootResponse.class);

            if (Constants.HTTP_TOKEN_FAILD.equals(token.code) || Constants.HTTP_TOKEN_NO.equals(token.code)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}