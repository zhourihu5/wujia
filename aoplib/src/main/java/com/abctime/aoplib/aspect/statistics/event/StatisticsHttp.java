package com.abctime.aoplib.aspect.statistics.event;

/**
 * Author: created by shenbingkai on 2018/11/2 18 20
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class StatisticsHttp extends Statistics {

    private String httpinfo;

    @Override
    public String getType() {
        return HTTP;
    }

    @Override
    public String getExtras() {

        return httpinfo;
    }

    public StatisticsHttp(String httpinfo) {
        this.httpinfo = httpinfo;
    }
}
