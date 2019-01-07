package com.abctime.aoplib.aspect.statistics.event;

import android.content.Context;

import com.abctime.aoplib.aspect.statistics.StatisticsAgent;
import com.abctime.aoplib.aspect.statistics.utils.DeviceUtil;
import com.abctime.lib_common.utils.AppContext;
import com.abctime.lib_common.utils.AppUtil;

/**
 * author:Created by xmren on 2018/7/13.
 * email :renxiaomin@100tal.com
 */

public class StatisticsInfo extends Statistics {
    private String uuid;

    @Override
    public String getType() {
        return INFO;
    }

    @Override
    public String getExtras() {
        Context context = StatisticsAgent.getInstance().getContext();
        if (context == null) {
            return "";
        }
        String devid = AppUtil.getDeviceInfo(AppContext.get());
        String model = DeviceUtil.getDeviceBrand() + " " + DeviceUtil.getSystemModel();
        String os = DeviceUtil.getSystemVersion();
        String version = AppUtil.getVersionName(context);
        String uid = uuid;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(devid);
        stringBuilder.append(" ");
        stringBuilder.append(model);
        stringBuilder.append("  Android");
        stringBuilder.append(os);
        stringBuilder.append(" v");
        stringBuilder.append(version);
        stringBuilder.append(" ");
        stringBuilder.append(uid);
        stringBuilder.append(" ");
        return stringBuilder.toString();
    }


    public StatisticsInfo(String uuid) {
        this.uuid = uuid;
    }
}
