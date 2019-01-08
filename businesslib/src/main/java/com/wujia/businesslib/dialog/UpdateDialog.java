package com.wujia.businesslib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.wujia.businesslib.R;
import com.wujia.businesslib.data.ConfigResponse;
import com.wujia.lib.widget.CommonDialog;
import com.wujia.lib_common.utils.font.FontUtils;

/**
 * author:Created by xmren on 2018/7/10.
 * email :renxiaomin@100tal.com
 */

public class UpdateDialog extends AbsDialog {
    private CommonDialog dialog;
    private UpdateCallback updateCallback;

    private ConfigResponse.UpdateInfoBean updateInfoBean;

    public UpdateDialog(ConfigResponse.UpdateInfoBean updateInfoBean, UpdateCallback updateCallback) {
        this.updateInfoBean = updateInfoBean;
        this.updateCallback = updateCallback;
    }

    public CommonDialog getDialog() {
        return dialog;
    }

    @Override
    protected Dialog createDialog(final Context context, Object... params) {

        ImageView view = new ImageView(context);
        view.setImageResource(com.wujia.lib.uikit.R.mipmap.dialog_top_img);
        CommonDialog.TopViewParam param = new CommonDialog.TopViewParam();
        param.bottomMargin = -context.getResources().getDimensionPixelSize(R.dimen.dp_13);
        param.leftMargin = context.getResources().getDimensionPixelSize(R.dimen.dp_15);
        CommonDialog.TextItem title = new CommonDialog.TextItem(updateInfoBean.updateTitle);
        title.textColor = R.color.clr_ffffff;
        title.textSize = 22;
        title.textFont = FontUtils.Font_TYPE_FZRUISYJW_CU;
        try {
            title.text = updateInfoBean.updateTitle.substring(0, 6);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dialog = (CommonDialog) new CommonDialog.Builder()
                .addTopView(view, param)
                .setDialogBg(com.wujia.lib.uikit.R.mipmap.dialog_background_title)
                .isShowTopCloseIcon(updateInfoBean.updateType != 2)
                .setTitle(title)
                .setContent(updateInfoBean.updateTip)
                .setHeight(0.9f)
                .setWidth(0.5f)
                .setCancelable(false)
                .addBottomBtn(new CommonDialog.ButtonItem("立即更新"), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != updateCallback) {
                            updateCallback.updateApp(updateInfoBean);
                        }
                    }
                }).create(context);
        return dialog;
    }


    public interface UpdateCallback {
        void updateApp(ConfigResponse.UpdateInfoBean updateInfoBean);
    }

    @Override
    public int getPriority() {
        return PopupPriority.PRIORITY_UPDATE;
    }


}
