package com.wujia.lib_common.utils

import android.content.pm.PackageInfo
import android.content.pm.PackageManager

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-31
 * description ：
 */
object VersionUtil {

    //    public static int VERCODE = Integer.MAX_VALUE;
    //    public static String VERNAME = "1.0";

    /**
     * @return 当前版本号
     * @Description: TODO 获取当前版本信息
     */
    // 获取packagemanager的实例
    // getPackageName()是你当前类的包名，0代表是获取版本信息
    val versionCode: Int
        get() {
            val packageManager = AppContext.get().packageManager
            val packInfo: PackageInfo
            var versionCode = 0
            try {
                packInfo = packageManager.getPackageInfo(AppContext.get().packageName, 0)
                versionCode = packInfo.versionCode
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            LogUtil.i("versionCode $versionCode")
            return versionCode
        }

    /**
     * @return 当前版本号
     * @Description: TODO 获取当前版本号
     */
    // 获取packagemanager的实例
    // getPackageName()是你当前类的包名，0代表是获取版本信息
    val versionName: String
        get() {
            val packageManager = AppContext.get().packageManager
            val packInfo: PackageInfo
            var versionName = ""
            try {
                packInfo = packageManager.getPackageInfo(AppContext.get().packageName, 0)
                versionName = packInfo.versionName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            return versionName
        }


}
