package com.wujia.lib.imageloader.glideprogress

import com.bumptech.glide.Priority
import com.bumptech.glide.load.data.DataFetcher
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.io.InputStream

/**
 * @see com.bumptech.glide.load.data.HttpUrlFetcher
 *
 * @see [OkHttp sample](https://github.com/square/okhttp/blob/master/samples/guide/src/main/java/okhttp3/recipes/Progress.java)
 * modified by soulrelay
 */
class ProgressDataFetcher(private val url: String, private val proListener: ProgressUIListener?) : DataFetcher<InputStream> {
    private var progressCall: Call? = null
    private var stream: InputStream? = null
    @Volatile
    private var isCancelled: Boolean = false

    @Throws(Exception::class)
    override fun loadData(priority: Priority): InputStream? {
        val request = Request.Builder()
                .url(url)
                .build()
        val progressListener = object : ProgressListener {
            override fun update(bytesRead: Long, contentLength: Long, done: Boolean) {
                proListener?.update(bytesRead.toInt(), contentLength.toInt())
            }
        }
        val client = OkHttpClient.Builder()
                .addNetworkInterceptor { chain ->
                    val originalResponse = chain.proceed(chain.request())
                    originalResponse.newBuilder()
                            .body(ProgressResponseBody(originalResponse.body()!!, progressListener))
                            .build()
                }
                .build()
        try {
            progressCall = client.newCall(request)
            val response = progressCall!!.execute()
            if (isCancelled) {
                return null
            }
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            stream = response.body()!!.byteStream()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return stream
    }

    override fun cleanup() {
        if (stream != null) {
            try {
                stream!!.close()
            } catch (e: IOException) {
                // Ignore
            }

        }
        if (progressCall != null) {
            progressCall!!.cancel()
        }
    }

    override fun getId(): String {
        return url
    }

    override fun cancel() {
        // TODO: we should consider disconnecting the url connection here, but we can't do so directly because cancel is
        // often called on the main thread.
        isCancelled = true
    }

    companion object {

        private val TAG = "ProgressDataFetcher"
    }
}
