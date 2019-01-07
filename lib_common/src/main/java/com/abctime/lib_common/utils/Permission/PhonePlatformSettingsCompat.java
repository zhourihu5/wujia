package com.abctime.lib_common.utils.Permission;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;

import com.abctime.lib_common.BuildConfig;

/**
 * description:
 * author: KisenHuang
 * email: KisenHuang@163.com
 * time: 2018/8/17 下午3:27
 */

public class PhonePlatformSettingsCompat {
    public static boolean jumpToSetting(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return false;
        }
        String brand = Build.BRAND;//手机厂商
        if (TextUtils.isEmpty(brand)) {
            return false;
        }
        boolean jump = false;
        switch (brand.toLowerCase()) {
            case "huawei":
//                jump = true;
//                Intent intentH = new Intent();
//                intentH.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intentH.putExtra("packageName", BuildConfig.APPLICATION_ID);
//                ComponentName compH = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
//                intent.setComponent(compH);
//                activity.startActivity(intentH);
                break;
            case "meizu":
//                jump = true;
//                Intent intentM = new Intent("com.meizu.safe.security.SHOW_APPSEC");
//                intentM.addCategory(Intent.CATEGORY_DEFAULT);
//                intentM.putExtra("packageName", BuildConfig.APPLICATION_ID);
//                activity.startActivity(intentM);
                break;
            case "xiaomi":
//                jump = true;
//                Intent intentX = new Intent("miui.intent.action.APP_PERM_EDITOR");
//                ComponentName compX = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
//                intentX.setComponent(compX);
//                intentX.putExtra("extra_pkgname", BuildConfig.APPLICATION_ID);
//                activity.startActivity(intentX);
                break;
            case "sony":
//                jump  = true;
//                Intent intentS = new Intent();
//                intentS.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intentS.putExtra("packageName", BuildConfig.APPLICATION_ID);
//                ComponentName compS = new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity");
//                intentS.setComponent(compS);
//                activity.startActivity(intentS);
                break;
            case "oppo":
//                jump = true;
//                Intent intentO = new Intent();
//                intentO.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intentO.putExtra("packageName", BuildConfig.APPLICATION_ID);
//                ComponentName compO = new ComponentName("com.color.safecenter",
//                        "com.color.safecenter.permission.PermissionManagerActivity");
//                intentO.setComponent(compO);
//                activity.startActivity(intentO);
                break;
            case "lg":
                break;
            case "vivo":
                break;
            case "asmsung":
                break;
            case "letv":
                break;
            case "zte":
                break;
            case "yulong":
                break;
            case "lenovo":
                break;
        }
        return jump;
    }
}
