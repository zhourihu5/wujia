package com.abctime.aoplib.aspect.statistics;

import java.util.ArrayList;
import java.util.List;

/**
 * author:Created by xmren on 2018/7/18.
 * email :renxiaomin@100tal.com
 */

public class Order {
    public String orderId;
    public float total;
    public String currencyType = "CNY";
    public List<GoodInfo> goodInfos;

    public Order(String orderId, float total) {
        this.orderId = orderId;
        this.total = total;
        goodInfos = new ArrayList<>();
    }

    public static Order createOrder(String orderId, float total) {
        return new Order(orderId, total);
    }

    public void addGood(GoodInfo goodInfo) {
        if (goodInfos == null) {
            goodInfos = new ArrayList<>();
        }
        goodInfos.add(goodInfo);
    }
}
