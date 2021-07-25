package com.wujia.lib_common.data.network.retrofit_url

import android.text.TextUtils
import com.wujia.lib_common.data.network.retrofit_url.parser.DefaultUrlParser
import com.wujia.lib_common.data.network.retrofit_url.parser.UrlParser
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * RetrofitUrlManager 以简洁的 Api ,让 Retrofit 不仅支持多 BaseUrl
 * 还可以在 App 运行时动态切换任意 BaseUrl,在多 BaseUrl 场景下也不会影响到其他不需要切换的 BaseUrl
 *
 *
 */

class RetrofitUrlManager private constructor() {

    /**
     * 管理器是否在运行
     *
     * @return
     */
    /**
     * 控制管理器是否运行,在每个域名地址都已经确定,不需要再动态更改时可设置为 false
     *
     * @param run
     */
    private var isRun = true //默认开始运行,可以随时停止运行,比如你在 App 启动后已经不需要在动态切换 baseurl 了
    private val mDomainNameHub = ConcurrentHashMap<String, HttpUrl>()
    private val mInterceptor: Interceptor
    private val mListeners = ArrayList<onUrlChangeListener>()
    private var mUrlParser: UrlParser? = null


    init {
        if (!DEPENDENCY_OKHTTP) { //使用本管理器必须依赖 Okhttp
            throw IllegalStateException("Must be dependency Okhttp")
        }
        setUrlParser(DefaultUrlParser())
        this.mInterceptor = Interceptor { chain -> if (!isRun) chain.proceed(chain.request()) else chain.proceed(processRequest(chain.request())) }
    }

    private object RetrofitUrlManagerHolder {
        val INSTANCE = RetrofitUrlManager()
    }

    /**
     * 将 [OkHttpClient.Builder] 传入,配置一些本管理器需要的参数
     *
     * @param builder
     * @return
     */
    fun with(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        return builder
                .addInterceptor(mInterceptor)
    }

    /**
     * 对 [Request] 进行一些必要的加工
     *
     * @param request
     * @return
     */
    fun processRequest(request: Request): Request {

        val newBuilder = request.newBuilder()

        val domainName = obtainDomainNameFromHeaders(request)

        val httpUrl: HttpUrl?

        // 如果有 header，获取 header 中配置的url，否则检查全局的 BaseUrl，未找到则为null
        if (!TextUtils.isEmpty(domainName)) {
            httpUrl = fetchDomain(domainName)
            newBuilder.removeHeader(DOMAIN_NAME)
        } else {
            httpUrl = fetchDomain(GLOBAL_DOMAIN_NAME)
        }

        if (null != httpUrl) {
            val newUrl = mUrlParser!!.parseUrl(httpUrl, request.url())

            val listeners = listenersToArray()
            if (listeners != null) {
                for (i in listeners.indices) {
                    (listeners[i] as onUrlChangeListener).onUrlChange(newUrl, request.url()) // 通知监听器此 Url 的 BaseUrl 已被改变
                }
            }

            return newBuilder
                    .url(newUrl)
                    .build()
        }

        return newBuilder.build()

    }

    /**
     * 存放 Domain 的映射关系
     *
     * @param domainName
     * @param domainUrl
     */
    fun putDomain(domainName: String, domainUrl: String) {
        mDomainNameHub[domainName] = Utils.checkUrl(domainUrl)
    }

    /**
     * 取出对应 DomainName 的 Url
     *
     * @param domainName
     * @return
     */
    private fun fetchDomain(domainName: String?): HttpUrl? {
        return mDomainNameHub[domainName]
    }

    fun clearAllDomain() {
        mDomainNameHub.clear()
    }

    fun domainSize(): Int {
        return mDomainNameHub.size
    }

    /**
     * 可自行实现 [UrlParser] 动态切换 Url 解析策略
     *
     * @param parser
     */
    fun setUrlParser(parser: UrlParser) {
        this.mUrlParser = parser
    }

    /**
     * 注册当 Url 的 BaseUrl 被改变时会被回调的监听器
     *
     * @param listener
     */
    fun registerUrlChangeListener(listener: onUrlChangeListener) {
        synchronized(mListeners) {
            mListeners.add(listener)
        }
    }

    private fun listenersToArray(): Array<Any>? {
        var listeners: Array<Any>? = null
        synchronized(mListeners) {
            if (mListeners.size > 0) {
                listeners = mListeners.toTypedArray()
            }
        }
        return listeners
    }


    /**
     * 从 [Request.header] 中取出 DomainName
     *
     * @param request
     * @return
     */
    private fun obtainDomainNameFromHeaders(request: Request): String? {
        val headers = request.headers(DOMAIN_NAME)
        if (headers == null || headers.size == 0)
            return null
        if (headers.size > 1)
            throw IllegalArgumentException("Only one Domain-Name in the headers")
        return request.header(DOMAIN_NAME)
    }

    companion object {
        private val DEPENDENCY_OKHTTP: Boolean
        private const val DOMAIN_NAME = "Domain-Name"
        private const val GLOBAL_DOMAIN_NAME = "me.jessyan.retrofiturlmanager.globalDomainName"

        init {
            var hasDependency: Boolean
            hasDependency = try {
                Class.forName("okhttp3.OkHttpClient")
                true
            } catch (e: ClassNotFoundException) {
                false
            }

            DEPENDENCY_OKHTTP = hasDependency
        }

        val instance: RetrofitUrlManager
            get() = RetrofitUrlManagerHolder.INSTANCE
    }

}
