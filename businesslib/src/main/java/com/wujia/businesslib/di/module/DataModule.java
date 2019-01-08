package com.wujia.businesslib.di.module;


import com.wujia.businesslib.base.BaseApplication;
import com.wujia.lib_common.data.network.NetConfig;
import com.wujia.lib_common.data.network.HttpHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    NetConfig netConfig;

    public DataModule(NetConfig netConfig) {
        this.netConfig = netConfig;
    }

    @Provides
    @Singleton
    HttpHelper provideRetrofitHelper(BaseApplication application) {
        HttpHelper.Builder builder = new HttpHelper.Builder(netConfig);
        HttpHelper httpHelper = builder.build();
        return httpHelper;
    }

}
