package com.wujia.lib_common.utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.content.PermissionChecker;

import com.wujia.lib_common.base.BaseActivity;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * Created by yseerd on 2018/6/2.
 */

public class PermissionUtils {

    private RxPermissions rxPermissions;
    private BaseActivity curActivity;

    public void PermissionUtils(BaseActivity baseActivity) {
        curActivity = baseActivity;
        rxPermissions = new RxPermissions(baseActivity);
    }

    private String PermissionType(EPermission ePermission) {
        switch (ePermission) {
            case camera:
                return Manifest.permission.CAMERA;
            case record:
                return Manifest.permission.RECORD_AUDIO;
            default:
                return Manifest.permission.RECORD_AUDIO;
        }
    }

    // 检查相机权限
    public static boolean checkCameraPermission(Context context) {

        return checkPermission(context, Manifest.permission.CAMERA);
    }

    // 检查 SD 卡权限
    public static boolean checkSDCardPermission(Context context) {
        return checkPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    // 请求 SD 卡权限
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void requestSDCardPermission(Activity activity, int requestCode) {
        requestPermission(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);
    }

    // 检查权限组
    public static boolean checkRequestPermissionsResult(int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private static boolean checkPermission(Context context, String permissionStr) {

        boolean result = true;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            result = context.checkSelfPermission(permissionStr) == PackageManager.PERMISSION_GRANTED;
        } else {

            result = PermissionChecker.checkSelfPermission(context, permissionStr) == PackageManager.PERMISSION_GRANTED;
        }

        return result;
    }

    // 请求相机权限
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void requestCameraPermission(Activity activity, int requestCode) {
        requestPermission(activity, new String[]{Manifest.permission.CAMERA}, requestCode);
    }

    // 申请权限
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void requestPermission(Activity activity, String[] permissionStrs, int requestCode) {
        activity.requestPermissions(permissionStrs, requestCode);
    }

    //申请权限类型
    public void checkPermission(final EPermission ePermission) {
        rxPermissions
                .requestEach(
                        PermissionType(ePermission))
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) {
                        if (permission.granted) {
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // Denied permission without ask never again
                        } else {
                            // Denied permission with ask never again
                            // Need to go to the settings
                            goToAppPermissionPage("需要您开启必要权限！");
                        }
                    }
                });
    }

    private void ShowToast(String msg) {
//        ToastUtil.show(curActivity, msg);
    }

    private void goToAppPermissionPage(String permissionTips) {
        AlertDialog.Builder builder = new AlertDialog.Builder(curActivity);
        builder.setTitle("abctime" + permissionTips);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + curActivity.getPackageName())); // 根据包名打开对应的设置界面
                curActivity.startActivity(intent);
            }
        });
        builder.create().show();
    }

}


enum EPermission {
    camera, record,
}


