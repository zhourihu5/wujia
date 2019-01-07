package com.abctime.businesslib.dialog;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;

import com.abctime.businesslib.R;
import com.abctime.businesslib.arouter.ARouterURI;
import com.abctime.businesslib.arouter.RouteDelegate;
import com.abctime.lib.widget.CommonDialog;
import com.abctime.lib_common.utils.Permission.PermissionCallBack;
import com.abctime.lib_common.utils.Permission.PermissionUtils;
import com.alibaba.android.arouter.launcher.ARouter;

/**
 * description:
 * author: KisenHuang
 * email: KisenHuang@163.com
 * time: 2018/7/13 下午2:27
 */

public class VipExpireDialog extends AbsDialog {
    @Override
    protected Dialog createDialog(Context context, Object... params) {
        SpannableString sp = getSpan(context, params);
        CommonDialog.TextItem content = new CommonDialog.TextItem(sp);
        content.lineSpacingMultiplier = 2;
        return new CommonDialog.Builder()
                .setDialogBg(com.abctime.lib.uikit.R.mipmap.dialog_background)
                .setWidth(0.6f)
                .setHeight(-1)
                .setContent(content)
                .isShowTopCloseIcon(true)
                .addBottomBtn(new CommonDialog.ButtonItem("切换账号"), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RouteDelegate.login().withString("mode", "common").jump();
                        dismiss();
                    }
                })
                .addBottomBtn(new CommonDialog.ButtonItem("开通VIP"), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RouteDelegate.payment().jump();
                        dismiss();
                    }
                })
                .create(context);
    }

    @NonNull
    private SpannableString getSpan(final Context context, Object[] params) {
        boolean showScan = false;
        try {
            showScan = (boolean) params[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        SpannableString sp;
        if (showScan) {
            sp = new SpannableString("伤心•••使用期已结束\n开通VIP，享受会员权益\n扫一扫看书");
            //设置下划线
            sp.setSpan(new UnderlineSpan(), 25, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            sp.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    requestCameraPermission(context);
                }
            }, 25, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            sp.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, com.abctime.lib.uikit.R.color.clr_fa6c51)), 25, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            sp = new SpannableString("伤心•••使用期已结束\n开通VIP，享受会员权益\n");
        }
        return sp;
    }

    private void requestCameraPermission(final Context context) {
        PermissionUtils.checkPermission((Activity) context, Manifest.permission.CAMERA, new PermissionCallBack() {

            @Override
            public void permissionGranted() {
                ARouter.getInstance().build(ARouterURI.QrcodeURI.SCAN).navigation();
                dismiss();
            }

            @Override
            public void permissionDenied() {
                PermissionUtils.showNeverAskAgainDialog((Activity) context, context.getString(R.string.app_name)+"需要访问您的相机，请检查您的权限");
            }

            @Override
            public void permissionDeniedWithNeverAsk() {
                PermissionUtils.showNeverAskAgainDialog((Activity) context, context.getString(R.string.app_name) + "需要访问您的相机，请检查您的权限");
            }
        });
    }
}
