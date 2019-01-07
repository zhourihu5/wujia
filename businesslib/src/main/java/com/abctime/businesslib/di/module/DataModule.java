package com.abctime.businesslib.di.module;


import com.abctime.businesslib.base.BaseApplication;
import com.abctime.lib_common.data.network.NetConfig;
import com.abctime.lib_common.data.network.HttpHelper;

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
