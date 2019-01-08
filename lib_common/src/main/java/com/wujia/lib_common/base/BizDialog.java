package com.wujia.lib_common.base;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * author:Created by xmren on 2018/7/10.
 * email :renxiaomin@100tal.com
 */

public abstract class BizDialog<T extends BizDialog> {

    protected Dialog dialog;
    private static Map<String, BizDialog> mInstanceMap = new HashMap<>();

    @Nullable
    public static BizDialog showInstance(String className, Context context, Object... params) {
        BizDialog instance;
        if (mInstanceMap.containsKey(className)) {
            instance = mInstanceMap.get(className);
        } else {
            instance = reflectObj(className);
            if (instance != null) {
                mInstanceMap.put(className, instance);
            }
        }
        if (instance != null) {
            instance.create(context, params);
            instance.show();
        }
        return instance;
    }

    @Nullable
    public static BizDialog show(String className, Context context, Object... params) {
        BizDialog dialog = reflectObj(className);
        if (dialog != null) {
            dialog.create(context, params);
            dialog.show();
        }
        return dialog;
    }

    public T create(Context context, Object... params) {
        if (dialog != null) {
            dialog.cancel();
        }
        dialog = createDialog(context, params);
        return (T) this;
    }

    protected abstract Dialog createDialog(Context context, Object... params);

    protected boolean isShow() {
        return dialog != null && dialog.isShowing();
    }

    public void show() {
        if (dialog != null) {
            dialog.show();
        }
    }

    public void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public static void destroy(Context context) {
        for (BizDialog dialog : mInstanceMap.values()) {
            if (dialog.dialog.getContext() == context) {
                dialog.release();
            }
        }
    }

    protected void release() {
        dismiss();
        dialog = null;
    }

    public static void clearDialogs() {
        mInstanceMap.clear();
    }

    @Nullable
    private static <T extends BizDialog> T reflectObj(String className) {
        try {
            return (T) Class.forName(className).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
