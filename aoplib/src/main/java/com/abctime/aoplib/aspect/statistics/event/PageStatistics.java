package com.abctime.aoplib.aspect.statistics.event;

import android.content.Context;

import org.json.JSONObject;

/**
 * author:Created by xmren on 2018/7/13.
 * email :renxiaomin@100tal.com
 */

public class PageStatistics extends Statistics {
    private Context context;

    public PageStatistics(Context context, String eventId) {
        this.context = context;
        this.eventId = eventId;
    }

    @Override
    public String getType() {
        return EVENT;
    }

    @Override
    public String getExtras() {
        if (context != null) {
            String map = statisticsExtraParamMap != null ? " param " + new JSONObject(statisticsExtraParamMap).toString() : "";
            return eventId + " " + context.getClass().getSimpleName() + map;
        }
        return eventId;
    }
}
