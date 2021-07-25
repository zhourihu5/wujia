package com.wujia.lib_common.utils

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.PopupWindow
import kotlin.math.max
import kotlin.math.min

object ScreenUtil {

    private const val RATIO = 0.85

    private var screenWidth: Int = 0
    var screenHeight: Int = 0
    private var screenMin: Int = 0// 宽高中，小的一边
    private var screenMax: Int = 0// 宽高中，较大的值

    var density: Float = 0.toFloat()
    var scaleDensity: Float = 0.toFloat()
    private var xdpi: Float = 0.toFloat()
    private var ydpi: Float = 0.toFloat()
    var densityDpi: Int = 0

    val dialogWidth: Int
        get(){
        return  (screenMin * RATIO).toInt()
    }
    var statusbarheight: Int = 0
    private var navbarheight: Int = 0

    val landscapeWidth: Int
        get() {
            if (screenMax == 0)
                GetInfo(AppContext.get())
            return screenMax
        }

    val landscapeHeight: Int
        get() {
            if (screenMin == 0)
                GetInfo(AppContext.get())
            return screenMin
        }

    val displayHeight: Int
        get() {
            if (screenHeight == 0) {
                GetInfo(AppContext.get())
            }
            return screenHeight
        }

    init {
        init(AppContext.get())
    }

    fun dip2px(dipValue: Float): Int {
        return (dipValue * density + 0.5f).toInt()
    }

    fun px2dip(pxValue: Float): Int {
        return (pxValue / density + 0.5f).toInt()
    }


    private fun init(context: Context?) {
        if (null == context) {
            return
        }
        val dm = context.applicationContext.resources.displayMetrics
        screenWidth = dm.widthPixels
        screenHeight = dm.heightPixels
        screenMin = min(screenWidth, screenHeight)
        screenMax = max(screenWidth, screenHeight)
        density = dm.density
        scaleDensity = dm.scaledDensity
        xdpi = dm.xdpi
        ydpi = dm.ydpi
        densityDpi = dm.densityDpi

        LogUtil.d("screenWidth=$screenWidth screenHeight=$screenHeight density=$density")
    }

    private fun GetInfo(context: Context?) {
        if (null == context) {
            return
        }
        val dm = context.applicationContext.resources.displayMetrics
        screenWidth = dm.widthPixels
        screenHeight = dm.heightPixels
        screenMin = min(screenWidth, screenHeight)
        screenMax = max(screenWidth, screenHeight)
        density = dm.density
        scaleDensity = dm.scaledDensity
        xdpi = dm.xdpi
        ydpi = dm.ydpi
        densityDpi = dm.densityDpi
        statusbarheight = getStatusBarHeight(context)
        navbarheight = getNavBarHeight(context)
        LogUtil.d("screenWidth=$screenWidth screenHeight=$screenHeight density=$density")
    }

    private fun getStatusBarHeight(context: Context): Int {
        if (statusbarheight == 0) {
            try {
                val c = Class.forName("com.android.internal.R\$dimen")
                val o = c.newInstance()
                val field = c.getField("status_bar_height")
                val x = field.get(o) as Int
                statusbarheight = context.resources.getDimensionPixelSize(x)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        if (statusbarheight == 0) {
            statusbarheight = dip2px(25f)
        }
        return statusbarheight
    }

    private fun getNavBarHeight(context: Context): Int {
        val resources = context.resources
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
        } else 0
    }

}
