package com.wujia.lib.widget.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.DrawableRes;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wujia.lib.uikit.R;


/**
 * author ：shenbingkai@163.com
 * date ：2019-01-27 00:21
 * description ： toast 工具
 */
public class ToastUtil {
//    public static Handler handler=new Handler(Looper.getMainLooper());
    static Toast toast;
    private static void show(Context context, String msg, @DrawableRes int iconId, int duration) {
//        Toast toast=null;
        TextView title;
        ImageView icon;
        Context appContext=context.getApplicationContext();
        if(toast==null){
            toast = new Toast(appContext);
            View contentView = View.inflate(appContext, R.layout.layout_toast, null);

            toast.setView(contentView);
            toast.setGravity(Gravity.CENTER, 0, 0);

        }
        title=toast.getView().findViewById(R.id.toast_msg);
        title.setText(msg);
        toast.setDuration(duration);
        icon= toast.getView().findViewById(R.id.toast_icon);
        if (iconId != 0) {
            icon.setImageResource(iconId);
            icon.setVisibility(View.VISIBLE);
        }else {
            icon.setImageResource(iconId);
            icon.setVisibility(View.GONE);
        }

        toast.show();
    }

    public static void showShort(Context context, String msg) {
        show(context, msg, 0, Toast.LENGTH_SHORT);
    }

    public static void showShort(Context context, int strId) {
        show(context, context.getString(strId), 0, Toast.LENGTH_SHORT);
    }

    public static void showLong(Context context, String msg) {
        show(context, msg, 0, Toast.LENGTH_LONG);
    }

    public static void showShortWithIcon(Context context, String msg, int iconId) {
        show(context, msg, iconId, Toast.LENGTH_LONG);
    }
}
