package com.abctime.aoplib.aspect.statistics.event;

import org.json.JSONObject;

/**
 * author:Created by xmren on 2018/7/13.
 * email :renxiaomin@100tal.com
 */

public class EventStatistics extends Statistics {
    public EventStatistics(String eventId) {
        super(eventId);
    }

    @Override
    public String getType() {
        return EVENT;
    }

    @Override
    public String getExtras() {
        if (statisticsExtraParamMap != null) {
            return eventId + " " + new JSONObject(statisticsExtraParamMap);
        }
        return eventId + " ";
    }
}
