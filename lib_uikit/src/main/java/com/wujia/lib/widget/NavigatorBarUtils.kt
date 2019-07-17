package com.wujia.lib.widget

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.util.DisplayMetrics
import android.view.Display
import android.view.ViewConfiguration

import java.lang.reflect.Method

/**
 * Created by lishaowei on 2018/4/25.
 */

object NavigatorBarUtils {

    /**
     * 判断虚拟按键栏是否重写
     *
     * @return
     */
    private val navBarOverride: String?
        get() {
            var sNavBarOverride: String? = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                try {
                    val c = Class.forName("android.os.SystemProperties")
                    val m = c.getDeclaredMethod("get", String::class.java)
                    m.isAccessible = true
                    sNavBarOverride = m.invoke(null, "qemu.hw.mainkeys") as String
                } catch (e: Throwable) {
                }

            }
            return sNavBarOverride
        }

    fun getNavigatorBarHeight(activity: Activity): Int {
        try {
            return getRealWindosHeight(activity) - getWindosHeight(activity)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return 0
    }


    /**
     * 获取屏幕尺寸，但是不包括虚拟功能高度
     *
     * @return
     */
    private fun getWindosHeight(activity: Activity): Int {
        return activity.windowManager.defaultDisplay.height
    }

    private fun getRealWindosHeight(activity: Activity): Int {
        var dpi = 0
        val display = activity.windowManager.defaultDisplay
        val dm = DisplayMetrics()
        val c: Class<*>
        try {
            c = Class.forName("android.view.Display")
            val method = c.getMethod("getRealMetrics", DisplayMetrics::class.java)
            method.invoke(display, dm)
            dpi = dm.heightPixels
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return dpi
    }

    //获取虚拟按键的高度
    fun getNavigationBarHeight(context: Context): Int {
        var result = 0
        if (hasNavBar(context)) {
            val res = context.resources
            val resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId)
            }
        }
        return result
    }

    /**
     * 检查是否存在虚拟按键栏
     *
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    fun hasNavBar(context: Context): Boolean {
        val res = context.resources
        val resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android")
        if (resourceId != 0) {
            var hasNav = res.getBoolean(resourceId)
            // check override flag
            val sNavBarOverride = navBarOverride
            if ("1" == sNavBarOverride) {
                hasNav = false
            } else if ("0" == sNavBarOverride) {
                hasNav = true
            }
            return hasNav
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey()
        }
    }

}
