package com.abctime.aoplib.aspect.statistics.event;

import android.util.Log;

/**
 * author:Created by xmren on 2018/7/13.
 * email :renxiaomin@100tal.com
 */

public class StatisticsError extends Statistics {

    @Override
    public String getType() {
        return ERROR;
    }


    @Override
    public String getExtras() {
        if (e != null)
            return Log.getStackTraceString(e);
        return "";
    }

    private Throwable e;

    public StatisticsError(Throwable e) {
        this.e = e;
    }
}
