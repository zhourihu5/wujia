package com.wujia.lib_common.data.network

import android.os.Handler
import android.os.Looper
import okhttp3.ResponseBody
import java.io.*

class DownLoadManager(private val callBack: DownloadCallBack?) {

    private var handler: Handler? = null


    fun writeResponseBodyToDisk(filePath: String, body: ResponseBody): Boolean {


        try {
            val futureStudioIconFile = File(filePath)

            if (futureStudioIconFile.exists()) {
                futureStudioIconFile.delete()
            }

            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null

            try {
                val fileReader = ByteArray(4096)

                val fileSize = body.contentLength()
                var fileSizeDownloaded: Long = 0
                inputStream = body.byteStream()
                outputStream = FileOutputStream(futureStudioIconFile)

                while (true) {
                    val read = inputStream!!.read(fileReader)

                    if (read == -1) {
                        break
                    }

                    outputStream.write(fileReader, 0, read)

                    fileSizeDownloaded += read.toLong()

                    if (callBack != null) {
                        handler = Handler(Looper.getMainLooper())
                        val finalFileSizeDownloaded = fileSizeDownloaded
                        handler!!.post { callBack.onProgress((100 * finalFileSizeDownloaded / fileSize).toInt().toLong()) }

                    }
                }

                outputStream.flush()
                if (callBack != null) {
                    handler = Handler(Looper.getMainLooper())
                    handler!!.post { callBack.onSucess(filePath, fileSize) }
                }

                return true
            } catch (e: IOException) {
                callBack?.onError(e)
                return false
            } finally {
                inputStream?.close()

                outputStream?.close()
            }
        } catch (e: IOException) {
            callBack?.onError(e)
            return false
        }

    }

    companion object {

        private val TAG = "DownLoadManager"

        private var sInstance: DownLoadManager? = null

        /**
         * DownLoadManager getInstance
         */
        @Synchronized
        fun getInstance(callBack: DownloadCallBack?): DownLoadManager {
            if (sInstance == null) {
                sInstance = DownLoadManager(callBack)
            }
            return sInstance!!
        }
    }
}
