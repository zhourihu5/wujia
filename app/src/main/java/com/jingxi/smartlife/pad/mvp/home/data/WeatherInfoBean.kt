package com.jingxi.smartlife.pad.mvp.home.data

import com.wujia.lib_common.base.RootResponse

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-27
 * description ：
 */
class WeatherInfoBean : RootResponse() {
    /**
     * code : 0
     * data : {"restrict":{"createDate":"2019-06-18T06:38:08.579Z","id":0,"num":"string"},"token":"string","weather":{}}
     */

    //    @SerializedName("code")
    //    private int codeX;
    //    public int getCodeX() {
    //        return codeX;
    //    }
    //
    //    public void setCodeX(int codeX) {
    //        this.codeX = codeX;
    //    }

    var data: DataBean? = null

    class DataBean {
        /**
         * restrict : {"createDate":"2019-06-18T06:38:08.579Z","id":0,"num":"string"}
         * token : string
         * weather : {}
         */

        var restrict: RestrictBean? = null
        var token: String? = null
        var weather: WeatherBean? = null

        class RestrictBean {
            /**
             * createDate : 2019-06-18T06:38:08.579Z
             * id : 0
             * num : string
             */

            var createDate: String? = null
            var id: Int = 0
            var num: String? = null
        }

        class WeatherBean {

            /**
             * showapi_res_error :
             * showapi_res_id : 331bbc3e1be44ddd93b15a0ddf846864
             * showapi_res_code : 0
             * showapi_res_body : {"ret_code":0,"area":"北京","showapi_fee_code":-1,"areaid":"101010100","hourList":[{"weather_code":"04","time":"201906181500","area":"北京","wind_direction":"东南风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"雷阵雨","temperature":"31"},{"weather_code":"04","time":"201906181600","area":"北京","wind_direction":"东南风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"雷阵雨","temperature":"31"},{"weather_code":"04","time":"201906181700","area":"北京","wind_direction":"南风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"雷阵雨","temperature":"31"},{"weather_code":"04","time":"201906181800","area":"北京","wind_direction":"东南风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"雷阵雨","temperature":"30"},{"weather_code":"04","time":"201906181900","area":"北京","wind_direction":"东南风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"雷阵雨","temperature":"28"},{"weather_code":"04","time":"201906182000","area":"北京","wind_direction":"南风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"雷阵雨","temperature":"27"},{"weather_code":"01","time":"201906182100","area":"北京","wind_direction":"东南风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"多云","temperature":"26"},{"weather_code":"01","time":"201906182200","area":"北京","wind_direction":"东南风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"多云","temperature":"26"},{"weather_code":"02","time":"201906182300","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"阴","temperature":"25"},{"weather_code":"01","time":"201906190000","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"多云","temperature":"25"},{"weather_code":"01","time":"201906190100","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"多云","temperature":"24"},{"weather_code":"01","time":"201906190200","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"多云","temperature":"23"},{"weather_code":"01","time":"201906190300","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"多云","temperature":"23"},{"weather_code":"01","time":"201906190400","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"多云","temperature":"22"},{"weather_code":"02","time":"201906190500","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"阴","temperature":"22"},{"weather_code":"04","time":"201906190600","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"雷阵雨","temperature":"23"},{"weather_code":"04","time":"201906190700","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"雷阵雨","temperature":"24"},{"weather_code":"04","time":"201906190800","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"雷阵雨","temperature":"25"},{"weather_code":"02","time":"201906190900","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"阴","temperature":"26"},{"weather_code":"02","time":"201906191000","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"阴","temperature":"27"},{"weather_code":"01","time":"201906191100","area":"北京","wind_direction":"东南风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"多云","temperature":"28"},{"weather_code":"02","time":"201906191200","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"阴","temperature":"29"},{"weather_code":"02","time":"201906191300","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"阴","temperature":"29"},{"weather_code":"02","time":"201906191400","area":"北京","wind_direction":"东南风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"阴","temperature":"30"}]}
             */

            var showapi_res_error: String? = null
            var showapi_res_id: String? = null
            var showapi_res_code: Int = 0
            var showapi_res_body: ShowapiResBodyBean? = null

            class ShowapiResBodyBean {
                /**
                 * ret_code : 0
                 * area : 北京
                 * showapi_fee_code : -1
                 * areaid : 101010100
                 * hourList : [{"weather_code":"04","time":"201906181500","area":"北京","wind_direction":"东南风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"雷阵雨","temperature":"31"},{"weather_code":"04","time":"201906181600","area":"北京","wind_direction":"东南风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"雷阵雨","temperature":"31"},{"weather_code":"04","time":"201906181700","area":"北京","wind_direction":"南风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"雷阵雨","temperature":"31"},{"weather_code":"04","time":"201906181800","area":"北京","wind_direction":"东南风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"雷阵雨","temperature":"30"},{"weather_code":"04","time":"201906181900","area":"北京","wind_direction":"东南风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"雷阵雨","temperature":"28"},{"weather_code":"04","time":"201906182000","area":"北京","wind_direction":"南风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"雷阵雨","temperature":"27"},{"weather_code":"01","time":"201906182100","area":"北京","wind_direction":"东南风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"多云","temperature":"26"},{"weather_code":"01","time":"201906182200","area":"北京","wind_direction":"东南风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"多云","temperature":"26"},{"weather_code":"02","time":"201906182300","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"阴","temperature":"25"},{"weather_code":"01","time":"201906190000","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"多云","temperature":"25"},{"weather_code":"01","time":"201906190100","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"多云","temperature":"24"},{"weather_code":"01","time":"201906190200","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"多云","temperature":"23"},{"weather_code":"01","time":"201906190300","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"多云","temperature":"23"},{"weather_code":"01","time":"201906190400","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"多云","temperature":"22"},{"weather_code":"02","time":"201906190500","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"阴","temperature":"22"},{"weather_code":"04","time":"201906190600","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"雷阵雨","temperature":"23"},{"weather_code":"04","time":"201906190700","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"雷阵雨","temperature":"24"},{"weather_code":"04","time":"201906190800","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"雷阵雨","temperature":"25"},{"weather_code":"02","time":"201906190900","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"阴","temperature":"26"},{"weather_code":"02","time":"201906191000","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"阴","temperature":"27"},{"weather_code":"01","time":"201906191100","area":"北京","wind_direction":"东南风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"多云","temperature":"28"},{"weather_code":"02","time":"201906191200","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"阴","temperature":"29"},{"weather_code":"02","time":"201906191300","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"阴","temperature":"29"},{"weather_code":"02","time":"201906191400","area":"北京","wind_direction":"东南风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"阴","temperature":"30"}]
                 */

                var ret_code: Int = 0
                var area: String? = null
                var showapi_fee_code: Int = 0
                var areaid: String? = null
                var hourList: List<HourListBean>? = null

                class HourListBean {
                    /**
                     * weather_code : 04
                     * time : 201906181500
                     * area : 北京
                     * wind_direction : 东南风
                     * wind_power : 0-3级 微风  <5.4m/s
                     * areaid : 101010100
                     * weather : 雷阵雨
                     * temperature : 31
                     */

                    var weather_code: String? = null
                        get() = String.format("http://appimg.showapi.com/images/weather/icon/day/%s.png", field)
                    var time: String? = null
                    var area: String? = null
                    var wind_direction: String? = null
                    var wind_power: String? = null
                    var areaid: String? = null
                    var weather: String? = null
                    var temperature: String? = null
                }
            }
        }
    }

    //    public ArrayList<Weather> data;
    //
    //    @Table("table_message")
    //    public class Weather {
    //        @PrimaryKey(AssignType.AUTO_INCREMENT)
    //        private int _id;
    //
    //        public String weather;
    //        public String temperature;
    //        public String time;
    //        public String wind_direction;
    //        public String wind_power;

    //    }


}
