package com.wujia.lib_common.utils

import android.content.Context
import androidx.core.content.ContextCompat

object ContextUtil {
    fun getColor(context: Context, resId: Int): Int {
        return ContextCompat.getColor(context, resId)
    }
}
