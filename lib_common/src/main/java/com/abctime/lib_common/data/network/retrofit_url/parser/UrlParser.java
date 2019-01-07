package com.abctime.lib_common.data.network.retrofit_url.parser;


import okhttp3.HttpUrl;

/**
 * Url解析器
 * <p>
 */

public interface UrlParser {

    HttpUrl parseUrl(HttpUrl domainUrl, HttpUrl url);
}
