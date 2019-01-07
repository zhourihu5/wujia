package com.abctime.aoplib.aspect.statistics.event;

/**
 * Author: created by shenbingkai on 2018/11/2 18 20
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class StatisticsRecord extends Statistics {

    private String record;

    @Override
    public String getType() {
        return RECORD;
    }

    @Override
    public String getExtras() {

        return record;
    }

    public StatisticsRecord(String record) {
        this.record = record;
    }
}
