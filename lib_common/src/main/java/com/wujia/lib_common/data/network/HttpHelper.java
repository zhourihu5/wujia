
package com.wujia.lib_common.data.network;

import android.support.annotation.NonNull;

import com.wujia.lib_common.data.network.https.SslContextFactory;
import com.wujia.lib_common.data.network.retrofit_url.RetrofitUrlManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.CustomConverterFactory;


/**
 * Created by xmren on 2018/3/8.
 */

public class HttpHelper {
    private String mBaseUrl;
    private Retrofit mRetrofit;
    private ConcurrentHashMap mServiceMap;

    private OkHttpClient mOkHttpClient;

    HttpHelper(Builder builder) {
        mServiceMap = new ConcurrentHashMap();
        this.mBaseUrl = builder.baseUrl;
        mOkHttpClient = provideClient(builder);
        mRetrofit = new Retrofit.Builder().client(mOkHttpClient).addConverterFactory(CustomConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(mBaseUrl).build();
    }

    public HttpHelper(String baseUrl) {
        this(new Builder(baseUrl));
    }


    public <T> T create(final Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        T apiService = null;
        if (mServiceMap != null) {
            apiService = (T) mServiceMap.get(service.getName());
            if (apiService==null) {
                apiService = mRetrofit.create(service);
                mServiceMap.put(service.getName(), apiService);
            }
        }
        return apiService;
    }

    public BaseApiService createBaseApi() {
        BaseApiService baseApiService = null;
        if (mServiceMap != null) {
            String name = BaseApiService.class.getName();
            String key = name + mBaseUrl;//通过baseservice类名+baseurl的方式区分不同模块的apisevice
            if (mServiceMap.containsKey(key)) {
                baseApiService = (BaseApiService) mServiceMap.get(key);
            } else {
                baseApiService = mRetrofit.create(BaseApiService.class);
                mServiceMap.put(key, baseApiService);
            }
        }
        return baseApiService;

    }

    private OkHttpClient provideClient(Builder retrofitBuilder) {
        if (retrofitBuilder.okHttpClient != null) {
            return retrofitBuilder.okHttpClient;
        } else {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(SslContextFactory.createSSLSocketFactory());



            List<Interceptor> interceptors = retrofitBuilder.interceptors();
            if (interceptors != null && interceptors.size() > 0) {
                for (Interceptor interceptor : interceptors) {
                    builder.addInterceptor(interceptor);
                }
            }
            List<Interceptor> networkInterceptors = retrofitBuilder.networkInterceptors();
            if (networkInterceptors != null && networkInterceptors.size() > 0) {
                for (Interceptor interceptor : networkInterceptors) {
                    builder.addInterceptor(interceptor);
                }
            }
            builder.addInterceptor(new ParamsInterceptor());

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(NetConfig.debug?HttpLoggingInterceptor.Level.BODY: HttpLoggingInterceptor.Level.NONE);
            builder.addInterceptor(loggingInterceptor);

            //设置超时
            builder.connectTimeout(retrofitBuilder.connectTimeout, TimeUnit.SECONDS);
            builder.readTimeout(retrofitBuilder.readTimeout, TimeUnit.SECONDS);
            builder.writeTimeout(retrofitBuilder.writeTimeout, TimeUnit.SECONDS);

            HashMap<String, String> mDomainNameHub = retrofitBuilder.mDomainNameHub;
            if (mDomainNameHub != null && mDomainNameHub.size() > 0) {
                Iterator<Map.Entry<String, String>> iterator = mDomainNameHub.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, String> entry = iterator.next();
                    String key = entry.getKey();
                    String value = mDomainNameHub.get(key);
                    RetrofitUrlManager.getInstance().putDomain(key, value);
                }
            }
            return RetrofitUrlManager.getInstance().with(builder).build();
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

    @NonNull
    private String getFileSavePath(String url, String saveFilePath) {
        int lastIndex = url.lastIndexOf('/');
        String name = url;
        if (lastIndex != -1) {
            name = url.substring(lastIndex);
        }
        if (saveFilePath.endsWith("/")) {
            saveFilePath = saveFilePath + name;
        } else {
            saveFilePath = saveFilePath + "/" + name;
        }
        return saveFilePath;
    }


    public static final class Builder {
        NetConfig netConfig;
        String baseUrl;

        OkHttpClient okHttpClient;
        final List<Interceptor> interceptors = new ArrayList<>();
        final List<Interceptor> networkInterceptors = new ArrayList<>();
        int connectTimeout;
        int readTimeout;
        int writeTimeout;

        HashMap<String, String> mDomainNameHub;


        public Builder(String url) {
            baseUrl = url;
            connectTimeout = 60;
            readTimeout = 30;
            writeTimeout = 60;
        }

        public Builder(NetConfig netConfig) {
            baseUrl = netConfig.baseURL;
            connectTimeout = (int) netConfig.connectTimeoutMills;
            readTimeout = (int) netConfig.readTimeoutMills;
            Interceptor[] interceptorsInner = netConfig.mInterceptors;
            if (interceptorsInner != null && interceptorsInner.length > 0) {
                Collections.addAll(interceptors, interceptorsInner);
            }
        }

        public Builder okHttpClient(OkHttpClient okHttpClient) {
            this.okHttpClient = okHttpClient;
            return this;
        }

        public Builder domainNameHub(HashMap domianNameHub) {
            mDomainNameHub = domianNameHub;
            return this;
        }

        public Builder baseUrl(String url) {
            baseUrl = url;
            return this;
        }

        public Builder connectTimeout(int timeout) {
            connectTimeout = timeout;
            return this;
        }

        public Builder readTimeout(int timeout) {
            readTimeout = timeout;
            return this;
        }

        public Builder writeTimeout(int timeout) {
            writeTimeout = timeout;
            return this;
        }

        public Builder addInterceptor(Interceptor interceptor) {
            interceptors.add(interceptor);
            return this;
        }

        public List<Interceptor> interceptors() {
            return interceptors;
        }

        public List<Interceptor> networkInterceptors() {
            return networkInterceptors;
        }

        public Builder addNetworkInterceptor(Interceptor interceptor) {
            networkInterceptors.add(interceptor);
            return this;
        }

        public HttpHelper build() {
            return new HttpHelper(this);
        }

    }

    public OkHttpClient getOkHttpClient() {
        return mOkHttpClient;
    }
}
