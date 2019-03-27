package com.wujia.intellect.terminal.mvp.home.data;

import com.wujia.businesslib.data.RootResponse;

import java.util.ArrayList;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-27
 * description ：
 */
public class WeatherBean extends RootResponse {

    public ArrayList<Weather> content;

    public class Weather {

        public String weather;
        public String temperature;
    }
}
