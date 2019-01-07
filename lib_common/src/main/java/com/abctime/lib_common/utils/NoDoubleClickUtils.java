package com.abctime.lib_common.utils;

/**
 * author:Created by xmren on 2018/7/23.
 * email :renxiaomin@100tal.com
 */

public class NoDoubleClickUtils {
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
