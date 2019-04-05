package com.wujia.lib_common.utils;

/**
 * Author: shenbingkai
 * CreateDate: 2019-04-06 00:51
 * Description:
 */

public class DoubleClickUtils {
    private static long lastClickTime = 0;
    private final static int SPACE_TIME = 500;

    public synchronized static boolean isDoubleClick() {
        long currentTime = System.currentTimeMillis();
        boolean isDoubleClick;
        if (currentTime - lastClickTime >
                SPACE_TIME) {
            isDoubleClick = false;
        } else {
            isDoubleClick = true;
        }
        lastClickTime = currentTime;
        return isDoubleClick;
    }
}
