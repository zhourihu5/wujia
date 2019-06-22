package com.wujia.lib_common.utils;

import android.util.Log;

public class LogUtil {


    private static final String TAG = "--NoTAG--";
//    private static boolean logSwitch = BuildConfig.DEBUG;
    private static boolean logSwitch = true;


    public static void i(Object message) {
        if (logSwitch) {
            Log.i(TAG, message.toString());
        }
    }

    public static void e(Object message) {
        if (logSwitch) {
            Log.e(TAG, message.toString());
        }
    }

    public static void debug(String tag,Object message) {
        if (logSwitch) {
            Log.d(tag, message.toString());
        }
    }

    public static void warn(String tag,Object message) {
        if (logSwitch) {
            Log.w(tag, message.toString());
        }
    }
    public static void info(String tag,Object message) {
        if (logSwitch) {
            Log.i(tag, message.toString());
        }
    }

    public static void error(String tag,Object message) {
        if (logSwitch) {
            Log.e(tag, message.toString());
        }
    }
    public static void t(Object message,Throwable t) {
        if (logSwitch) {
            Log.e(TAG, message.toString(),t);
        }
    }

    public static void d(Object message) {
        if (logSwitch) {
            Log.d(TAG, message.toString());
        }
    }

    public static void w(Object message) {
        if (logSwitch) {
            Log.w(TAG, message.toString());
        }
    }
}
