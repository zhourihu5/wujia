package com.abctime.aoplib.aspect.statistics.event;

/**
 * author:Created by xmren on 2018/7/13.
 * email :renxiaomin@100tal.com
 */

public class StatisticsNetwork extends Statistics {

    @Override
    public String getType() {
        return NETWORK;
    }


    @Override
    public String getExtras() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(url);
        stringBuilder.append(" ");
        stringBuilder.append(params);
        stringBuilder.append(" ");
        stringBuilder.append(response);
        stringBuilder.append(" ");
        stringBuilder.append(timespace);
        return stringBuilder.toString();
    }


    private String url;
    private String params;
    private String response;
    private long timespace;

    public StatisticsNetwork(String url, String params, String response, long timespace) {
        this.url = url;
        this.params = params;
        this.response = response;
        this.timespace = timespace;
    }
}
