package com.wujia.lib_common.utils;

import android.content.Context;
import androidx.core.content.ContextCompat;

public class ContextUtil {
    public static int getColor(Context context, int resId) {
        return ContextCompat.getColor(context, resId);
    }
}
