package com.abctime.aoplib.aspect.statistics.event;

/**
 * author:Created by xmren on 2018/7/13.
 * email :renxiaomin@100tal.com
 */

public interface IStatistics {
    String getType();

    String getExtras();

    String format();

    void write();
}
