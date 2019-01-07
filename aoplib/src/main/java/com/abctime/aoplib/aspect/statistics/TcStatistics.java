package com.abctime.aoplib.aspect.statistics;

import android.content.Context;
import android.text.TextUtils;

import com.abctime.lib_common.utils.ChannelUtils;
import com.tendcloud.tenddata.TCAgent;
import com.tendcloud.tenddata.TDAccount;

import java.util.List;
import java.util.Map;

/**
 * author:Created by xmren on 2018/7/19.
 * email :renxiaomin@100tal.com
 */

public class TcStatistics implements IStatisticsPoint {
    private Context mContext;
    private final String APPID;// = "9F94B8E9B7974AC8BBEB2767FAF02314";//CA2BD0A4F4534892BABD761C1A5F7E22 // 52CD5BE047154D7FAD9DFACC27E478C8

    public TcStatistics(String appId){
        APPID = appId;
    }

    @Override
    public void init(Context context) {
        mContext = context.getApplicationContext();
        String chanelName = ChannelUtils.getChannelName(context);
        if(TextUtils.isEmpty(chanelName)){
            chanelName="default";
        }
        TCAgent.init(context, APPID, chanelName);
        TCAgent.setReportUncaughtExceptions(true);
    }

    @Override
    public void destory() {

    }

    @Override
    public void customEventTrack(String event_id) {
        TCAgent.onEvent(mContext, event_id);
    }

    @Override
    public void registerEvent(UserFigure userFigure) {
        TCAgent.onRegister(userFigure.accountID, TDAccount.AccountType.REGISTERED, userFigure.name);
    }

    @Override
    public void loginEvent(UserFigure userFigure) {
        TCAgent.onLogin(userFigure.accountID, TDAccount.AccountType.REGISTERED, userFigure.name);
    }

    @Override
    public void placeOrder(String accountID, Order order) {
        TCAgent.onPlaceOrder(accountID, transferOrder(order));
    }

    @Override
    public void orderPaySuccess(String accountID, String payType, Order order) {
        TCAgent.onOrderPaySucc(accountID, payType, transferOrder(order));
    }

    @Override
    public void goodBrowse(GoodInfo goodInfo) {
        TCAgent.onViewItem(goodInfo.goodId, goodInfo.category, goodInfo.name, goodInfo.unitPrice);
    }

    @Override
    public void pageEnterTrack(String pageEvent) {
        TCAgent.onPageStart(mContext, pageEvent);
    }

    @Override
    public void pageEndTrack(String pageEvent) {
        TCAgent.onPageEnd(mContext, pageEvent);
    }

    @Override
    public void advancedEventTrack(Map<String, String> advancedMap) {

    }

    private com.tendcloud.tenddata.Order transferOrder(Order order) {
        com.tendcloud.tenddata.Order realorder = com.tendcloud.tenddata.Order.createOrder(order.orderId, (int) order.total, order.currencyType);
        List<GoodInfo> goodInfos = order.goodInfos;
        for (GoodInfo goodInfo : goodInfos) {
            realorder.addItem(goodInfo.goodId, goodInfo.category, goodInfo.name, goodInfo.unitPrice, (int) goodInfo.amount);
        }
        return realorder;
    }
}
