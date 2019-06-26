package com.wujia.businesslib.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.wujia.businesslib.base.BaseApplication;
import com.wujia.lib_common.utils.AppContext;

public class LoginUtil {
    public static void toLoginActivity() {
        try {
            Activity currentActivity = BaseApplication.getCurrentAcitivity();
            Context context = AppContext.get();
            Intent intent = new Intent();
            if (currentActivity != null) {
                context = currentActivity;
            } else {
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);//applicationcontext 启动只能以newtask
            }

            intent.setClassName(context, "com.jingxi.smartlife.pad.mvp.login.LoginActivity");
            context.startActivity(intent);
            if (currentActivity != null) {
                currentActivity.finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
