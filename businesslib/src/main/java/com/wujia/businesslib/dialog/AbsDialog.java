package com.wujia.businesslib.dialog;

import android.content.DialogInterface;

import com.wujia.lib_common.base.BizDialog;

/**
 * description:
 * author: KisenHuang
 * email: KisenHuang@163.com
 * time: 2018/10/22 下午8:39
 */

public abstract class AbsDialog<T extends BizDialog> extends BizDialog<T> implements IPopupPriority {

    @Override
    public int getPriority() {
        return PopupPriority.PRIORITY_NORMAL;
    }

    @Override
    public String getTag() {
        String tag = "";
        try {
            tag = String.valueOf(dialog.hashCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tag;
    }

    @Override
    public void show() {
        boolean canShow = PopupPriority.get().addPopup(this);
        if (canShow) {
            super.show();
            if (null != dialog) {
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        PopupPriority.get().removePopup(AbsDialog.this);
                    }
                });
            }
        }
    }

    @Override
    public void dismiss() {
        super.dismiss();
//        PopupPriority.get().removePopup(this);
    }
}
