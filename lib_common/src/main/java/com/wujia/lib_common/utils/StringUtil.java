package com.wujia.lib_common.utils;

import java.util.Locale;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-27
 * description ：
 */
public class StringUtil {


    public static String format(String format, Object... args) {
        return String.format(Locale.CHINA, format, args);
    }
}
