package com.wujia.businesslib.base;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import com.wujia.lib_common.base.ApplicationLike;
import com.wujia.lib_common.utils.LogUtil;

/**
 * 该Application只能放在子moudle中使用,用于moudle隔离
 */

public class BaseChildApplication extends BaseApplication implements ApplicationLike {

    private static final String TAG = BaseChildApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        onCreateAsLibrary(this);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        onLowMemoryAsLibrary(this);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        onTrimMemoryAsLibrary(this, level);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        onTerminate(this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        onConfigurationChanged(this, newConfig);
    }

    @Override
    public void onTerminate(Application application) {
        LogUtil.i(this.getClass().getName() + "onTerminate");
    }

    @Override
    public void onCreateAsLibrary(Application application) {
        LogUtil.i(this.getClass().getName() + "onCreateAsLibrary");
    }

    @Override
    public void onLowMemoryAsLibrary(Application application) {
        LogUtil.i(this.getClass().getName() + "onLowMemoryAsLibrary");
    }

    @Override
    public void onTrimMemoryAsLibrary(Application application, int level) {
        LogUtil.i(this.getClass().getName() + "onTrimMemoryAsLibrary");
    }

    @Override
    public void onConfigurationChanged(Application application, Configuration configuration) {
        LogUtil.i(this.getClass().getName() + "onConfigurationChanged");
    }

    /**
     * arouter定义的启动之前的初始化(可以忽略)
     */
    @Override
    public void init(Context context) {

    }


}
