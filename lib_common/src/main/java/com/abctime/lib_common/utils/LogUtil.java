package com.abctime.lib_common.utils;


import com.abctime.lib_common.BuildConfig;
import com.orhanobut.logger.Logger;

/**
 * Created by xmren on 2017/8/7.
 */

public class LogUtil {
    public static boolean isDebug = BuildConfig.DEBUG;
    private static final String TAG = "com.abctime.android";

    public static void e(String msg, Object... tag) {
        if (isDebug) {
            Logger.e(msg, tag);
        }
    }

    public static void w(String msg, Object... o) {
        if (isDebug) {
            Logger.w(msg, o);
        }
    }

    public static void d(String msg, Object... o) {
        if (isDebug) {
            Logger.d(msg, o);
        }
    }

    public static void d(Object o) {
        if (isDebug) {
            Logger.d(o);
        }
    }

    public static void i(String msg) {
        if (isDebug) {
            Logger.i(msg);
        }
    }


    public static void t(String develop, String s) {
        Logger.t(develop).d(s);
    }
}
