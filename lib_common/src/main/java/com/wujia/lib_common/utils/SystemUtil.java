package com.wujia.lib_common.utils;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-26
 * description ：
 */
public class SystemUtil {

    private static String serialNum;


    public static void init() {
        serialNum = android.os.Build.SERIAL;

    }

    public static String getSerialNum() {
        return serialNum;
//        return "HS1JXY6M12D2900034";
    }
}
