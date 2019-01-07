package com.abctime.aoplib.aspect;

import java.util.concurrent.TimeUnit;

/**
 * author:Created by xmren on 2018/7/12.
 * email :renxiaomin@100tal.com
 */

public class MethodWatcher {
    private long startTime;
    private long endTime;
    private long elapsedTime;

    public MethodWatcher() {
    }

    private void reset() {
        startTime = 0;
        endTime = 0;
        elapsedTime = 0;
    }

    public void start() {
        reset();
        startTime = System.nanoTime();
    }

    public void stop() {
        if (startTime != 0) {
            endTime = System.nanoTime();
            elapsedTime = endTime - startTime;
        } else {
            reset();
        }
    }

    public long getTotalTimeMillis() {
        return (elapsedTime != 0) ? TimeUnit.NANOSECONDS.toMillis(endTime - startTime) : 0;
    }
    
}
