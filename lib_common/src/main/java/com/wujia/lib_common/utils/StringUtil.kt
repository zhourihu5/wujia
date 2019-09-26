package com.wujia.lib_common.utils

import java.util.Locale

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-27
 * description ：
 */
object StringUtil {


    fun format(format: String, vararg args: Any): String {
        return String.format(Locale.CHINA, format, *args)
    }
}
