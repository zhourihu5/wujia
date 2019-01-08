package com.wujia.businesslib.base;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDex;

import com.wujia.businesslib.LifecycleEventManager;
import com.wujia.businesslib.di.component.AppComponent;
import com.wujia.businesslib.di.component.DaggerAppComponent;
import com.wujia.businesslib.di.module.AppModule;
import com.wujia.businesslib.di.module.DataModule;
import com.wujia.lib.imageloader.ImageLoaderManager;
import com.wujia.lib_common.base.BizDialog;
import com.wujia.lib_common.data.network.NetConfig;
import com.wujia.lib_common.utils.AppContext;
import com.wujia.lib_common.utils.AppExecutors;
import com.wujia.lib_common.utils.AppManager;
import com.wujia.lib_common.utils.AppUtil;
import com.wujia.lib_common.utils.SoundPoolManager;
import com.wujia.lib_common.utils.Utils;
import com.wujia.lib_common.utils.mediaplayer.MediaPlayerHelper;
import com.wujia.lib_common.utils.sys.ScreenUtil;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;

public class BaseApplication extends Application {

    protected static BaseApplication instance;


    @Override
    public void onCreate() {
        super.onCreate();
        if (AppUtil.inMainProcess(this)) {
            instance = this;
            registerActivityLifecycleCallbacks(new BaseApplication.SwitchBackgroundCallbacks());

            performInit(instance);
            InitializeService.start(instance);

            setLifecycleEvent();
        }
    }

    private void setLifecycleEvent() {
        LifecycleEventManager.setupApp(this, new LifecycleEventManager.ILifecycleEventProvider() {

            @Override
            public List<LifecycleEventManager.Interceptor> get() {
                List<LifecycleEventManager.Interceptor> interceptors = new ArrayList<>();
                return interceptors;
            }
        });
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        MediaPlayerHelper.release();
        SoundPoolManager.getInstance().release();
        AppExecutors.inst().shutDown();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        ImageLoaderManager.getInstance().clearImageMemoryCache(this);
    }

    public AppComponent getAppComponent() {
        return DaggerAppComponent.builder()
                .dataModule(new DataModule(getNetConfig()))
                .appModule(new AppModule(instance))
                .build();

    }

    public NetConfig getNetConfig() {
        return null;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private class SwitchBackgroundCallbacks implements ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            AppManager.getInstance().addActivity(activity);
//            StatisticsHelper.getHelper().pageEnterTrack("page_enter_" + activity.getClass().getSimpleName());
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            AppManager.getInstance().removeActivity(activity);
            BizDialog.destroy(activity);
//            StatisticsHelper.getHelper().pageEndTrack("page_end_" + activity.getClass().getSimpleName());
        }
    }

    private void performInit(Application applicationContext) {
        AppContext.init(applicationContext);

        AppUtil.syncIsDebug(applicationContext);//判断是否是debug模式
        ScreenUtil.init(applicationContext);
        Utils.init(applicationContext);
        ARouter.init(applicationContext); // 尽可能早，推荐在Application中初始化
        if (AppUtil.isDebug()) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
    }

}
