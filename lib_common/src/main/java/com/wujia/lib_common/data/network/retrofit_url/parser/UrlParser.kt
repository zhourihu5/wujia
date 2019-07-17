package com.wujia.lib_common.data.network.retrofit_url.parser


import okhttp3.HttpUrl

/**
 * Url解析器
 *
 *
 */

interface UrlParser {

    fun parseUrl(domainUrl: HttpUrl, url: HttpUrl): HttpUrl
}
