package com.wujia.businesslib.listener

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-30
 * description ：
 */
interface DownloadListener {

    fun onTaskStart()

    fun onTaskProgress(percent: Int, currentOffset: Long, totalLength: Long)

    fun onTaskComplete(state: Int, path: String)  //0成功
}
