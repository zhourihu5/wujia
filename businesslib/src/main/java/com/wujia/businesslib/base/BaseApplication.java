package com.wujia.businesslib.base;


import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.jingxi.smartlife.pad.sdk.JXPadSdk;
import com.wujia.lib_common.utils.AppContext;
import com.wujia.lib_common.utils.SystemUtil;

import java.lang.ref.WeakReference;

public class BaseApplication extends Application {

    protected static BaseApplication instance;
    String TAG="wujia";
    protected static WeakReference<Activity> currentActivity;
    public static Activity getCurrentAcitivity(){
        if(currentActivity!=null){
            return currentActivity.get();
        }
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        AppContext.init(instance);
        initSDKManager();

        SystemUtil.init();


        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                currentActivity=new WeakReference<>(activity);
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
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    private void initSDKManager() {
        JXPadSdk.init(instance);
        JXPadSdk.initDoorAccess();
//        JXPadSdk.initPushManager();//todo replace it by our push，原来的push APPkey等信息是写在manifest里的，


        // 如果需要更改为我们自己的推送，用代码住处推送账号信息

//        String appKey="8bc0518b5aa55c176ffbcbcb";
//        String appSecret="52251687bfd0b5dcebda580d";
//        PushServiceFactory.init(instance);
//        CloudPushService pushService = PushServiceFactory.getCloudPushService();
//        pushService.register(instance,appKey,appSecret, new CommonCallback() {
//            @Override
//            public void onSuccess(String response) {
//                JXLogUtil.logW(TAG, "init success ");
//            }
//
//            @Override
//            public void onFailed(String errorCode, String errorMessage) {
//                JXLogUtil.logW(TAG, "init faild errorCode = " + errorCode + " errorMessage = " + errorMessage);
//            }
//        });
    }
}
