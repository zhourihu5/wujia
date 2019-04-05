package com.wujia.businesslib;

import android.Manifest;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.UserHandle;
import android.text.TextUtils;

import com.wujia.lib_common.utils.AppContext;
import com.wujia.lib_common.utils.LogUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018/3/7.
 */

public class ThirdPermissionUtil {
    /**
     * PackageManager.FLAG_PERMISSION_USER_SET
     * PackageManager.FLAG_PERMISSION_USER_FIXED
     * PackageManager.FLAG_PERMISSION_POLICY_FIXED
     * PackageManager.FLAG_PERMISSION_REVOKE_ON_UPGRADE
     * PackageManager.FLAG_PERMISSION_SYSTEM_FIXED
     * PackageManager.FLAG_PERMISSION_GRANTED_BY_DEFAULT
     */
    public static final int FLAG_PERMISSION_USER_SET = 1 << 0;
    public static final int FLAG_PERMISSION_USER_FIXED =  1 << 1;
    public static final int FLAG_PERMISSION_POLICY_FIXED =  1 << 2;
    public static final int FLAG_PERMISSION_REVOKE_ON_UPGRADE =  1 << 3;
    public static final int FLAG_PERMISSION_SYSTEM_FIXED =  1 << 4;
    public static final int FLAG_PERMISSION_GRANTED_BY_DEFAULT =  1 << 5;

    private static String[] grantPermissions = new String[]{
            Manifest.permission.CHANGE_WIFI_MULTICAST_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA,
            Manifest.permission.GET_TASKS,
            Manifest.permission.INTERNET,
            Manifest.permission.MODIFY_AUDIO_SETTINGS,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.VIBRATE,
            Manifest.permission.WAKE_LOCK,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_SETTINGS,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BROADCAST_STICKY,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
    };
    private static List<String> grantList = Arrays.asList(grantPermissions);

    public static void requestDefaultPermissions(String packageName){
        requestDefaultPermissions(packageName,false);
    }

    public static void requestDefaultPermissions(String packageName,boolean needGrant){
        if(TextUtils.isEmpty(packageName)){
            return;
        }

        try {
            PackageManager packageManager = AppContext.get().getPackageManager();

            PackageInfo info = packageManager.getPackageInfo(packageName,PackageManager.GET_PERMISSIONS);
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageName,0);

            Constructor newUserHandle = UserHandle.class.getDeclaredConstructor(int.class);
            newUserHandle.setAccessible(true);
            UserHandle handle = (UserHandle) newUserHandle.newInstance(0);

            Method grantRuntimePermission = packageManager.getClass().getDeclaredMethod("grantRuntimePermission",String.class,String.class,UserHandle.class);
            grantRuntimePermission.setAccessible(true);

            Method revokeRuntimePermission = packageManager.getClass().getDeclaredMethod("revokeRuntimePermission",String.class,String.class,UserHandle.class);
            revokeRuntimePermission.setAccessible(true);

            Method updatePermissionFlags = packageManager.getClass().getDeclaredMethod("updatePermissionFlags",String.class,String.class,int.class,int.class,UserHandle.class);
            updatePermissionFlags.setAccessible(true);
            String[] permissions = info.requestedPermissions;
            for(String permission : permissions){
                try {
                    if(grantList.contains(permission) || needGrant || ( applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) > 0){
                        grantRuntimePermission.invoke(packageManager,packageName,permission,handle);
                    }else{
                        revokeRuntimePermission.invoke(packageManager,packageName,permission,handle);
                        /**
                         * 不再提示权限申请弹框
                         */
                        updatePermissionFlags.invoke(packageManager,permission,packageName,
                                FLAG_PERMISSION_USER_FIXED| FLAG_PERMISSION_USER_SET,
                                FLAG_PERMISSION_USER_FIXED,handle);
                    }
                } catch (Exception e) {
                    LogUtil.i("Permission err : [ " + e.getMessage() + " ]" );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
