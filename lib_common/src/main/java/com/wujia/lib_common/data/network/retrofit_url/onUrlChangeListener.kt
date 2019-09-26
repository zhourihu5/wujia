package com.wujia.lib_common.data.network.retrofit_url

import okhttp3.HttpUrl


interface onUrlChangeListener {
    /**
     * 当 Url 的 BaseUrl 被改变时回调
     * 调用时间是在接口请求服务器之前
     *
     * @param newUrl
     * @param oldUrl
     */
    fun onUrlChange(newUrl: HttpUrl, oldUrl: HttpUrl)
}
