package com.wujia.lib_common.utils

import android.app.PendingIntent
import android.content.ComponentName
import android.content.Intent
import android.text.TextUtils
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-30
 * description ：
 */
object AppUtil {


    fun startAPPByPackageName(packagename: String): Boolean {

        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等 有版本兼容问题，getPackageInfo,所以不去校验
//        var packageinfo: PackageInfo? = null
//        try {
//            packageinfo = AppContext.get().packageManager.getPackageInfo(packagename, 0)
//        } catch (e: PackageManager.NameNotFoundException) {
//            e.printStackTrace()
//        }
//
//        if (packageinfo == null) {
//            return false
//        }

        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        try {
            val resolveIntent = Intent(Intent.ACTION_MAIN, null)
            resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER)
            resolveIntent.setPackage(packagename)

            // 通过getPackageManager()的queryIntentActivities方法遍历
            val resolveinfoList = AppContext.get().packageManager
                    .queryIntentActivities(resolveIntent, 0)

            val resolveinfo = resolveinfoList.iterator().next()
            if (resolveinfo != null) {
                // packagename = 参数packname
                val packageName = resolveinfo.activityInfo.packageName
                // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
                val className = resolveinfo.activityInfo.name
                // LAUNCHER Intent
                val intent = Intent(Intent.ACTION_MAIN)
                intent.addCategory(Intent.CATEGORY_LAUNCHER)
                //不保存历史记录
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
                // 设置ComponentName参数1:packagename参数2:MainActivity路径
                val cn = ComponentName(packageName, className)
                intent.component = cn
                //            Activity activity = BaseApplication.baseApplication.getLastActivity();
                //            if (activity == null || activity.isFinishing()) {
                //                return false;
                //            }
                AppContext.get().startActivity(intent)
                return true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    fun startAdbWifi(): Boolean {
        var result = execCmd("setprop service.adb.tcp.port 5555\n")
        result = result and execCmd("stop adbd\n")
        result = result and execCmd("start adbd\n")
        return result
    }

    fun execCmd(command: String): Boolean {
        LogUtil.i("execCmd  $command")
        var sderr: InputStream? = null
        var proc: Process? = null
        var isr: InputStreamReader? = null
        var br: BufferedReader? = null
        try {
            val rt = Runtime.getRuntime()
            //            String command = "pm install -r " + apkPath + "\n";
            proc = rt.exec(command)
            sderr = proc!!.errorStream
            isr = InputStreamReader(sderr)
            br = BufferedReader(isr)
//            while ((line = br.readLine()) != null) {
            while (true) {
                br.readLine()?.let { LogUtil.i("cmd line $it") }?:break
            }
            val exitVal = proc.waitFor()
            if (exitVal == 0) {
                return true
            }


        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                br?.close()
                isr?.close()
                sderr?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        return false
    }

    fun install(apkPath: String): Boolean {
        LogUtil.i("install $apkPath")
        val command = "pm install -r $apkPath\n"
        return execCmd(command)
    }

    fun uninstall(packName: String): Boolean {
        if (TextUtils.isEmpty(packName)) {
            return false
        }
        LogUtil.i("uninstall $packName")
        //        InputStream sderr = null;
        //        Process proc = null;
        //        InputStreamReader isr = null;
        //        BufferedReader br = null;
        //        try {
        //            Runtime rt = Runtime.getRuntime();
        //            String command = "pm uninstall " + packName + "\n";
        //            proc = rt.exec(command);
        //            sderr = proc.getErrorStream();
        //            isr = new InputStreamReader(sderr);
        //            br = new BufferedReader(isr);
        //            String line = null;
        //            while ((line = br.readLine()) != null) {
        //                LogUtil.i("uninstall line " + line);
        //            }
        //            int exitVal = proc.waitFor();
        //            if (exitVal == 0) {
        //                return true;
        //            }
        //
        //
        //        } catch (Exception e) {
        //            e.printStackTrace();
        //        } finally {
        //            try {
        //                br.close();
        //                isr.close();
        //                sderr.close();
        //            } catch (IOException e) {
        //                e.printStackTrace();
        //            }
        //        }
        //
        //        return false;

        val intent = Intent()
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        val sender = PendingIntent.getActivity(AppContext.get(), 0, intent, 0)
        val mPackageInstaller = AppContext.get().packageManager.packageInstaller
        mPackageInstaller.uninstall(packName, sender.intentSender)
        return true
    }
}
