package com.abctime.lib_common.data.network.retrofit_url;

import okhttp3.HttpUrl;

/**
 */
class Utils {

    private Utils() {
        throw new IllegalStateException("do not instantiation me");
    }

    /**
     * 运行时需要暂时注掉，否则报错
     * @param url
     * @return
     */
    static HttpUrl checkUrl(String url) {
       HttpUrl parseUrl = HttpUrl.parse(url);
        if (null == parseUrl) {
            throw new InvalidUrlException(url);
        } else {
            return parseUrl;
        }
    }
}
