package com.wujia.lib_common.utils

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import android.widget.PopupWindow

object ScreenUtil {
    private val TAG = "ScreenUtil"

    private val RATIO = 0.85

    var screenWidth: Int = 0
    var screenHeight: Int = 0
    var screenMin: Int = 0// 宽高中，小的一边
    var screenMax: Int = 0// 宽高中，较大的值

    var density: Float = 0.toFloat()
    var scaleDensity: Float = 0.toFloat()
    var xdpi: Float = 0.toFloat()
    var ydpi: Float = 0.toFloat()
    var densityDpi: Int = 0

    var dialogWidth: Int = 0
    get(){
        return  (screenMin * RATIO).toInt()
    }
    var statusbarheight: Int = 0
    var navbarheight: Int = 0

    val displayWidth: Int
        get() {
            if (screenWidth == 0) {
                GetInfo(AppContext.get())
            }
            return screenWidth
        }

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

    fun sp2px(spValue: Float): Int {
        return (spValue * scaleDensity + 0.5f).toInt()
    }


    private fun init(context: Context?) {
        if (null == context) {
            return
        }
        val dm = context.applicationContext.resources.displayMetrics
        screenWidth = dm.widthPixels
        screenHeight = dm.heightPixels
        screenMin = Math.min(screenWidth, screenHeight)
        screenMax = Math.max(screenWidth, screenHeight)
        density = dm.density
        scaleDensity = dm.scaledDensity
        xdpi = dm.xdpi
        ydpi = dm.ydpi
        densityDpi = dm.densityDpi

        LogUtil.d("screenWidth=$screenWidth screenHeight=$screenHeight density=$density")
    }

    fun GetInfo(context: Context?) {
        if (null == context) {
            return
        }
        val dm = context.applicationContext.resources.displayMetrics
        screenWidth = dm.widthPixels
        screenHeight = dm.heightPixels
        screenMin = Math.min(screenWidth, screenHeight)
        screenMax = Math.max(screenWidth, screenHeight)
        density = dm.density
        scaleDensity = dm.scaledDensity
        xdpi = dm.xdpi
        ydpi = dm.ydpi
        densityDpi = dm.densityDpi
        statusbarheight = getStatusBarHeight(context)
        navbarheight = getNavBarHeight(context)
        LogUtil.d("screenWidth=$screenWidth screenHeight=$screenHeight density=$density")
    }

    fun getStatusBarHeight(context: Context): Int {
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
            statusbarheight = ScreenUtil.dip2px(25f)
        }
        return statusbarheight
    }

    fun getNavBarHeight(context: Context): Int {
        val resources = context.resources
        val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resources.getDimensionPixelSize(resourceId)
        } else 0
    }

    /**
     * 设置dialog大小占屏幕的宽高
     *
     * @param widthRatio  宽度比例
     * @param heightRatio 高度比例
     * @param isTrue      高度和宽度是否相等
     * @param type        -> 1: 和宽度相等
     * -> 2: 和高度相等
     */
    fun setWindowDisplay(mDialog: Dialog, mContext: Context, widthRatio: Double, heightRatio: Double, isTrue: Boolean, type: Int) {
        val dialogWindow = mDialog.window
        val params = dialogWindow!!.attributes
        val metrics = mContext.resources.displayMetrics // 获取屏幕宽、高

        if (isTrue) {
            if (type == 1) {
                params.width = (metrics.widthPixels * widthRatio).toInt()
                params.height = (metrics.widthPixels * widthRatio).toInt()
            } else {
                params.width = (metrics.heightPixels * heightRatio).toInt()
                params.height = (metrics.heightPixels * heightRatio).toInt()
            }
        } else {
            params.width = (metrics.widthPixels * widthRatio).toInt()
            params.height = (metrics.heightPixels * heightRatio).toInt()
        }

        dialogWindow.attributes = params
    }

    /**
     * 设置dialog大小占屏幕的宽高
     *
     * @param widthRatio  宽度比例
     * @param heightRatio 高度比例
     */
    fun setWindowDisplay(mWindows: PopupWindow, mContext: Context, widthRatio: Double, heightRatio: Double) {
        val metrics = mContext.resources.displayMetrics // 获取屏幕宽、高
        mWindows.width = (metrics.widthPixels * widthRatio).toInt()
        mWindows.height = (metrics.heightPixels * heightRatio).toInt()
    }

    fun getRealScreenWidth(context: Context): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            val dm = DisplayMetrics()
            //获取的像素高度不包含虚拟键所占空间
            (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
                    .getRealMetrics(dm)
            return Math.max(dm.widthPixels, dm.heightPixels)
        } else {
            return landscapeWidth
        }
    }
}
