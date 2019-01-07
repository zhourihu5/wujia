package com.abctime.businesslib.dialog;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.abctime.businesslib.R;
import com.abctime.businesslib.UpdateHelper;
import com.abctime.businesslib.arouter.ARouterURI;
import com.abctime.businesslib.data.ConfigResponse;
import com.abctime.lib.widget.CommonDialog;
import com.abctime.lib.widget.util.ToastUtil;
import com.abctime.lib_common.base.BizDialog;
import com.abctime.lib_common.utils.DownloadUtils;
import com.abctime.lib_common.utils.LogUtil;
import com.abctime.lib_common.utils.Permission.PermissionCallBack;
import com.abctime.lib_common.utils.Permission.PermissionUtils;
import com.abctime.lib_common.utils.font.FontUtils;
import com.alibaba.android.arouter.launcher.ARouter;

import java.io.File;

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
        view.setImageResource(com.abctime.lib.uikit.R.mipmap.dialog_top_img);
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
                .setDialogBg(com.abctime.lib.uikit.R.mipmap.dialog_background_title)
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
