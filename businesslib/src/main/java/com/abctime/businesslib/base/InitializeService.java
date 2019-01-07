package com.abctime.businesslib.base;

import android.app.Application;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;

import com.abctime.aoplib.aspect.statistics.StatisticsHelper;
import com.abctime.lib_common.utils.SoundPoolManager;
import com.abctime.lib_common.utils.mediaplayer.MediaPlayerHelper;
import com.squareup.leakcanary.LeakCanary;


public class InitializeService extends JobIntentService {

    private static final int JOB_ID = Integer.MAX_VALUE;
    private static final String ACTION_INIT_WHEN_APP_CREATE = "com.tal.abctimelibrary.INIT";

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        final String action = intent.getAction();
        if (ACTION_INIT_WHEN_APP_CREATE.equals(action)) {
            performInit();
        }
    }

    public static void start(Context context) {
        Intent intent = new Intent();
        intent.setAction(ACTION_INIT_WHEN_APP_CREATE);
        enqueueWork(context, InitializeService.class, JOB_ID, intent);
    }


    private void performInit() {
        Application applicationContext = getApplication();
        MediaPlayerHelper.getInstance();
        SoundPoolManager.getInstance().initSound(applicationContext);

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(applicationContext);
    }
}