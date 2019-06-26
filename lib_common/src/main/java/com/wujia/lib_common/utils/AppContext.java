package com.wujia.lib_common.utils;

import android.content.Context;

public class AppContext {
    public static Context mAppContext;


    public static void init(Context context) {
        if (mAppContext == null) {
            mAppContext = context.getApplicationContext();
        } else {
            throw new IllegalStateException("set context duplicate");
        }
    }

    public static Context get() {
        if (mAppContext == null) {
            throw new IllegalStateException("forget init?");
        } else {
            return mAppContext;
        }
    }
}
