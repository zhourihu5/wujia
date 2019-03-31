package com.wujia.intellect.terminal.mvp.home.data;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;
import com.wujia.businesslib.data.RootResponse;

import java.util.ArrayList;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-27
 * description ：
 */
public class WeatherBean extends RootResponse {

    public ArrayList<Weather> content;

    @Table("table_message")
    public class Weather {

        @PrimaryKey(AssignType.AUTO_INCREMENT)
        private int _id;

        public String weather;
        public String temperature;
        public String time;
        public String wind_direction;
        public String wind_power;
    }
}
