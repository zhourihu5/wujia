package com.wujia.businesslib.di.component;


import com.wujia.businesslib.base.BaseApplication;
import com.wujia.lib_common.data.network.HttpHelper;
import com.wujia.businesslib.di.module.AppModule;
import com.wujia.businesslib.di.module.DataModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, DataModule.class})
public interface AppComponent {
    BaseApplication getContext();  // 提供App的Context

    HttpHelper retrofitHelper();  //提供http的帮助类
}
