package com.wujia.businesslib.base;


import android.app.Application;

import com.wujia.businesslib.BuildConfig;
import com.alibaba.android.arouter.launcher.ARouter;
import com.wujia.lib_common.utils.AppContext;

public class BaseApplication extends Application {

    protected static BaseApplication instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        AppContext.init(instance);
        initArouter();
    }

    private void initArouter() {
        ARouter.init(instance); // 尽可能早，推荐在Application中初始化
        if (BuildConfig.DEBUG) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
    }
}
