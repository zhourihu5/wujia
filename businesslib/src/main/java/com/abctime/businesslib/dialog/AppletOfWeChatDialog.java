package com.abctime.businesslib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;

import com.abctime.aoplib.annotation.Tracker;
import com.abctime.businesslib.R;
import com.abctime.businesslib.arouter.ARouterURI;
import com.abctime.businesslib.data.ConfigResponse;
import com.abctime.businesslib.data.DataManager;
import com.abctime.businesslib.wechat.WechatUtil;
import com.abctime.lib.widget.CommonDialog;
import com.abctime.lib_common.utils.font.FontUtils;
import com.abctime.lib_common.utils.sys.DensityUtils;
import com.alibaba.android.arouter.launcher.ARouter;


/**
 * Created by yseerd on 2018/10/23.
 */

public class AppletOfWeChatDialog extends AbsDialog {

    public AppletOfWeChatDialog() {
    }

    @Override
    protected Dialog createDialog(final Context context, Object... params) {
        ImageView view = new ImageView(context);
        view.setImageResource(com.abctime.lib.uikit.R.mipmap.dialog_top_bear);
        CommonDialog.TopViewParam param = new CommonDialog.TopViewParam();
        param.bottomMargin = -context.getResources().getDimensionPixelSize(R.dimen.dp_15);
        CommonDialog.TextItem title = new CommonDialog.TextItem("精讲课");
        title.textColor = R.color.clr_ffffff;
        title.textSize = 25;
        title.textFont = FontUtils.Font_TYPE_FZRUISYJW_CU;
        String minProject = "ABC Reading美国小学绘本馆";
        ConfigResponse configData = DataManager.getConfigData();
        if(configData != null){
            minProject = configData.wechatSmallProgramName;
        }
        String hint = "小程序中绑定手机号，可同步app权限哦～";
        SpannableString contentSS = new SpannableString("打开小程序“"+minProject+"”即可观看\n"+ hint);
        int totalLength = contentSS.length();
        int contentLength = totalLength - hint.length();
        contentSS.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.clr_8b3e29)), 0, contentLength -1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        contentSS.setSpan(new AbsoluteSizeSpan(17, true), 0, contentLength - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        contentSS.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.clr_666666)), contentLength, totalLength, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        contentSS.setSpan(new AbsoluteSizeSpan(12, true), contentLength, totalLength, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        CommonDialog.TextItem content = new CommonDialog.TextItem(contentSS);
        content.viewParam = new CommonDialog.TopViewParam();
        content.viewParam.topMargin = DensityUtils.dp2px(context, 20);

        try {
            title.text = "精讲课";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new CommonDialog.Builder()
                .addTopView(view, param)
                .setDialogBg(com.abctime.lib.uikit.R.mipmap.dialog_background1)
                .isShowTopCloseIcon(true)
                .setTitle(title)
                .setContent(content)
                .setHeight(-1)
                .setWidth(DensityUtils.dp2px(context,350))
                .setCancelable(false)
                .addBottomBtn(new CommonDialog.ButtonItem("立即观看"),  new View.OnClickListener() {
                    @Override
                    @Tracker(evevtId = "viewing_atonce",eventType ="click")
                    public void onClick(View v) {
                        ARouter.getInstance().build(ARouterURI.WechatURI.MAIN).withInt(WechatUtil.WECHAT_TYPE_KEY, WechatUtil.WECHAT_TYPE_KEY_MINPEOGRAM).navigation();
                        dialog.dismiss();
                    }
                }).create(context);
    }

    @Override
    public int getPriority() {
        return PopupPriority.PRIORITY_NORMAL;
    }

}
