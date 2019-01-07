package com.abctime.businesslib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.abctime.businesslib.R;
import com.abctime.businesslib.arouter.RouteDelegate;
import com.abctime.businesslib.data.DataManager;
import com.abctime.lib.widget.CommonDialog;
import com.abctime.lib_common.base.BizDialog;

/**
 * author:Created by xmren on 2018/7/10.
 * email :renxiaomin@100tal.com
 */

public class GuestExpireDialog extends AbsDialog {

    @Override
    protected Dialog createDialog(Context context, Object... params) {
        String firstText = DataManager.getConfigData().vip_notice;
        if (TextUtils.isEmpty(firstText))
            firstText = "开通VIP，享会员权益";
        CommonDialog.ButtonItem login = new CommonDialog.ButtonItem("去登录");
        CommonDialog.ButtonItem vip = new CommonDialog.ButtonItem("开通VIP");
        CommonDialog.TextItem content = new CommonDialog.TextItem(firstText + "\n若有已付费账号，建议您先去登录哦");
        CommonDialog.Builder builder = new CommonDialog.Builder();
        try {
            boolean isGuest = (boolean) params[0];
            String param = (String) params[1];
            if (isGuest) {
                builder.addBottomBtn(login, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RouteDelegate.login().withString("mode", "common").jump();
                        dismiss();
                    }
                });
                if (!TextUtils.equals(param, "0"))
                    content.text = String.format(firstText + "\n现在开通有%s折优惠哦！\n若有已付费账号，建议您先去登录哦", param);
                else
                    content.text = firstText + "\n若有已付费账号，建议您先去登录哦";
            } else {
                if (!TextUtils.equals(param, "0"))
                    content.text = String.format(firstText + "\n现在开通有%s折优惠哦！", param);
                else
                    content.text = firstText ;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        final String cid = params.length > 2 ? (String) params[2] : "";
        return builder
                .setDialogBg(R.mipmap.dialog_background)
                .isShowTopCloseIcon(true)
                .setHeight(0.6f)
                .setWidth(0.6f)
                .setContent(content)
                .addBottomBtn(vip, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RouteDelegate.payment().withString("cid", cid).jump();
                        dismiss();
                    }
                }).create(context);
    }
}
