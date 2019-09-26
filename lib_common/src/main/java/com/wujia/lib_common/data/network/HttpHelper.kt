package com.wujia.lib_common.data.network

import com.wujia.lib_common.data.network.https.SslContextFactory
import com.wujia.lib_common.data.network.retrofit_url.RetrofitUrlManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.CustomConverterFactory
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit


/**
 * Created by xmren on 2018/3/8.
 */

class HttpHelper internal constructor(builder: Builder) {
    private val mBaseUrl: String
    private val mRetrofit: Retrofit
    private val mServiceMap: ConcurrentHashMap<String, Any?>?

    val okHttpClient: OkHttpClient

    init {
        mServiceMap = ConcurrentHashMap()
        this.mBaseUrl = builder.baseUrl
        okHttpClient = provideClient(builder)
        mRetrofit = Retrofit.Builder().client(okHttpClient).addConverterFactory(CustomConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(mBaseUrl).build()
    }

    constructor(baseUrl: String) : this(Builder(baseUrl)) {}


    fun <T> create(service: Class<T>?): T? {
        if (service == null) {
            throw RuntimeException("Api service is null!")
        }
        var apiService: T? = null
        if (mServiceMap != null) {
            apiService = mServiceMap[service.name] as T?
            if (apiService == null) {
                apiService = mRetrofit.create(service)
                mServiceMap[service.name] = apiService
            }
        }
        return apiService
    }

    fun createBaseApi(): BaseApiService? {
        var baseApiService: BaseApiService? = null
        if (mServiceMap != null) {
            val name = BaseApiService::class.java.name
            val key = name + mBaseUrl//通过baseservice类名+baseurl的方式区分不同模块的apisevice
            if (mServiceMap.containsKey(key)) {
                baseApiService = mServiceMap[key] as BaseApiService?
            } else {
                baseApiService = mRetrofit.create(BaseApiService::class.java)
                mServiceMap[key] = baseApiService
            }
        }
        return baseApiService

    }

    private fun provideClient(retrofitBuilder: Builder): OkHttpClient {
        if (retrofitBuilder.okHttpClient != null) {
            return retrofitBuilder.okHttpClient!!
        } else {
            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(SslContextFactory.createSSLSocketFactory()!!)


            val interceptors = retrofitBuilder.interceptors()
            if (interceptors != null && interceptors.size > 0) {
                for (interceptor in interceptors) {
                    builder.addInterceptor(interceptor)
                }
            }
            val networkInterceptors = retrofitBuilder.networkInterceptors()
            if (networkInterceptors != null && networkInterceptors.size > 0) {
                for (interceptor in networkInterceptors) {
                    builder.addInterceptor(interceptor)
                }
            }
            builder.addInterceptor(ParamsInterceptor())

            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = if (NetConfig.debug) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            builder.addInterceptor(loggingInterceptor)

            //设置超时
            builder.connectTimeout(retrofitBuilder.connectTimeout.toLong(), TimeUnit.SECONDS)
            builder.readTimeout(retrofitBuilder.readTimeout.toLong(), TimeUnit.SECONDS)
            builder.writeTimeout(retrofitBuilder.writeTimeout.toLong(), TimeUnit.SECONDS)

            val mDomainNameHub = retrofitBuilder.mDomainNameHub
            if (mDomainNameHub != null && mDomainNameHub.size > 0) {
                val iterator = mDomainNameHub.entries.iterator()
                while (iterator.hasNext()) {
                    val entry = iterator.next()
                    val key = entry.key
                    val value = mDomainNameHub[key]
                    RetrofitUrlManager.instance.putDomain(key, value!!)
                }
            }
            return RetrofitUrlManager.instance.with(builder).build()
        }
    }

    //    public <T extends BaseResponse> void get(String url, Map parameters, HttpCallBack<T> callBack) {
    //        BaseApiService<T> baseApiService = createBaseApi(callBack.getClass().getDeclaredAnnotations()[0].annotationType());
    //        if (baseApiService != null) {
    //            baseApiService.executeGet(url, parameters).compose(RxUtil.rxSchedulerHelper()).subscribe(new SimpleSubscriber(callBack));
    //        }
    //    }
    //
    //    public <T extends BaseResponse> void post(String url, Map parameters, HttpCallBack<T> callBack) {
    //        BaseApiService<T> baseApiService = createBaseApi();
    //        if (baseApiService != null) {
    //            baseApiService.executePost(url, parameters).compose(RxUtil.rxSchedulerHelper()).subscribe(new SimpleSubscriber(callBack));
    //        }
    //    }
    //
    //    public <T extends BaseResponse> void json(String url, RequestBody jsonBody, HttpCallBack<T> callBack) {
    //        BaseApiService<T> baseApiService = createBaseApi();
    //        if (baseApiService != null) {
    //            baseApiService.json(url, jsonBody).compose(RxUtil.rxSchedulerHelper()).subscribe(new SimpleSubscriber(callBack));
    //        }
    //    }
    //
    //    public <T extends BaseResponse> void upload(String url, RequestBody requestBody, HttpCallBack<T> callBack) {
    //        BaseApiService<T> baseApiService = createBaseApi();
    //        if (baseApiService != null) {
    //            baseApiService.upLoadFile(url, requestBody).compose(RxUtil.rxSchedulerHelper()).subscribe(new SimpleSubscriber(callBack));
    //        }
    //    }

    //    public void download(String url, String saveFilePath, DownloadCallBack callBack) {
    //        if (TextUtils.isEmpty(url) || TextUtils.isEmpty(saveFilePath)) {
    //            throw new RuntimeException("url or saveFilePath can not to be null");
    //        }
    //        BaseApiService baseApiService = createBaseApi();
    //        if (baseApiService != null) {
    //            saveFilePath = getFileSavePath(url, saveFilePath);
    //            baseApiService.downloadFile(url).compose(RxUtil.rxSchedulerHelper()).subscribe(new DownSubscriber<Object>(saveFilePath, callBack));
    //        }
    //    }

    private fun getFileSavePath(url: String, saveFilePath: String): String {
        var saveFilePath = saveFilePath
        val lastIndex = url.lastIndexOf('/')
        var name = url
        if (lastIndex != -1) {
            name = url.substring(lastIndex)
        }
        if (saveFilePath.endsWith("/")) {
            saveFilePath = saveFilePath + name
        } else {
            saveFilePath = "$saveFilePath/$name"
        }
        return saveFilePath
    }


    class Builder {
        internal var netConfig: NetConfig? = null
        internal var baseUrl: String

        internal var okHttpClient: OkHttpClient? = null
        internal val interceptors: MutableList<Interceptor> = ArrayList()
        internal val networkInterceptors: MutableList<Interceptor> = ArrayList()
        internal var connectTimeout: Int = 0
        internal var readTimeout: Int = 0
        internal var writeTimeout: Int = 0

        internal var mDomainNameHub: HashMap<String, String>? = null


        constructor(url: String) {
            baseUrl = url
            connectTimeout = 60
            readTimeout = 30
            writeTimeout = 60
        }

        constructor(netConfig: NetConfig) {
            baseUrl = netConfig.baseURL
            connectTimeout = netConfig.connectTimeoutMills.toInt()
            readTimeout = netConfig.readTimeoutMills.toInt()
            val interceptorsInner = netConfig.mInterceptors
            if (interceptorsInner != null && interceptorsInner.size > 0) {
                Collections.addAll(interceptors, *interceptorsInner)
            }
        }

        fun okHttpClient(okHttpClient: OkHttpClient): Builder {
            this.okHttpClient = okHttpClient
            return this
        }


        fun baseUrl(url: String): Builder {
            baseUrl = url
            return this
        }

        fun connectTimeout(timeout: Int): Builder {
            connectTimeout = timeout
            return this
        }

        fun readTimeout(timeout: Int): Builder {
            readTimeout = timeout
            return this
        }

        fun writeTimeout(timeout: Int): Builder {
            writeTimeout = timeout
            return this
        }

        fun addInterceptor(interceptor: Interceptor): Builder {
            interceptors.add(interceptor)
            return this
        }

        fun interceptors(): List<Interceptor>? {
            return interceptors
        }

        fun networkInterceptors(): List<Interceptor>? {
            return networkInterceptors
        }

        fun addNetworkInterceptor(interceptor: Interceptor): Builder {
            networkInterceptors.add(interceptor)
            return this
        }

        fun build(): HttpHelper {
            return HttpHelper(this)
        }

    }
}
