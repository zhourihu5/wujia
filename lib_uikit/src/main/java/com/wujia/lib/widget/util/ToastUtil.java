package com.wujia.lib.widget.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wujia.lib.uikit.R;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-27 00:21
 * description ： toast 工具
 */
public class ToastUtil {

    private static void show(Context context, String msg, int duration) {
        Toast toast = new Toast(context);
        View contentView = View.inflate(context, R.layout.layout_toast, null);
        TextView title = contentView.findViewById(R.id.tv_title);
        title.setText(msg);
        toast.setView(contentView);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(duration);
        toast.show();
    }

    public static void showShort(Context context, String msg) {
        show(context, msg, Toast.LENGTH_SHORT);
    }

    public static void showLong(Context context, String msg) {
        show(context, msg, Toast.LENGTH_LONG);
    }
}
