package com.abctime.aoplib.aspect.statistics.event;

import android.support.annotation.StringDef;

import com.abctime.aoplib.aspect.statistics.utils.DeviceUtil;
import com.abctime.aoplib.aspect.statistics.utils.StatisticsFileUtil;
import com.abctime.lib_common.utils.AppExecutors;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;

/**
 * author:Created by xmren on 2018/7/13.
 * email :renxiaomin@100tal.com
 */

public class Statistics implements IStatistics {
    protected static final String INFO = "INFO";
    protected static final String CRASH = "CRASH";
    protected static final String ERROR = "ERROR";
    protected static final String EVENT = "EVENT";
    protected static final String NETWORK = "NETWORK";
    protected static final String HTTP = "HTTP";
    protected static final String RECORD = "RECORD";

    public String eventId;
    public Map<String, String> statisticsExtraParamMap;
    protected String time;


    public Statistics() {}

    public Statistics(String eventId) {
        this.eventId = eventId;
    }

    public Statistics(String eventId, Map<String, String> statisticsExtraParamMap) {
        this.eventId = eventId;
        this.statisticsExtraParamMap = statisticsExtraParamMap;
    }

    public void addKeyValue(String key, String value) {
        if (statisticsExtraParamMap == null) {
            statisticsExtraParamMap = new HashMap<>();
        }
        statisticsExtraParamMap.put(key, value);
    }

    private String getTime(){
        return DeviceUtil.formatTime(System.currentTimeMillis());
    }

    @Override
    public String getType() {
        return "";
    }

    @Override
    public String getExtras() {
        return "";
    }

    @Override
    public String format() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(getType());
        sb.append("]");
        sb.append(" ");
        sb.append(getTime());
        sb.append(" ");
        sb.append(getExtras());
        sb.append(" ");
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public void write() {
        AppExecutors.inst().singleIO().submit(new Runnable() {
            @Override
            public void run() {
                StatisticsFileUtil.write(format());

            }
        });
    }

    @StringDef({INFO, CRASH, ERROR, EVENT, NETWORK})
    @Retention(RetentionPolicy.SOURCE)
    public @interface EventType {
    }
}
