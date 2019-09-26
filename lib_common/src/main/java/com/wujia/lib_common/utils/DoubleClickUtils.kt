package com.wujia.lib_common.utils

/**
 * Author: shenbingkai
 * CreateDate: 2019-04-06 00:51
 * Description:
 */

object DoubleClickUtils {
    private var lastClickTime: Long = 0
    private val SPACE_TIME = 500

    val isDoubleClick: Boolean
        @Synchronized get() {
            val currentTime = System.currentTimeMillis()
            val isDoubleClick: Boolean
            isDoubleClick = currentTime - lastClickTime <= SPACE_TIME
            lastClickTime = currentTime
            return isDoubleClick
        }
}
