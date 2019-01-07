package com.abctime.lib.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.abctime.lib.uikit.R;
import com.abctime.lib_common.utils.sys.ScreenUtil;

/**
 * Created by xmren on 2018/5/22.
 */

public abstract class BaseDialog extends Dialog {
    LayoutInflater layoutInflater;
    protected Context context;
    protected final View dialogView;

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        dialogView = layoutInflater.inflate(getLayoutId(), null);
        initView(dialogView);
    }

    public abstract int getLayoutId();

    public abstract float getLayoutWidth();

    public abstract int getLayoutPosition();

    public abstract int getAnimations();

    public abstract void initView(View dialogView);

    public abstract float getLayoutHeight();


    public void create() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                super.create();//该方法在21以上的版本才有
        } catch (Exception e) {
            e.printStackTrace();
        }
        createDialog();
    }

    private void createDialog() {
        Window dialogWindow = getWindow();
        if (dialogWindow != null) {
            if (getAnimations() != 0) {
                dialogWindow.setWindowAnimations(getAnimations());
            }
            WindowManager.LayoutParams params = dialogWindow.getAttributes();
            dialogWindow.setGravity(getLayoutPosition());
            float layoutWidth = getLayoutWidth();
            if (layoutWidth > 1) {
                params.width = (int) layoutWidth;
            } else if (layoutWidth > 0) {
                params.width = (int) (ScreenUtil.getRealScreenWidth(context) * layoutWidth);
            } else {
                params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            }
            float layoutHeight = getLayoutHeight();
            if (layoutHeight > 1) {
                params.height = (int) layoutHeight;
            } else if (layoutHeight > 0) {
                params.height = (int) (ScreenUtil.getLandscapeHeight() * layoutHeight);
            } else {
                dialogView.findViewById(R.id.view_space).setVisibility(View.GONE);
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            }
            dialogWindow.setAttributes(params);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(params.width, params.height);
            setContentView(dialogView, layoutParams);
        }
    }

    public static abstract class Builder {
        public Builder() {
        }

        public abstract BaseDialog create(Context context);

    }
}
