package com.abctime.businesslib.dialog;

import android.app.Dialog;
import android.content.Context;

/**
 * Author: created by shenbingkai on 2018/11/1 17 29
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class OperateDialogControl extends AbsDialog<OperateDialogControl> {

    @Override
    protected Dialog createDialog(Context context, Object... params) {

        return new OperateDialog(context).setContent(params).build();
    }
}
