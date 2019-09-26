package com.wujia.lib.imageloader.glideprogress

/**
 * 通知UI进度
 * modified by soulrelay
 */
interface ProgressUIListener {
    fun update(bytesRead: Int, contentLength: Int)
}
