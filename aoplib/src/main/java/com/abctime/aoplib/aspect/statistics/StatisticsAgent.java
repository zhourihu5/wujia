package com.abctime.aoplib.aspect.statistics;

import android.content.Context;

import com.abctime.aoplib.aspect.statistics.event.EventStatistics;
import com.abctime.aoplib.aspect.statistics.event.PageStatistics;
import com.abctime.aoplib.aspect.statistics.event.StatisticsError;
import com.abctime.aoplib.aspect.statistics.event.StatisticsInfo;
import com.abctime.aoplib.aspect.statistics.event.StatisticsNetwork;
import com.abctime.aoplib.aspect.statistics.utils.CrashHandler;
import com.abctime.aoplib.aspect.statistics.utils.StatisticsFileUtil;
import com.abctime.lib_common.utils.FileUtils;
import com.tendcloud.tenddata.TDAccount;

/**
 * author:Created by xmren on 2018/7/13.
 * email :renxiaomin@100tal.com
 */

public class StatisticsAgent {

    private static volatile StatisticsAgent mClickAgent;
    private Context mContext;

    private StatisticsAgent() {
    }

    public static StatisticsAgent getInstance() {
        if (mClickAgent == null) {
            mClickAgent = new StatisticsAgent();
        }
        return mClickAgent;
    }

    public Context getContext() {
        return mContext;
    }


    public static void init(Context context, String uuid) {
        getInstance().mContext = context.getApplicationContext();
        StatisticsFileUtil.setLogSaveFilePath(FileUtils.getLogSaveFile(context));
        CrashHandler.getInstance().init(context);
        new StatisticsInfo(uuid).write();
    }

    public static void onError(Throwable e) {
        new StatisticsError(e).write();
    }

    public static void onPageStart(Context context) {
        new PageStatistics(context, "onPageStart").write();
    }

    public static void onPageStart(Context context, String eventId) {
        new PageStatistics(context, eventId + "_start").write();
    }


    public static void onPageEnd(Context context, String eventId) {
        new PageStatistics(context, eventId + "_end").write();
    }

    public static void onEvent(String eventId) {
        new EventStatistics(eventId).write();
    }


    public static void onNetwork(String url, String params, String response, long timespace) {
        new StatisticsNetwork(url, params, response, timespace).write();
    }


    public static void destory() {

    }

    public static void onRegister(String accountID, TDAccount.AccountType registered, String name) {

    }

    public static void onLogin(String accountID, TDAccount.AccountType registered, String name) {

    }

    public static void placeOrder(String accountID, String orderInfo) {

    }

    public static void orderPaySuccess(String accountID, String orderInfo) {

    }
}
