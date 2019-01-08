package com.wujia.lib.widget.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wujia.lib.uikit.R;


public class ToastUtil {

    private static final String COUPON = "coupon";
    private static final String NORMAL = "normal";
    static ToastUtil td;
    Context context;
    static Toast toast;
    String msg;
    TextView tvMsg;
    String type;

    public static void show(Context contex, int resId) {
        show(contex.getApplicationContext(), contex.getApplicationContext().getString(resId));
    }

    public static void showCoupon(Context contex, String msg) {
        if (contex == null) {
            return;
        }
        if (td == null) {
            td = new ToastUtil(contex);
        }
        if (toast == null || !TextUtils.equals(td.type, COUPON)) {
            toast = td.createCoupon();
            td.type = COUPON;
        }
        toast.setDuration(Toast.LENGTH_SHORT);
        td.setText(msg);
        toast.show();
    }

    public static void show(Context contex, String msg) {
        if (contex == null) {
            return;
        }
        if (td == null) {
            td = new ToastUtil(contex);
        }
        if (toast == null || !TextUtils.equals(td.type, NORMAL)) {
            toast = td.create();
            td.type = NORMAL;
        }
        toast.setDuration(Toast.LENGTH_SHORT);
        td.setText(msg);
        toast.show();
    }

    public static void shortShow(Context contex, String msg) {
        if (contex == null) {
            return;
        }
        if (td == null) {
            td = new ToastUtil(contex);
        }

        if (toast == null || !TextUtils.equals(td.type, NORMAL)) {
            toast = td.create();
            td.type = NORMAL;
        }
        toast.setDuration(Toast.LENGTH_SHORT);
        td.setText(msg);
        toast.show();
    }


    public ToastUtil(Context context) {
        this.context = context.getApplicationContext();
    }

    //TODO 居中显示
    public Toast create() {
        toast = new Toast(context);
        View contentView = View.inflate(context, R.layout.layout_dialog_toast, null);
        tvMsg = (TextView) contentView.findViewById(R.id.tv_toast_msg);
        toast.setView(contentView);
        toast.setGravity(Gravity.CENTER, 0, 0);
        tvMsg.setText(msg);
        return toast;
    }

    public Toast createCoupon() {
        View contentView = View.inflate(context, R.layout.layout_getcoupoon_toast, null);
        tvMsg = (TextView) contentView.findViewById(R.id.tv_coupon_value);
        toast = new Toast(context);
        toast.setView(contentView);
        toast.setGravity(Gravity.CENTER, 0, 0);
        tvMsg.setText(msg);
        return toast;
    }

    //TODO 据下方显示
    public Toast create_bottom() {
        View contentView = View.inflate(context, R.layout.layout_dialog_toast, null);
        TextView tvMsg = (TextView) contentView.findViewById(R.id.tv_toast_msg);
        toast = new Toast(context);
        toast.setView(contentView);
        toast.setGravity(Gravity.BOTTOM, 0, 50);
        toast.setDuration(Toast.LENGTH_LONG);
        tvMsg.setText(msg);
        return toast;
    }


    public void show() {
        if (toast != null) {
            toast.show();
        }
    }

    private void setText(String text) {
        msg = text;
        tvMsg.setText(msg);
    }


}
