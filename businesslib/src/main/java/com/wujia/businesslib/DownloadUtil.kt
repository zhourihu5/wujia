package com.wujia.businesslib

import com.liulishuo.okdownload.DownloadTask
import com.liulishuo.okdownload.core.cause.EndCause
import com.liulishuo.okdownload.core.cause.ResumeFailedCause
import com.liulishuo.okdownload.core.listener.DownloadListener1
import com.liulishuo.okdownload.core.listener.assist.Listener1Assist
import com.wujia.businesslib.listener.DownloadListener
import com.wujia.lib_common.utils.AppContext
import com.wujia.lib_common.utils.FileUtil
import com.wujia.lib_common.utils.LogUtil

import java.io.File

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-30
 * description ：
 */
object DownloadUtil {

    val STATE_COMPLETE = 0
    val STATE_CANCELED = 10
    val STATE_OTHER = 20

    fun download(url: String, listener: DownloadListener): DownloadTask {

        val fileName = FileUtil.getNameForUrl(url)

        val builder = DownloadTask.Builder(url, File(FileUtil.getDowndloadApkPath(AppContext.get()), fileName))
        builder.setMinIntervalMillisCallbackProcess(1000)
        builder.setPriority(10)
        builder.setReadBufferSize(8192)
        builder.setFlushBufferSize(32768)
        builder.setConnectionCount(1)
        val task = builder.build()
        task.enqueue(object : DownloadListener1() {
            override fun taskStart(task: DownloadTask, model: Listener1Assist.Listener1Model) {
                listener.onTaskStart()
            }

            override fun retry(task: DownloadTask, cause: ResumeFailedCause) {}

            override fun connected(task: DownloadTask, blockCount: Int, currentOffset: Long, totalLength: Long) {}

            override fun progress(task: DownloadTask, currentOffset: Long, totalLength: Long) {
                if (totalLength != 0L) {
                    val percent = (currentOffset * 1.0f / totalLength * 100).toInt()
                    LogUtil.i("正在下载：$percent %")
                    listener.onTaskProgress(percent, currentOffset, totalLength)
                }
            }

            override fun taskEnd(task: DownloadTask, cause: EndCause, realCause: Exception?, model: Listener1Assist.Listener1Model) {

                LogUtil.i("taskEnd")
                if (EndCause.COMPLETED == cause) {
                    listener.onTaskComplete(STATE_COMPLETE, task.file!!.absolutePath)

                    //                    DataBaseUtil.insert(new AppPackageBean(subscriptions.get(position).servicePackage));
                    //TODO 安装需要系统签名
                    //                            AppUtil.install(task.getFile().getPath());
                } else if (EndCause.CANCELED == cause) {
                    listener.onTaskComplete(STATE_CANCELED, "")

                } else {
                    realCause?.printStackTrace()
                    LogUtil.i("cause:" + cause.name)
                    listener.onTaskComplete(STATE_OTHER, "")

                }
            }
        })
        return task
    }
}
