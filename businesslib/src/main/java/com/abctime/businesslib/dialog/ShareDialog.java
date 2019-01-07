package com.abctime.businesslib.dialog;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.abctime.aoplib.annotation.Tracker;
import com.abctime.businesslib.R;
import com.abctime.businesslib.arouter.ARouterURI;
import com.abctime.businesslib.wechat.WechatUtil;
import com.abctime.businesslib.wechat.bean.WeChatShareBean;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;

/**
 * Created by shenbingkai on 2018/8/30.
 * 分享dialog
 */

public class ShareDialog extends Dialog {

    Activity mContext;
    WeChatShareBean shareBean;
    int requestCode;
    private int a;

    public ShareDialog(@NonNull Activity context, WeChatShareBean shareBean) {
        this(context, shareBean, -101);
    }

    public ShareDialog(@NonNull Activity context, WeChatShareBean shareBean, int requestCode) {
        super(context, R.style.bottomDialogStyle);
        this.mContext = context;
        this.shareBean = shareBean;
        this.requestCode = requestCode;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_dialog_share);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);

        findViewById(R.id.btn_share_wechat_friend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toShare(WeChatShareBean.WECHAT_SHARE_SCENE_SESSION);
            }
        });
        findViewById(R.id.btn_share_wechat_circle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toShare(WeChatShareBean.WECHAT_SHARE_SCENE_TIME_LINE);
            }
        });
        findViewById(R.id.iv_share_div).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Tracker(evevtId = "report_shareboard_click")
    private void toShare(int scene) {
        dismiss();
        shareBean.scene = scene;
        Postcard nav = ARouter.getInstance().build(ARouterURI.WechatURI.MAIN).withInt(WechatUtil.WECHAT_TYPE_KEY, WechatUtil.WECHAT_TYPE_KEY_SHARE).withSerializable(WechatUtil.WECHAT_KEY_CONTENT, shareBean);
        if (requestCode == -101) {
            nav.navigation(mContext);
        } else {
            nav.navigation(mContext, requestCode);
        }
    }

    @Override
    public void show() {
        super.show();
    }
}
