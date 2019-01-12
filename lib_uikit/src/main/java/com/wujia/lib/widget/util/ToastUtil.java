package com.wujia.lib.widget.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wujia.lib.uikit.R;


public class ToastUtil {

    private static Toast mToast = null; // Toast对像
    private static String oldMsg;
    private static long oneTime = 0;
    private static long twoTime = 0;


    private ToastUtil() {
    }

    public static void shortShow(Context context, String message) {
        if (mToast == null) {
            mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
//            mToast.setGravity(Gravity.TOP, 0, 150);
            mToast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (message.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    mToast.show();
                }
            } else {
                oldMsg = message;
                mToast.setText(message);
                mToast.show();
            }
        }
        oneTime = twoTime;
    }

    public static void cancel() {
        if (null != mToast) {
            mToast.cancel();
        }
    }
}
