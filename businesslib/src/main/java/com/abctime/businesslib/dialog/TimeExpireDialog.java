package com.abctime.businesslib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.abctime.businesslib.arouter.RouteDelegate;
import com.abctime.lib.widget.CommonDialog;
import com.abctime.lib_common.base.BizDialog;

/**
 * description:
 * author: KisenHuang
 * email: KisenHuang@163.com
 * time: 2018/7/23 上午10:53
 */

public class TimeExpireDialog extends AbsDialog<TimeExpireDialog> {
    @Override
    protected Dialog createDialog(Context context, Object... params) {
        CommonDialog.Builder builder = new CommonDialog.Builder();
        boolean isGuest = (boolean) params[1];
        String text = "您的使用期只剩余%s天，快快充值吧～\n";
        if (isGuest) {
            builder.addBottomBtn(new CommonDialog.ButtonItem("去登录"), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RouteDelegate.login().withString("mode", "common").jump();
                    dismiss();
                }
            });
            text = "您的使用期只剩余%s天，快快充值吧～\n若已有已付费账号，建议您先去登录哦";
        }
        CommonDialog.TextItem content = new CommonDialog.TextItem("");
        try {
            content.text = String.format(text, params[0]);
        } catch (Exception e) {
            content.text = String.format(text, 0);
        }

        return builder
                .setDialogBg(com.abctime.lib.uikit.R.mipmap.dialog_background)
                .setWidth(0.6f)
                .setHeight(0.6f)
                .setContent(content)
                .isShowTopCloseIcon(true)
                .addBottomBtn(new CommonDialog.ButtonItem("开通VIP"), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RouteDelegate.payment().jump();
                        dismiss();
                    }
                })
                .create(context);
    }
}
