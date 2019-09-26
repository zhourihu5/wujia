package com.wujia.lib_common.data.network.retrofit_url.parser

import okhttp3.HttpUrl

/**
 *
 */
class DefaultUrlParser : UrlParser {
    override fun parseUrl(domainUrl: HttpUrl, url: HttpUrl): HttpUrl {

        // 如果 HttpUrl.parse(url); 解析为 null 说明,url 格式不正确,正确的格式为 "https://github.com:443"
        // http 默认端口 80,https 默认端口 443 ,如果端口号是默认端口号就可以将 ":443" 去掉
        // 只支持 http 和 https

        return if (null == domainUrl) url else url.newBuilder()
                .scheme(domainUrl.scheme())
                .host(domainUrl.host())
                .port(domainUrl.port())
                .build()

    }
}
