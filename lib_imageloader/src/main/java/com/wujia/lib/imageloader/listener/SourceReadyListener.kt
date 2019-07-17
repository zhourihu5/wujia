package com.wujia.lib.imageloader.listener

/**
 * 通知准备就绪
 * modified by soulrelay
 */
interface SourceReadyListener {

    fun onResourceReady(width: Int, height: Int)
}
