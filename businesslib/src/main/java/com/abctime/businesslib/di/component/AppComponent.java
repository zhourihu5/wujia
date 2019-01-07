package com.abctime.businesslib.di.component;


import com.abctime.businesslib.base.BaseApplication;
import com.abctime.lib_common.data.network.HttpHelper;
import com.abctime.businesslib.di.module.AppModule;
import com.abctime.businesslib.di.module.DataModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, DataModule.class})
public interface AppComponent {
    BaseApplication getContext();  // 提供App的Context

    HttpHelper retrofitHelper();  //提供http的帮助类
}
