package com.wujia.lib_common.utils

import android.text.TextUtils

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-27
 * description ： 验证
 */
object VerifyUtil {

    fun isPhone(num: String): Boolean {

        return !TextUtils.isEmpty(num) && num.length == 11

    }
}
