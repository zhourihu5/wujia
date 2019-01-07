package com.abctime.aoplib.aspect.statistics;

import android.content.Context;

import com.tendcloud.tenddata.TDAccount;

import java.util.Map;

/**
 * author:Created by xmren on 2018/7/19.
 * email :renxiaomin@100tal.com
 */

public class OwerStatistics implements IStatisticsPoint {
    private Context mContext;
    private String uid;

    public OwerStatistics(String uid){
        this.uid = uid;
    }

    @Override
    public void init(Context context) {
        this.mContext = context.getApplicationContext();
        StatisticsAgent.init(context, uid);
    }

    @Override
    public void destory() {
        StatisticsAgent.destory();
    }

    @Override
    public void customEventTrack(String event_id) {
        StatisticsAgent.onEvent(event_id);
    }

    @Override
    public void registerEvent(UserFigure userFigure) {
        StatisticsAgent.onRegister(userFigure.accountID, TDAccount.AccountType.REGISTERED, userFigure.name);
    }

    @Override
    public void loginEvent(UserFigure userFigure) {
        StatisticsAgent.onLogin(userFigure.accountID, TDAccount.AccountType.REGISTERED, userFigure.name);
    }

    @Override
    public void placeOrder(String accountID, Order order) {
        StatisticsAgent.placeOrder(accountID, "");
    }

    @Override
    public void orderPaySuccess(String accountID, String payType, Order order) {
        StatisticsAgent.orderPaySuccess(accountID, "");
    }

    @Override
    public void goodBrowse(GoodInfo goodInfo) {

    }

    @Override
    public void pageEnterTrack(String pageEvent) {
        StatisticsAgent.onPageStart(mContext, pageEvent);
    }

    @Override
    public void pageEndTrack(String pageEvent) {
        StatisticsAgent.onPageEnd(mContext, pageEvent);
    }

    @Override
    public void advancedEventTrack(Map<String, String> advancedMap) {

    }
}
