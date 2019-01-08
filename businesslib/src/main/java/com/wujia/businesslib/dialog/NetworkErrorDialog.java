package com.wujia.businesslib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.wujia.businesslib.R;
import com.wujia.lib.widget.CommonDialog;

/**
 * description: 此类被基类中反射使用，需要在混淆文件中处理
 * author: KisenHuang
 * email: KisenHuang@163.com
 * time: 2018/7/11 下午5:22
 */


public class NetworkErrorDialog extends AbsDialog{

    @Override
    protected Dialog createDialog(final Context context, Object... params) {
        CommonDialog.Builder builder = new CommonDialog.Builder();
        return builder.setDialogBg(com.wujia.lib.uikit.R.mipmap.dialog_background)
                .isShowTopCloseIcon(false)
                .setHeight(0.6f)
                .setWidth(0.6f)
                .setContent(context.getString(R.string.hint_network_error))
                .addBottomBtn(new CommonDialog.ButtonItem(context.getString(R.string.sure)), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                }).create(context);
    }

    public interface OnButtonListener {

        public void cliked();
    }

    public static Dialog createDialog(final Context context, final OnButtonListener listener) {

        CommonDialog.Builder builder = new CommonDialog.Builder();
        return builder.setDialogBg(com.wujia.lib.uikit.R.mipmap.dialog_background)
                .isShowTopCloseIcon(false)
                .setHeight(0.6f)
                .setWidth(0.6f)
                .setContent(context.getString(R.string.hint_network_error))
                .addBottomBtn(new CommonDialog.ButtonItem(context.getString(R.string.sure)), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {

                            listener.cliked();
                        }
                    }
                }).create(context);
    }
}
