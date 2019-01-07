package com.abctime.aoplib.aspect.statistics;

import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * author:Created by xmren on 2018/7/13.
 * email :renxiaomin@100tal.com
 */

public class StatisticsHelper implements IStatisticsPoint {
    private static StatisticsHelper helper;
    private List<IStatisticsPoint> allTrackerPlatform;

    public static StatisticsHelper getHelper() {
        if (helper == null) {
            helper = new StatisticsHelper();
        }
        return helper;
    }

    private StatisticsHelper() {

    }

    public void setProvider(StatisticsProvider provider) {
        if (provider == null)
            return;
        allTrackerPlatform = provider.get();
    }

    @Override
    public void init(Context context) {
//        allTrackerPlatform = getAllTrackerPlatform();
        if (allTrackerPlatform == null) {
            return;
        }
        for (IStatisticsPoint statisticsPoint : allTrackerPlatform) {
            statisticsPoint.init(context);
        }
    }

    @Override
    public void destory() {
        if (allTrackerPlatform == null) {
            return;
        }
        for (IStatisticsPoint statisticsPoint : allTrackerPlatform) {
            statisticsPoint.destory();
        }
    }

    @Override
    public void customEventTrack(String event_id) {
        if (TextUtils.isEmpty(event_id)) {
            return;
        }
        event_id = event_id.toLowerCase();
        if (allTrackerPlatform == null) {
            return;
        }
        for (IStatisticsPoint statisticsPoint : allTrackerPlatform) {
            statisticsPoint.customEventTrack("event_" + event_id);
        }
    }

    @Override
    public void registerEvent(UserFigure userFigure) {
        if (allTrackerPlatform == null) {
            return;
        }
        for (IStatisticsPoint statisticsPoint : allTrackerPlatform) {
            statisticsPoint.registerEvent(userFigure);
        }
    }

    @Override
    public void loginEvent(UserFigure userFigure) {
        if (allTrackerPlatform == null) {
            return;
        }
        for (IStatisticsPoint statisticsPoint : allTrackerPlatform) {
            statisticsPoint.loginEvent(userFigure);
        }
    }

    @Override
    public void placeOrder(String accountID, Order order) {
        if (allTrackerPlatform == null) {
            return;
        }
        for (IStatisticsPoint statisticsPoint : allTrackerPlatform) {
            statisticsPoint.placeOrder(accountID, order);
        }
    }

    @Override
    public void orderPaySuccess(String accountID, String payType, Order order) {
        if (allTrackerPlatform == null) {
            return;
        }
        for (IStatisticsPoint statisticsPoint : allTrackerPlatform) {
            statisticsPoint.orderPaySuccess(accountID, payType, order);
        }
    }

    @Override
    public void goodBrowse(GoodInfo goodInfo) {
        if (allTrackerPlatform == null) {
            return;
        }
        for (IStatisticsPoint statisticsPoint : allTrackerPlatform) {
            statisticsPoint.goodBrowse(goodInfo);
        }
    }

    @Override
    public void pageEnterTrack(String pageEvent) {
        if (allTrackerPlatform == null) {
            return;
        }
        if (TextUtils.isEmpty(pageEvent)) {
            return;
        }
        pageEvent = pageEvent.toLowerCase();
        for (IStatisticsPoint statisticsPoint : allTrackerPlatform) {
            statisticsPoint.pageEnterTrack(pageEvent);
        }
    }

    @Override
    public void pageEndTrack(String pageEvent) {
        if (allTrackerPlatform == null) {
            return;
        }
        if (TextUtils.isEmpty(pageEvent)) {
            return;
        }
        pageEvent = pageEvent.toLowerCase();
        for (IStatisticsPoint statisticsPoint : allTrackerPlatform) {
            statisticsPoint.pageEndTrack(pageEvent);
        }
    }

    @Override
    public void advancedEventTrack(Map<String, String> advancedMap) {
        if (allTrackerPlatform == null) {
            return;
        }
        for (IStatisticsPoint statisticsPoint : allTrackerPlatform) {
            statisticsPoint.advancedEventTrack(advancedMap);
        }
    }

    public List<IStatisticsPoint> getAllTrackerPlatform() {
        List<IStatisticsPoint> statisticsPointList = new ArrayList<>();
        IStatisticsPoint owerStatistics = new OwerStatistics("");
        IStatisticsPoint tcStatistics = new TcStatistics("");
        statisticsPointList.add(tcStatistics);
        statisticsPointList.add(owerStatistics);
        return statisticsPointList;
    }

    public interface StatisticsProvider {
        List<IStatisticsPoint> get();
    }
}
