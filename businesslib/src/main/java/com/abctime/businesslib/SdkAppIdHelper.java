package com.abctime.businesslib;

/**
 * description: 第三方sdk接入，测试版处理
 * author: KisenHuang
 * email: KisenHuang@163.com
 * time: 2018/8/14 下午2:11
 */

public class SdkAppIdHelper {

    public static String TALKINGDATA_APPID;

    public static void setup(boolean isDebug) {
        TALKINGDATA_APPID = isDebug ? Constants.TALKINGDATA_DEBUG_APPID : Constants.TALKINGDATA_APPID;
    }
}
