package com.wujia.businesslib.base;

import com.wujia.businesslib.Constants;
import com.wujia.lib_common.data.network.HttpHelper;
import com.wujia.lib_common.data.network.NetConfig;

import okhttp3.Interceptor;

/**
 * author ：shenbingkai
 * date ：2019-01-12
 * description ：
 */
public class BaseModel {

    protected HttpHelper mHttpHelper;

    public BaseModel() {
        createHttp();
    }

    protected void createHttp() {
        mHttpHelper = new HttpHelper.Builder(NetConfigWrapper.create())
                .build();
    }
}
