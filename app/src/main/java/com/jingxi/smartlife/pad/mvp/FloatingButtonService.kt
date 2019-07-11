package com.jingxi.smartlife.pad.mvp

import android.app.ActivityManager
import android.app.Service
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import android.view.GestureDetector
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager

import com.jingxi.smartlife.pad.R
import com.wujia.lib_common.utils.AppUtil
import com.wujia.lib_common.utils.LogUtil

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.reflect.Method

/**
 * Created by dongzhong on 2018/5/30.
 */

class FloatingButtonService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private var windowManager: WindowManager? = null
    private var layoutParams: WindowManager.LayoutParams? = null

    private var floatingView: View? = null
    private var startTime: Long = 0
    internal var isAdded = false

    internal val topActivityPackage: String
        get() {
            var topActivity = ""
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val m = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
                if (m != null) {
                    val now = System.currentTimeMillis()
                    val stats = m.queryUsageStats(UsageStatsManager.INTERVAL_BEST, startTime - 50 * 1000, now)
                    LogUtil.info(TAG, "Running app number in last 60 seconds : " + stats!!.size)
                    if (stats != null && !stats.isEmpty()) {
                        var j = 0
                        for (i in stats.indices) {
                            if (stats[i].lastTimeUsed > stats[j].lastTimeUsed) {
                                j = i
                            }
                            topActivity = stats[j].packageName
                        }

                    } else {

                    }
                }
            } else {
                val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
                val cn = activityManager.getRunningTasks(1)[0].topActivity
                topActivity = cn.packageName
            }
            LogUtil.info(TAG, "top running app is : $topActivity")
            return topActivity
        }

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        layoutParams = WindowManager.LayoutParams()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            layoutParams!!.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            layoutParams!!.type = WindowManager.LayoutParams.TYPE_PHONE
        }
        layoutParams!!.format = PixelFormat.RGBA_8888
        layoutParams!!.gravity = Gravity.LEFT or Gravity.TOP
        layoutParams!!.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        //        layoutParams.width = 200;
        //        layoutParams.height = 200;
        layoutParams!!.width = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams!!.height = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams!!.x = 0
        layoutParams!!.y = 0
    }

    override fun onDestroy() {
        super.onDestroy()
        removeFloatingWindow()
    }


    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        startTime = System.currentTimeMillis()
        //        isStarted = true;
        showFloatingWindow()
        val resutl = super.onStartCommand(intent, flags, startId)
        stopIfCurrentApp()
        return resutl
    }

    internal fun removeFloatingWindow() {
        if (isAdded) {
            windowManager!!.removeView(floatingView)
            isAdded = false
        }
    }

    private fun showFloatingWindow() {
        if (Settings.canDrawOverlays(this) && !isAdded) {
            floatingView = View.inflate(applicationContext, R.layout.floating_back, null)
            windowManager!!.addView(floatingView, layoutParams)

            floatingView!!.setOnTouchListener(FloatingOnTouchListener())
            isAdded = true
        }
    }

    private fun closeWPS(packageName: String) {
        stopApp(packageName)
    }


    private inner class FloatingOnTouchListener : View.OnTouchListener {
        private var x: Int = 0
        private var y: Int = 0
        internal var gestureDetector = GestureDetector(this@FloatingButtonService, object : GestureDetector.OnGestureListener {
            override fun onDown(e: MotionEvent): Boolean {
                LogUtil.i("onDown")
                return false
            }

            override fun onShowPress(e: MotionEvent) {
                LogUtil.i("onShowPress")
            }

            override fun onSingleTapUp(e: MotionEvent): Boolean {
                LogUtil.i("onSingleTapUp")
                if (stopIfCurrentApp()) return false
                closeWPS(topActivityPackage)//todo optimize it to go to the same page  before.
                return false
            }

            override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
                LogUtil.i("onScroll")
                return false
            }

            override fun onLongPress(e: MotionEvent) {
                LogUtil.i("onLongPress")
            }

            override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
                LogUtil.i("onFling")
                return false
            }
        })

        override fun onTouch(view: View, event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    x = event.rawX.toInt()
                    y = event.rawY.toInt()
                }
                MotionEvent.ACTION_MOVE -> {
                    val nowX = event.rawX.toInt()
                    val nowY = event.rawY.toInt()
                    val movedX = nowX - x
                    val movedY = nowY - y
                    x = nowX
                    y = nowY
                    layoutParams!!.x = layoutParams!!.x + movedX
                    layoutParams!!.y = layoutParams!!.y + movedY
                    windowManager!!.updateViewLayout(view, layoutParams)
                }
                else -> {
                }
            }
            gestureDetector.onTouchEvent(event)
            return false
        }
    }

    protected fun stopIfCurrentApp(): Boolean {
        if (packageName == topActivityPackage) {
            stopSelf()
            return true
        }
        return false
    }

    companion object {
        private val TAG = "FloatingButtonService"
        fun stopApp(packageName: String) {
            val command = "am force-stop $packageName all\n"
            AppUtil.execCmd(command)
        }
    }
}
