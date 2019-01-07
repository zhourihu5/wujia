package com.abctime.businesslib.dialog;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import com.abctime.lib.widget.CommonDialog;
import com.abctime.lib_common.utils.font.FontUtils;

/**
 * Created by yseerd on 2018/10/29.
 */

public class SimpleDialogWithTitle extends AbsDialog<SimpleDialogWithTitle> {

    @Override
    protected Dialog createDialog(Context context, final Object... params) {
        String titleStr = "未设置";
        String contentStr = "未设置";
        String sureBtn = "确认";
        //String cancelBtn = "取消";
        try {
            titleStr = (String) params[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            contentStr = (String) params[1];
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            sureBtn = (String) params[2];
        } catch (Exception e) {
            e.printStackTrace();
        }
//        try {
//            cancelBtn = (String) params[4];
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        CommonDialog.TextItem title = new CommonDialog.TextItem(titleStr);
        title.textSize = 20;
        title.textFont = FontUtils.FONT_TYPE_FZRUISYJW_ZHONG;
        CommonDialog.TextItem content = new CommonDialog.TextItem(contentStr);
        content.textSize = 15;
        content.textFont = FontUtils.FONT_TYPE_FZRUISYJW_ZHONG;
        CommonDialog.ButtonItem goOn = new CommonDialog.ButtonItem(sureBtn);
        goOn.textColor = com.abctime.lib.uikit.R.color.clr_ffffff;
//        CommonDialog.ButtonItem exit = new CommonDialog.ButtonItem(cancelBtn);
//        exit.textColor = com.abctime.lib.uikit.R.color.clr_ffffff;
        return new CommonDialog.Builder()
                .setDialogBg(com.abctime.businesslib.R.mipmap.dialog_background)
                .isShowTopCloseIcon(true)
                .setTitle(title)
                .setContent(content)
                .setWidth(0.6f)
                .setHeight(0.6f)
//                .addBottomBtn(exit, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        try {
//                            ((View.OnClickListener) params[5]).onClick(v);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        dismiss();
//                    }
//                })
                .addBottomBtn(goOn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            ((View.OnClickListener) params[3]).onClick(v);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        dismiss();
                    }
                })
                .create(context);
    }
}
