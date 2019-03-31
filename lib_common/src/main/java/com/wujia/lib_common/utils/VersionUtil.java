package com.wujia.lib_common.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-31
 * description ：
 */
public class VersionUtil {

//    public static int VERCODE = Integer.MAX_VALUE;
//    public static String VERNAME = "1.0";

    /**
     * @return 当前版本号
     * @Description: TODO 获取当前版本信息
     */
    public static int getVersionCode() {
        // 获取packagemanager的实例
        PackageManager packageManager = AppContext.get().getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo;
        int versionCode = 0;
        try {
            packInfo = packageManager.getPackageInfo(AppContext.get().getPackageName(), 0);
            versionCode = packInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        LogUtil.i("versionCode " + versionCode);
        return versionCode;
    }

    /**
     * @return 当前版本号
     * @Description: TODO 获取当前版本号
     */
    public static String getVersionName() {
        // 获取packagemanager的实例
        PackageManager packageManager = AppContext.get().getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo;
        String versionName = "";
        try {
            packInfo = packageManager.getPackageInfo(AppContext.get().getPackageName(), 0);
            versionName = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }


}
