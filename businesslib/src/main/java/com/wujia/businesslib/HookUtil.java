package com.wujia.businesslib;

import android.os.Build;

import com.wujia.lib_common.utils.LogUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Author: created by shenbingkai on 2019/4/5 00 09
 * Email:  shenbingkai@gamil.com
 * Description:
 *visit https://www.cnblogs.com/genggeng/p/7716482.html
 */
public class HookUtil {


    public static void hookWebView() {
        int sdkInt = Build.VERSION.SDK_INT;
        try {
            Class factoryClass = Class.forName("android.webkit.WebViewFactory");
            Field field = factoryClass.getDeclaredField("sProviderInstance");
            field.setAccessible(true);
            Object sProviderInstance = field.get(null);
            if (sProviderInstance != null) {
                LogUtil.i("sProviderInstance isn't null");
                return;
            }
            Method getProviderClassMethod;
            if (sdkInt > 22) {
                getProviderClassMethod = factoryClass.getDeclaredMethod("getProviderClass");
            } else if (sdkInt == 22) {
                getProviderClassMethod = factoryClass.getDeclaredMethod("getFactoryClass");
            } else {
                LogUtil.i("Don't need to Hook WebView");
                return;
            }
            getProviderClassMethod.setAccessible(true);
            Class providerClass = (Class) getProviderClassMethod.invoke(factoryClass);
            Class delegateClass = Class.forName("android.webkit.WebViewDelegate");
            Constructor providerConstructor = providerClass.getConstructor(delegateClass);
            if (providerConstructor != null) {
                providerConstructor.setAccessible(true);
                Constructor declaredConstructor = delegateClass.getDeclaredConstructor();
                declaredConstructor.setAccessible(true);
                sProviderInstance = providerConstructor.newInstance(declaredConstructor.newInstance());
                field.set("sProviderInstance", sProviderInstance);
            }
            LogUtil.i("Hook done!");

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
