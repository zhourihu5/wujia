package com.wujia.lib_common.utils;

import android.text.TextUtils;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-27
 * description ： 验证
 */
public class VerifyUtil {

    public static boolean isPhone(String num) {

        return !TextUtils.isEmpty(num) && num.length() == 11;

    }
}
