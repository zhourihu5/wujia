package com.wujia.lib_common.utils.Permission;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

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

    public PermissionUtils(BaseActivity baseActivity) {
        curActivity = baseActivity;
        rxPermissions = new RxPermissions(baseActivity);
    }

    private String permissionType(EPermission ePermission) {
        switch (ePermission) {
            case camera:
                return Manifest.permission.CAMERA;
            case record:
                return Manifest.permission.RECORD_AUDIO;
            default:
                return Manifest.permission.RECORD_AUDIO;
        }
    }

    //申请权限类型
    public void checkPermission(EPermission ePermission) {
        rxPermissions
                .requestEach(
                        permissionType(ePermission))
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) {
                        if (permission.granted) {
                            //ARouter.getInstance().build("/zxing/qrscan").navigation();
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // Denied permission without ask never again
                        } else {
                            // Denied permission with ask never again
                            // Need to go to the settings
                            askForPermission("需要给予权限许可!", "取消", "去开启");
                        }
                    }
                });
    }

    public void checkPermission(EPermission ePermission, final PermissionCallBack permissionCallBack) {
        rxPermissions
                .requestEach(
                        permissionType(ePermission))
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) {
                        if (permission.granted) {
                            //ARouter.getInstance().build("/zxing/qrscan").navigation();
                            permissionCallBack.permissionGranted();
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // Denied permission without ask never again
                            permissionCallBack.permissionDenied();
                        } else {
                            // Denied permission with ask never again
                            // Need to go to the settings
                            askForPermission("需要给予权限许可!", "取消", "去开启");
                            permissionCallBack.permissionDeniedWithNeverAsk();
                        }
                    }
                });
    }

    public static void checkPermission(final Activity activity, final String permisson, final PermissionCallBack permissionCallBack) {
        new RxPermissions(activity)
                .requestEach(permisson)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) {
                        if (permission.granted) {
                            if (Manifest.permission.RECORD_AUDIO.equals(permisson)) {
                                if (isAudioPermissionGranted(activity)) {
                                    permissionCallBack.permissionGranted();
                                } else {
                                    permissionCallBack.permissionDenied();
                                }
                            } else if (Manifest.permission.CAMERA.equals(permisson)) {
                                if (isMeizuFactory()) {
                                    if (cameraIsCanUse()) {
                                        permissionCallBack.permissionGranted();
                                    } else {
                                        permissionCallBack.permissionDenied();
                                    }
                                } else {
                                    permissionCallBack.permissionGranted();
                                }
                            } else {
                                permissionCallBack.permissionGranted();
                            }
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            permissionCallBack.permissionDenied();
                        } else {
                            permissionCallBack.permissionDeniedWithNeverAsk();
                        }
                    }
                });
    }

    public void checkPermission(EPermission ePermission, final PermissionCallBack permissionCallBack,
                                final String title, final String cancel, final String settings) {
        rxPermissions
                .requestEach(
                        permissionType(ePermission))
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) {
                        if (permission.granted) {
                            //ARouter.getInstance().build("/zxing/qrscan").navigation();
                            permissionCallBack.permissionGranted();
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // Denied permission without ask never again
                            permissionCallBack.permissionDenied();
                        } else {
                            // Denied permission with ask never again
                            // Need to go to the settings
                            askForPermission(title, cancel, settings);
                            permissionCallBack.permissionDeniedWithNeverAsk();
                        }
                    }
                });
    }

    private void askForPermission(String title, String cancel, String settings) {
        AlertDialog.Builder builder = new AlertDialog.Builder(curActivity);
        builder.setTitle(title);
        builder.setNegativeButton(cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton(settings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + curActivity.getPackageName())); // 根据包名打开对应的设置界面
                curActivity.startActivity(intent);
            }
        });
        builder.create().show();
    }

    public static void showNeverAskAgainDialog(final Activity activity, String title) {
        final Activity curActivity = activity;
        AlertDialog.Builder builder = new AlertDialog.Builder(curActivity);
        builder.setTitle(title);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.setPositiveButton("去开启", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                if (isMeizuFactory()) {
//                    gotoMeizuPermission(activity, 100);
//                    return;
//                }
//                if (!PhonePlatformSettingsCompat.jumpToSetting(curActivity)) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + curActivity.getPackageName())); // 根据包名打开对应的设置界面
                curActivity.startActivity(intent);
//                }
            }
        });
        builder.create().show();
    }

    private static boolean isAudioPermissionGranted(Activity activity) {
        try {
            AudioRecordManager recordManager = new AudioRecordManager();

            recordManager.startRecord(activity.getCacheDir() + "/permission4p.3gp");

            int sleep = 250;
            if (isOppoFactory() && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                sleep = 450;
            }
            recordManager.stopRecord(sleep);

            return recordManager.getSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean isMeizuFactory() {
        String brand = Build.BRAND;//手机厂商
        if (TextUtils.isEmpty(brand)) {
            return false;
        }
        return brand.toLowerCase().equals("meizu");
    }

    private static boolean isOppoFactory() {
        String brand = Build.BRAND;//手机厂商
        if (TextUtils.isEmpty(brand)) {
            return false;
        }
        return brand.toLowerCase().equals("oppo");
    }

    private static void gotoMeizuPermission(Activity activity, int requestCode) {
        try {
            Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra("packageName", activity.getPackageName());
            activity.startActivityForResult(intent, requestCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean cameraIsCanUse() {
        boolean isCanUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            Camera.Parameters mParameters = mCamera.getParameters();
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            isCanUse = false;
        }

        if (mCamera != null) {
            try {
                mCamera.release();
            } catch (Exception e) {
                e.printStackTrace();
                return isCanUse;
            }
        }
        return isCanUse;
    }
}






