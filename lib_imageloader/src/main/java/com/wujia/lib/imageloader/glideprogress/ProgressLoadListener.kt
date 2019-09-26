package com.wujia.lib.imageloader.glideprogress

/**
 * 通知图片加载进度
 * modified by soulrelay
 */
interface ProgressLoadListener {

    fun update(bytesRead: Int, contentLength: Int)

    fun onException()

    fun onResourceReady()
}
