package com.wujia.lib.imageloader.glideprogress

/**
 * @author "https://github.com/square/okhttp/blob/master/samples/guide/src/main/java/okhttp3/recipes/Progress.java"
 * @see [OkHttp sample](https://github.com/square/okhttp/blob/master/samples/guide/src/main/java/okhttp3/recipes/Progress.java)
 * 通知下载进度
 * modified by soulrelay
 */
internal interface ProgressListener {
    fun update(bytesRead: Long, contentLength: Long, done: Boolean)
}
