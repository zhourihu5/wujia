package com.wujia.lib_common.data.network.retrofit_url

import okhttp3.HttpUrl

/**
 *
 */
internal class Utils private constructor() {

    init {
        throw IllegalStateException("do not instantiation me")
    }

    companion object {

        /**
         * 运行时需要暂时注掉，否则报错
         *
         * @param url
         * @return
         */
        fun checkUrl(url: String): HttpUrl {
            val parseUrl = HttpUrl.parse(url)
            return parseUrl ?: throw InvalidUrlException(url)
        }
    }


}
