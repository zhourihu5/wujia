package com.wujia.businesslib;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

/**
 * description:
 * author: KisenHuang
 * email: KisenHuang@163.com
 * time: 2018/7/16 下午2:30
 */

public class LifecycleEventManager {

    public static void setupApp(Application application, ILifecycleEventProvider provider) {
        if (application != null) {
            List<Interceptor> interceptors = provider.get();
            application.registerActivityLifecycleCallbacks(new ActivityEventLifecycle(interceptors));
        }
    }

    private static class ActivityEventLifecycle implements Application.ActivityLifecycleCallbacks {

        private List<Interceptor> mInterceptors;

        ActivityEventLifecycle(List<Interceptor> interceptors) {
            this.mInterceptors = interceptors;
        }

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            if (mInterceptors == null)
                return;
            for (Interceptor interceptor : mInterceptors) {
                interceptor.register(activity);
            }
            if (activity instanceof AppCompatActivity)
                ((AppCompatActivity) activity).getSupportFragmentManager()
                        .registerFragmentLifecycleCallbacks(new FragmentEventLifecycle(mInterceptors), true);
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
            if (mInterceptors == null)
                return;
            for (Interceptor interceptor : mInterceptors) {
                interceptor.unregister(activity);
            }
        }
    }

    private static class FragmentEventLifecycle extends FragmentManager.FragmentLifecycleCallbacks {
        private List<Interceptor> mInterceptors;

        FragmentEventLifecycle(@Nullable List<Interceptor> interceptors) {
            this.mInterceptors = interceptors;
        }

        @Override
        public void onFragmentAttached(FragmentManager fm, Fragment f, Context context) {
            if (mInterceptors == null)
                return;
            for (Interceptor interceptor : mInterceptors) {
                interceptor.register(f);
            }
        }

        @Override
        public void onFragmentDetached(FragmentManager fm, Fragment f) {
            if (mInterceptors == null)
                return;
            for (Interceptor interceptor : mInterceptors) {
                interceptor.unregister(f);
            }
        }
    }

    public interface ILifecycleEventProvider {
        /**
         * 该方法只能被调用一次
         * @return 返回所有事件拦截器
         */
        List<Interceptor> get();
    }

    public interface Interceptor {

        void register(Object object);

        void unregister(Object object);
    }

}
