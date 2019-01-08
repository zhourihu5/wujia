package com.wujia.businesslib.dialog;

import android.app.Dialog;
import android.content.Context;

import com.wujia.lib.widget.CommonDialog;

/**
 * description: 此类被基类中反射使用，需要在混淆文件中处理
 * author: KisenHuang
 * email: KisenHuang@163.com
 * time: 2018/7/11 下午5:22
 */

public class LongContentDialog extends AbsDialog{

    private String mContent;

    public LongContentDialog(String content) {
        this.mContent = content;
    }

    @Override
    protected Dialog createDialog(final Context context, Object... params) {
        CommonDialog.Builder builder = new CommonDialog.Builder();
        CommonDialog.TextItem textItem=new CommonDialog.TextItem(mContent);
        textItem.maxLine=-1;
        return builder.setDialogBg(com.wujia.lib.uikit.R.mipmap.dialog_background)
                .isShowTopCloseIcon(true)
                .setHeight(0.6f)
                .setWidth(-1)
                .setContent(textItem)
                .create(context);
    }
}
