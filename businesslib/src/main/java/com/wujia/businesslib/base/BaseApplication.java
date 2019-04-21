package com.wujia.businesslib.base;


import android.app.Application;

import com.jingxi.smartlife.pad.sdk.JXPadSdk;
import com.wujia.lib_common.utils.AppContext;
import com.wujia.lib_common.utils.SystemUtil;

public class BaseApplication extends Application {

    protected static BaseApplication instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        AppContext.init(instance);
        initSDKManager();

        SystemUtil.init();
    }

    private void initSDKManager() {
        JXPadSdk.init(instance);
        JXPadSdk.initDoorAccess();
        JXPadSdk.initPushManager();
    }
}
