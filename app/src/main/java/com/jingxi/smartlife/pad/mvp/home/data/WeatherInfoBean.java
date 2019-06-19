package com.jingxi.smartlife.pad.mvp.home.data;

import com.wujia.businesslib.data.RootResponse;

import java.util.List;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-27
 * description ：
 */
public class WeatherInfoBean extends RootResponse {
    /**
     * code : 0
     * data : {"restrict":{"createDate":"2019-06-18T06:38:08.579Z","id":0,"num":"string"},"token":"string","weather":{}}
     */

//    @SerializedName("code")
//    private int codeX;
    private DataBean data;

//    public int getCodeX() {
//        return codeX;
//    }
//
//    public void setCodeX(int codeX) {
//        this.codeX = codeX;
//    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * restrict : {"createDate":"2019-06-18T06:38:08.579Z","id":0,"num":"string"}
         * token : string
         * weather : {}
         */

        private RestrictBean restrict;
        private String token;
        private WeatherBean weather;

        public RestrictBean getRestrict() {
            return restrict;
        }

        public void setRestrict(RestrictBean restrict) {
            this.restrict = restrict;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public WeatherBean getWeather() {
            return weather;
        }

        public void setWeather(WeatherBean weather) {
            this.weather = weather;
        }

        public static class RestrictBean {
            /**
             * createDate : 2019-06-18T06:38:08.579Z
             * id : 0
             * num : string
             */

            private String createDate;
            private int id;
            private String num;

            public String getCreateDate() {
                return createDate;
            }

            public void setCreateDate(String createDate) {
                this.createDate = createDate;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getNum() {
                return num;
            }

            public void setNum(String num) {
                this.num = num;
            }
        }

        public static class WeatherBean {

            /**
             * showapi_res_error :
             * showapi_res_id : 331bbc3e1be44ddd93b15a0ddf846864
             * showapi_res_code : 0
             * showapi_res_body : {"ret_code":0,"area":"北京","showapi_fee_code":-1,"areaid":"101010100","hourList":[{"weather_code":"04","time":"201906181500","area":"北京","wind_direction":"东南风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"雷阵雨","temperature":"31"},{"weather_code":"04","time":"201906181600","area":"北京","wind_direction":"东南风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"雷阵雨","temperature":"31"},{"weather_code":"04","time":"201906181700","area":"北京","wind_direction":"南风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"雷阵雨","temperature":"31"},{"weather_code":"04","time":"201906181800","area":"北京","wind_direction":"东南风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"雷阵雨","temperature":"30"},{"weather_code":"04","time":"201906181900","area":"北京","wind_direction":"东南风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"雷阵雨","temperature":"28"},{"weather_code":"04","time":"201906182000","area":"北京","wind_direction":"南风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"雷阵雨","temperature":"27"},{"weather_code":"01","time":"201906182100","area":"北京","wind_direction":"东南风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"多云","temperature":"26"},{"weather_code":"01","time":"201906182200","area":"北京","wind_direction":"东南风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"多云","temperature":"26"},{"weather_code":"02","time":"201906182300","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"阴","temperature":"25"},{"weather_code":"01","time":"201906190000","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"多云","temperature":"25"},{"weather_code":"01","time":"201906190100","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"多云","temperature":"24"},{"weather_code":"01","time":"201906190200","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"多云","temperature":"23"},{"weather_code":"01","time":"201906190300","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"多云","temperature":"23"},{"weather_code":"01","time":"201906190400","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"多云","temperature":"22"},{"weather_code":"02","time":"201906190500","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"阴","temperature":"22"},{"weather_code":"04","time":"201906190600","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"雷阵雨","temperature":"23"},{"weather_code":"04","time":"201906190700","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"雷阵雨","temperature":"24"},{"weather_code":"04","time":"201906190800","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"雷阵雨","temperature":"25"},{"weather_code":"02","time":"201906190900","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"阴","temperature":"26"},{"weather_code":"02","time":"201906191000","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"阴","temperature":"27"},{"weather_code":"01","time":"201906191100","area":"北京","wind_direction":"东南风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"多云","temperature":"28"},{"weather_code":"02","time":"201906191200","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"阴","temperature":"29"},{"weather_code":"02","time":"201906191300","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"阴","temperature":"29"},{"weather_code":"02","time":"201906191400","area":"北京","wind_direction":"东南风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"阴","temperature":"30"}]}
             */

            private String showapi_res_error;
            private String showapi_res_id;
            private int showapi_res_code;
            private ShowapiResBodyBean showapi_res_body;

            public String getShowapi_res_error() {
                return showapi_res_error;
            }

            public void setShowapi_res_error(String showapi_res_error) {
                this.showapi_res_error = showapi_res_error;
            }

            public String getShowapi_res_id() {
                return showapi_res_id;
            }

            public void setShowapi_res_id(String showapi_res_id) {
                this.showapi_res_id = showapi_res_id;
            }

            public int getShowapi_res_code() {
                return showapi_res_code;
            }

            public void setShowapi_res_code(int showapi_res_code) {
                this.showapi_res_code = showapi_res_code;
            }

            public ShowapiResBodyBean getShowapi_res_body() {
                return showapi_res_body;
            }

            public void setShowapi_res_body(ShowapiResBodyBean showapi_res_body) {
                this.showapi_res_body = showapi_res_body;
            }

            public static class ShowapiResBodyBean {
                /**
                 * ret_code : 0
                 * area : 北京
                 * showapi_fee_code : -1
                 * areaid : 101010100
                 * hourList : [{"weather_code":"04","time":"201906181500","area":"北京","wind_direction":"东南风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"雷阵雨","temperature":"31"},{"weather_code":"04","time":"201906181600","area":"北京","wind_direction":"东南风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"雷阵雨","temperature":"31"},{"weather_code":"04","time":"201906181700","area":"北京","wind_direction":"南风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"雷阵雨","temperature":"31"},{"weather_code":"04","time":"201906181800","area":"北京","wind_direction":"东南风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"雷阵雨","temperature":"30"},{"weather_code":"04","time":"201906181900","area":"北京","wind_direction":"东南风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"雷阵雨","temperature":"28"},{"weather_code":"04","time":"201906182000","area":"北京","wind_direction":"南风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"雷阵雨","temperature":"27"},{"weather_code":"01","time":"201906182100","area":"北京","wind_direction":"东南风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"多云","temperature":"26"},{"weather_code":"01","time":"201906182200","area":"北京","wind_direction":"东南风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"多云","temperature":"26"},{"weather_code":"02","time":"201906182300","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"阴","temperature":"25"},{"weather_code":"01","time":"201906190000","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"多云","temperature":"25"},{"weather_code":"01","time":"201906190100","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"多云","temperature":"24"},{"weather_code":"01","time":"201906190200","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"多云","temperature":"23"},{"weather_code":"01","time":"201906190300","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"多云","temperature":"23"},{"weather_code":"01","time":"201906190400","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"多云","temperature":"22"},{"weather_code":"02","time":"201906190500","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"阴","temperature":"22"},{"weather_code":"04","time":"201906190600","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"雷阵雨","temperature":"23"},{"weather_code":"04","time":"201906190700","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"雷阵雨","temperature":"24"},{"weather_code":"04","time":"201906190800","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"雷阵雨","temperature":"25"},{"weather_code":"02","time":"201906190900","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"阴","temperature":"26"},{"weather_code":"02","time":"201906191000","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"阴","temperature":"27"},{"weather_code":"01","time":"201906191100","area":"北京","wind_direction":"东南风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"多云","temperature":"28"},{"weather_code":"02","time":"201906191200","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"阴","temperature":"29"},{"weather_code":"02","time":"201906191300","area":"北京","wind_direction":"东风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"阴","temperature":"29"},{"weather_code":"02","time":"201906191400","area":"北京","wind_direction":"东南风","wind_power":"0-3级 微风  <5.4m/s","areaid":"101010100","weather":"阴","temperature":"30"}]
                 */

                private int ret_code;
                private String area;
                private int showapi_fee_code;
                private String areaid;
                private List<HourListBean> hourList;

                public int getRet_code() {
                    return ret_code;
                }

                public void setRet_code(int ret_code) {
                    this.ret_code = ret_code;
                }

                public String getArea() {
                    return area;
                }

                public void setArea(String area) {
                    this.area = area;
                }

                public int getShowapi_fee_code() {
                    return showapi_fee_code;
                }

                public void setShowapi_fee_code(int showapi_fee_code) {
                    this.showapi_fee_code = showapi_fee_code;
                }

                public String getAreaid() {
                    return areaid;
                }

                public void setAreaid(String areaid) {
                    this.areaid = areaid;
                }

                public List<HourListBean> getHourList() {
                    return hourList;
                }

                public void setHourList(List<HourListBean> hourList) {
                    this.hourList = hourList;
                }

                public static class HourListBean {
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

                    private String weather_code;
                    private String time;
                    private String area;
                    private String wind_direction;
                    private String wind_power;
                    private String areaid;
                    private String weather;
                    private String temperature;

                    public String getWeather_code() {
                        return weather_code;
                    }

                    public void setWeather_code(String weather_code) {
                        this.weather_code = weather_code;
                    }

                    public String getTime() {
                        return time;
                    }

                    public void setTime(String time) {
                        this.time = time;
                    }

                    public String getArea() {
                        return area;
                    }

                    public void setArea(String area) {
                        this.area = area;
                    }

                    public String getWind_direction() {
                        return wind_direction;
                    }

                    public void setWind_direction(String wind_direction) {
                        this.wind_direction = wind_direction;
                    }

                    public String getWind_power() {
                        return wind_power;
                    }

                    public void setWind_power(String wind_power) {
                        this.wind_power = wind_power;
                    }

                    public String getAreaid() {
                        return areaid;
                    }

                    public void setAreaid(String areaid) {
                        this.areaid = areaid;
                    }

                    public String getWeather() {
                        return weather;
                    }

                    public void setWeather(String weather) {
                        this.weather = weather;
                    }

                    public String getTemperature() {
                        return temperature;
                    }

                    public void setTemperature(String temperature) {
                        this.temperature = temperature;
                    }
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
