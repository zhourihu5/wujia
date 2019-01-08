package com.wujia.lib_common.data.network;


import com.wujia.lib_common.utils.Encrypt;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * Created by xmren on 2018/5/14.
 */

public class RequestParamsUtils {

    public static String getSignValue(String paramStr) {
        String signVal = "";
        if (null != paramStr && !"".equals(paramStr)) {
            try {
                return URLEncoder.encode(Encrypt.SHA256(paramStr + NetConfig.signKey), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return signVal;
    }

    public static int getSecondTimestamp(Date date) {
        if (null == date) {
            return 0;
        }
        String timestamp = String.valueOf(date.getTime() / 1000);
        return Integer.valueOf(timestamp);
    }

    public static Request addGetParams(Request request, Map<String, String> params) {
        HttpUrl.Builder requestBuilder = request.url()
                .newBuilder();
        if (params != null && params.size() > 0) {
            for (Map.Entry entry : params.entrySet()) {
                requestBuilder.addQueryParameter((String) entry.getKey(), (String) entry.getValue());
            }
        }
        return request.newBuilder().url(requestBuilder.build()).build();
    }

    public static Request addPostParams(Request request, Map<String, String> params) {
        Request.Builder requestBuilder = request.newBuilder();
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        if (request.body() instanceof FormBody) {
            FormBody formBody = (FormBody) request.body();
            for (int i = 0; i < formBody.size(); i++) {
                bodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
            }
        }
        if (params != null && params.size() > 0) {
            for (Map.Entry entry : params.entrySet()) {
                bodyBuilder.add((String) entry.getKey(), (String) entry.getValue());
            }
        }
        return requestBuilder.post(bodyBuilder.build()).build();
    }

}
