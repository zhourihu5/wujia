package com.wujia.lib_common.data.network.retrofit_url

import android.text.TextUtils


class InvalidUrlException(url: String) : RuntimeException("You've configured an invalid url : " + if (TextUtils.isEmpty(url)) "EMPTY_OR_NULL_URL" else url)
