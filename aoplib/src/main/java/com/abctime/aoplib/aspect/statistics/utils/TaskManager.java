package com.abctime.aoplib.aspect.statistics.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * author:Created by xmren on 2018/7/13.
 * email :renxiaomin@100tal.com
 */

public class TaskManager {
    private static ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    private TaskManager() {

    }

    public static void addTask(Runnable task) {
        if (singleThreadExecutor == null) {
            singleThreadExecutor = Executors.newSingleThreadExecutor();
        }
        singleThreadExecutor.execute(task);
    }

    public static void destory() {
        if (singleThreadExecutor != null && !singleThreadExecutor.isShutdown()) {
            singleThreadExecutor.shutdown();
        }
    }
}
