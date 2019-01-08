package com.wujia.businesslib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.wujia.lib.widget.CommonDialog;
import com.wujia.lib_common.utils.font.FontUtils;

/**
 * description:
 * author: KisenHuang
 * email: KisenHuang@163.com
 * time: 2018/8/31 上午11:18
 */

public class SimpleDialog extends AbsDialog<SimpleDialog> {

    public CommonDialog getDialog() {
        if (null != dialog && dialog instanceof CommonDialog) {
            return (CommonDialog) dialog;
        } else {
            return null;
        }
    }

    @Override
    protected Dialog createDialog(Context context, final Object... params) {
        String contentStr = "未设置";
        String sureBtn = "确认";
        String cancelBtn = "取消";
        try {
            contentStr = (String) params[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            sureBtn = (String) params[1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            cancelBtn = (String) params[3];
        } catch (Exception e) {
            e.printStackTrace();
        }
        CommonDialog.TextItem content = new CommonDialog.TextItem(contentStr);
        content.textSize = 15;
        content.textFont = FontUtils.FONT_TYPE_FZRUISYJW_ZHONG;
        CommonDialog.ButtonItem goOn = new CommonDialog.ButtonItem(sureBtn);
        goOn.textColor = com.wujia.lib.uikit.R.color.clr_ffffff;
        CommonDialog.ButtonItem exit = new CommonDialog.ButtonItem(cancelBtn);
        exit.textColor = com.wujia.lib.uikit.R.color.clr_ffffff;
        return new CommonDialog.Builder()
                .setDialogBg(com.wujia.businesslib.R.mipmap.dialog_background)
                .isShowTopCloseIcon(true)
                .setContent(content)
                .setWidth(0.6f)
                .setHeight(0.6f)
                .addBottomBtn(exit, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            ((View.OnClickListener) params[4]).onClick(v);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        dismiss();
                    }
                })
                .addBottomBtn(goOn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if (null != getDialog()) {
                                getDialog().showProgressBar();
                                getDialog().setContent(new CommonDialog.TextItem("不要走开，马上就好~"));
                            }
                            ((View.OnClickListener) params[2]).onClick(v);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .create(context);
    }
}
