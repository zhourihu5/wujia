package com.abctime.lib_common.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * description:
 * author: KisenHuang
 * email: KisenHuang@163.com
 * time: 2018/8/2 下午6:04
 */

public class ChannelUtils {

    public static String getCurrentChannelId(Context context) {
        String chanelName = getChannelName(context);
        if (TextUtils.isEmpty(chanelName))
            chanelName = "default";
        String channelId = "2";
        if (chanelName.equals("yingyongbao")) {
            channelId = "5";
        } else if (chanelName.equals("huawei")) {
            channelId = "3";
        } else if (chanelName.equals("xiaomi")) {
            channelId = "4";
        } else if (chanelName.equals("wandoujia")) {
            channelId = "6";
        } else if (chanelName.equals("oppo")) {
            channelId = "9";
        } else if (chanelName.equals("baidu")) {
            channelId = "7";
        } else if (chanelName.equals("_360")) {
            channelId = "8";
        } else if (chanelName.equals("vivo")) {
            channelId = "10";
        } else if (chanelName.equals("meizu")) {
            channelId = "11";
        }
        return channelId;
    }

    @NonNull
    public static String getChannelName(Context context) {
        String channelName;
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            channelName = applicationInfo.metaData.getString("channelName", "default");
        } catch (Exception e) {
            e.printStackTrace();
            channelName = "default";
        }
        return channelName;
    }

}
