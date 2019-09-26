package com.wujia.lib_common.data.network

abstract class DownloadCallBack {
    fun onStart() {}

    fun onCompleted() {}

    abstract fun onError(e: Throwable)

    fun onProgress(fileSizeDownloaded: Long) {}

    abstract fun onSucess(path: String, fileSize: Long)
}
