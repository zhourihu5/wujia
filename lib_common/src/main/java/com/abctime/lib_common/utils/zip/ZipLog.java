package com.abctime.lib_common.utils.zip;

import android.util.Log;

import com.abctime.lib_common.BuildConfig;

final class ZipLog {
    private static final String TAG = "ZipLog";

    private static boolean DEBUG = BuildConfig.DEBUG;

    static void config(boolean debug) {
        DEBUG = debug;
    }

    static void debug(String msg) {
        if (DEBUG) Log.d(TAG, msg);
    }
}
