package com.wujia.businesslib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;

import com.wujia.businesslib.R;
import com.wujia.businesslib.arouter.RouteDelegate;
import com.wujia.businesslib.data.DataManager;
import com.wujia.lib.widget.CommonDialog;

/**
 * author:Created by xmren on 2018/7/10.
 * email :renxiaomin@100tal.com
 */

public class QrcodeExpireDialog extends AbsDialog<QrcodeExpireDialog> {

    @Override
    protected Dialog createDialog(Context context, Object... params) {
        ImageView titleView = new ImageView(context);
        titleView.setImageResource(R.mipmap.ic_dialog_title_login);
        CommonDialog.TopViewParam topParams = new CommonDialog.TopViewParam();
        topParams.bottomMargin = -context.getResources().getDimensionPixelSize(R.dimen.dp_27);
        CommonDialog.ButtonItem item = new CommonDialog.ButtonItem("");
        item.img = R.mipmap.ic_dialog_button_gologin;
        return new CommonDialog.Builder()
                .addTopView(titleView, topParams)
                .setDialogBg(R.mipmap.dialog_background)
                .isShowTopCloseIcon(true)
                .setHeight(0.7f)
                .setWidth(0.55f)
                .setContent(new CommonDialog.TextItem(getSpan(context)))
                .addBottomBtn(item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RouteDelegate.login().jump();
                        dismiss();
                    }
                }).create(context);
    }

    private SpannableString getSpan(Context context) {
        int qrcode_date = DataManager.getConfigData() != null ? DataManager.getConfigData().qrcode_date : 365;
        SpannableString sp = new SpannableString("登录后可获得" + qrcode_date + "天扫码体验期\n可使用APP看纸质书啦～");
        sp.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.clr_e8005e)), 6, 6 + String.valueOf(qrcode_date).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sp.setSpan(new AbsoluteSizeSpan(20, true), 6, 6 + String.valueOf(qrcode_date).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sp;
    }
}
