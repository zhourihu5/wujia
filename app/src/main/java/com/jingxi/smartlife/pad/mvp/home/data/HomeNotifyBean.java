package com.jingxi.smartlife.pad.mvp.home.data;

import com.wujia.businesslib.data.Advert;

/**
 * Author: created by shenbingkai on 2019/2/11 00 19
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class HomeNotifyBean {

//    {"type":"propertyMessage","propertyMessage":11}

    public static final String TYPE_PROPERTY = "propertyMessage";
    public static final String TYPE_NOTIFY = "noticeMessage";
    public static final String TYPE_HOME_CARD = "901";
    public static final String TYPE_HOME_AD = "303";

    public String type;
    public String propertyMessage;

    public Advert adLeapInfo;
}
