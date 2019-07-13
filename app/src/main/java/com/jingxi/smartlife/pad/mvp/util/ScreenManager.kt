package com.jingxi.smartlife.pad.mvp.util

import android.provider.Settings

import com.wujia.lib_common.utils.AppContext

/**
 * Created by asus on 2016/12/8.
 * 屏幕亮度调节器
 */
object ScreenManager {
    const val LIGHT_MODE_AUTO = 1
    const val LIGHT_MODE_MANUAL = 0
    const val LIGHT_MODE_FAILED = -1


    /**
     * 获得当前屏幕亮度的模式
     *
     * @return 1 为自动调节屏幕亮度,0 为手动调节屏幕亮度,-1 获取失败
     */
    /**
     * 设置当前屏幕亮度的模式
     *
     * @param mode 1 为自动调节屏幕亮度,0 为手动调节屏幕亮度
     */

    var screenMode: Int
        @LightMode
        get() {
            var mode = LIGHT_MODE_FAILED
            try {
                mode = Settings.System.getInt(AppContext.get().contentResolver,
                        Settings.System.SCREEN_BRIGHTNESS_MODE)
            } catch (e: Settings.SettingNotFoundException) {
                e.printStackTrace()
            }

            return mode
        }
        set(@LightMode mode) = try {
            Settings.System.putInt(AppContext.get().contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE, mode)
            val uri = Settings.System
                    .getUriFor("screen_brightness_mode")
            AppContext.get().contentResolver.notifyChange(uri, null)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    /**
     * 获得当前屏幕亮度值
     *
     * @return 0--255
     */
    /**
     * 保存当前的屏幕亮度值，并使之生效
     *
     * @param paramInt 0-255
     */
    var screenBrightness: Int
        get() {
            var screenBrightness = -1
            try {
                screenBrightness = Settings.System.getInt(AppContext.get().contentResolver,
                        Settings.System.SCREEN_BRIGHTNESS)
            } catch (e: Settings.SettingNotFoundException) {
                e.printStackTrace()
            }

            return screenBrightness
        }
        set(paramInt) {
            Settings.System.putInt(AppContext.get().contentResolver,
                    Settings.System.SCREEN_BRIGHTNESS, paramInt)
            val uri = Settings.System
                    .getUriFor("screen_brightness")
            AppContext.get().contentResolver.notifyChange(uri, null)
        }

}

