package com.abctime.lib_common.utils;


import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * author:Created by xmren on 2018/7/13.
 * email :renxiaomin@100tal.com
 */
public final class AppExecutors {

    private static AppExecutors mInst;

    private final ExecutorService mSingleIO;

    private ExecutorService mPoolIO;

    private final Executor mMainThread;

    private AppExecutors(ExecutorService singleIO, ExecutorService poolIO, Executor mainThread) {
        mSingleIO = singleIO;
        mPoolIO = poolIO;
        mMainThread = mainThread;
    }

    public AppExecutors updatePoolIO(int nThreads) {
        mPoolIO = Executors.newFixedThreadPool(nThreads);
        return this;
    }

    private AppExecutors() {
        this(Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()),
                new MainThreadExecutor());
    }

    public static AppExecutors inst() {
        if (mInst == null) {
            synchronized (AppExecutors.class) {
                if (mInst == null) {
                    mInst = new AppExecutors();
                }
            }
        }
        return mInst;
    }

    public ExecutorService singleIO() {
        return mSingleIO;
    }

    public ExecutorService poolIO() {
        return mPoolIO;
    }

    public Executor mainThread() {
        return mMainThread;
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mHandler.post(command);
        }
    }

    public void shutDown() {
        if (mSingleIO != null) {
            mSingleIO.shutdown();
        }
        if (mPoolIO != null) {
            mPoolIO.shutdown();
        }
    }
}
