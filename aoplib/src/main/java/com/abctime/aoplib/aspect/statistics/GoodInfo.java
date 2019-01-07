package com.abctime.aoplib.aspect.statistics;

/**
 * author:Created by xmren on 2018/7/18.
 * email :renxiaomin@100tal.com
 */

public class GoodInfo {
    public String goodId;
    public String category;
    public String name;
    public int unitPrice;
    public float amount;

    public GoodInfo(String goodId, String category, String name, int unitPrice, float amount) {
        this.goodId = goodId;
        this.category = category;
        this.name = name;
        this.unitPrice = unitPrice;
        this.amount = amount;
    }

    public static GoodInfo createGood(String goodId, String category, String name, int unitPrice, float amount){
        return new GoodInfo(goodId,category,name,unitPrice,amount);
    }
}
