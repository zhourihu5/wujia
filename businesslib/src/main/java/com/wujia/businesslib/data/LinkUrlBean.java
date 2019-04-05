package com.wujia.businesslib.data;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-29
 * description ：
 */
public class LinkUrlBean {

    public static final String CODE_TYPE_MARKET = "serviceMarket";//服务市场
    public static final String CODE_TYPE_PROPERTY = "realtyService";//物业服务

//  二级  {"code":"realtyService","name":"物业服务","children":{"code":"3","name":"家政服务","children":{}}}
//  三级  {"code":"realtyService","name":"物业服务","children":{"code":"report","name":"报事报修","children":{"code":"4","name":"搬家服务"}}}

    public String code;
    public String name;
    public Children1 children;


    public static class Children1 {
        public static final String CODE_TYPE_FIND = "discover";//发现
        public static final String CODE_TYPE_GOV = "goverment";//政务
        public static final String CODE_TYPE_ALL = "all";//全部

        public static final String CODE_TYPE_REPORT = "report";//报事报修
        public static final String CODE_TYPE_MONEY = "money";//物业缴费
        public static final String CODE_TYPE_ORDER = "order";//订单管理
        public static final String CODE_TYPE_PHONE = "phone";//电话查询

        public String code;
        public String name;
        public Children2 children;

    }

    public static class Children2 {
        public String code;
        public String name;


    }
}
