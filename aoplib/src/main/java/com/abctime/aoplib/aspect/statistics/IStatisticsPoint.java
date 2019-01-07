package com.abctime.aoplib.aspect.statistics;

import android.content.Context;

import java.util.Map;

/**
 * author:Created by xmren on 2018/7/19.
 * email :renxiaomin@100tal.com
 */

public interface IStatisticsPoint {
    public void init(Context context);

    public void destory();

    public void customEventTrack(String event_id);

    public void registerEvent(UserFigure userFigure);

    public void loginEvent(UserFigure userFigure);

    public void placeOrder(String accountID,Order order);

    public void orderPaySuccess(String accountID, String payType, Order order);

    public void goodBrowse(GoodInfo goodInfo);

    public void pageEnterTrack(String pageEvent);

    public void pageEndTrack(String pageEvent);

    public void advancedEventTrack(Map<String, String> advancedMap);
}
