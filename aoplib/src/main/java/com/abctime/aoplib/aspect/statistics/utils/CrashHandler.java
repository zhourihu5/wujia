package com.abctime.aoplib.aspect.statistics.utils;

import android.content.Context;
import android.os.Build;

import com.abctime.aoplib.BuildConfig;
import com.abctime.aoplib.aspect.statistics.event.StatisticsCrash;
import com.abctime.lib_common.utils.AppManager;
import com.abctime.lib_common.utils.LogUtil;
import com.tendcloud.tenddata.TCAgent;

/**
 * author:Created by xmren on 2018/7/13.
 * email :renxiaomin@100tal.com
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private volatile static CrashHandler INSTANCE;
    private Context mContext;
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private CrashHandler() {
    }

    public static CrashHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CrashHandler();
        }
        return INSTANCE;
    }

    public void init(Context context) {
        this.mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (thread.getId() != 1) {
                thread.interrupt();
            } else {
                if (BuildConfig.DEBUG && mDefaultHandler != null) {
                    mDefaultHandler.uncaughtException(thread, ex);
                } else {
                    AppManager.getAppManager().AppExit();
                }
            }
            LogUtil.e(ex.getMessage());
        }
    }

    private boolean handleException(final Throwable ex) {
        if (ex == null) {
            return false;
        }
        if (mContext != null)
            TCAgent.onError(mContext, ex);
        new StatisticsCrash(ex).write();
        return true;
    }


}
