package com.abctime.businesslib.wechat.bean;

import java.io.Serializable;

/**
 * Created by four on 2018/7/4.
 */

public class WeChatPaymentBean implements Serializable {

    public String appId = "";

    public String partnerId = "";

    public String prepayId = "";

    public String packageValue = "";

    public String nonceStr = "";

    public String timeStamp = "";

    public String sign = "";
}
