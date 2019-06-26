package com.wujia.lib_common.utils;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-26
 * description ：
 */
public class SystemUtil {

    private static String serialNum;


    public static void init() {
//        serialNum = android.os.Build.SERIAL;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (ActivityCompat.checkSelfPermission(AppContext.get(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            serialNum = Build.getSerial();
        } else {
            serialNum = android.os.Build.SERIAL;//todo 有时获取不到，结果就是"unknown"
        }

    }

    public static String getSerialNum() {
        if (TextUtils.isEmpty(serialNum) || Build.UNKNOWN.equals(serialNum)) {
            init();
        }
        return serialNum;
//        return "HS1JXY6M12D2900034";
//        return "GB8ZAJFKZE";
    }
}
