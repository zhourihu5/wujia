package com.jingxi.smartlife.pad.mvp.util;

import android.net.Uri;
import android.provider.Settings;

import androidx.annotation.IntDef;

import com.wujia.lib_common.utils.AppContext;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by asus on 2016/12/8.
 * 屏幕亮度调节器
 */
public class ScreenManager {

    @Retention(SOURCE)
    @IntDef({LIGHT_MODE_AUTO, LIGHT_MODE_MANUAL, })
    public @interface LightMode {}
    public static final int LIGHT_MODE_AUTO = 0;
    public static final int LIGHT_MODE_MANUAL = 1;


    /**
     * 获得当前屏幕亮度的模式
     *
     * @return 1 为自动调节屏幕亮度,0 为手动调节屏幕亮度,-1 获取失败
     */
    @LightMode
    public static int getScreenMode() {
        int mode = -1;
        try {
            mode = Settings.System.getInt(AppContext.get().getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return mode;
    }

    /**
     * 获得当前屏幕亮度值
     *
     * @return 0--255
     */
    public static int getScreenBrightness() {
        int screenBrightness = -1;
        try {
            screenBrightness = Settings.System.getInt(AppContext.get().getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return screenBrightness;
    }

    /**
     * 设置当前屏幕亮度的模式
     *
     * @param mode 1 为自动调节屏幕亮度,0 为手动调节屏幕亮度
     */

    public static void setScreenMode(@LightMode int mode) {
        try {
            Settings.System.putInt(AppContext.get().getContentResolver(),
                    Settings.System.SCREEN_BRIGHTNESS_MODE, mode);
            Uri uri = Settings.System
                    .getUriFor("screen_brightness_mode");
            AppContext.get().getContentResolver().notifyChange(uri, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存当前的屏幕亮度值，并使之生效
     *
     * @param paramInt 0-255
     */
    public static void setScreenBrightness(int paramInt) {
        Settings.System.putInt(AppContext.get().getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS, paramInt);
        Uri uri = Settings.System
                .getUriFor("screen_brightness");
        AppContext.get().getContentResolver().notifyChange(uri, null);
    }
}

