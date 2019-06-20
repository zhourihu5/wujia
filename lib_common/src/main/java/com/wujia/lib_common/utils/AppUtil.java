package com.wujia.lib_common.utils;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-30
 * description ：
 */
public class AppUtil {


    public static boolean startAPPByPackageName(String packagename) {

        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageinfo = null;
        try {
            packageinfo = AppContext.get().getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageinfo == null) {
            return false;
        }

        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);

        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveinfoList = AppContext.get().getPackageManager()
                .queryIntentActivities(resolveIntent, 0);

        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null) {
            // packagename = 参数packname
            String packageName = resolveinfo.activityInfo.packageName;
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
            String className = resolveinfo.activityInfo.name;
            // LAUNCHER Intent
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            //不保存历史记录
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            // 设置ComponentName参数1:packagename参数2:MainActivity路径
            ComponentName cn = new ComponentName(packageName, className);
            intent.setComponent(cn);
//            Activity activity = BaseApplication.baseApplication.getLastActivity();
//            if (activity == null || activity.isFinishing()) {
//                return false;
//            }
            AppContext.get().startActivity(intent);
        }
        return true;
    }

    public static boolean install(String apkPath) {
        LogUtil.i("install " + apkPath);
        InputStream sderr = null;
        Process proc = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            Runtime rt = Runtime.getRuntime();
            String command = "pm install -r " + apkPath + "\n";
            proc = rt.exec(command);
            sderr = proc.getErrorStream();
            isr = new InputStreamReader(sderr);
            br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null) {
                LogUtil.i("install line " + line);
            }
            int exitVal = proc.waitFor();
            if (exitVal == 0) {
                return true;
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != br)
                    br.close();
                if (null != isr)
                    isr.close();
                if (null != sderr)
                    sderr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public static boolean uninstall(String packName) {
        if(TextUtils.isEmpty(packName)){
            return false;
        }
        LogUtil.i("uninstall " + packName);
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

        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent sender = PendingIntent.getActivity(AppContext.get(), 0, intent, 0);
        PackageInstaller mPackageInstaller = AppContext.get().getPackageManager().getPackageInstaller();
        mPackageInstaller.uninstall(packName, sender.getIntentSender());
        return true;
    }
}
