package com.abctime.businesslib;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.abctime.lib_common.utils.ChannelUtils;

/**
 * Created by xmren on 2018/5/30.
 */

public class UpdateHelper {

    public static void goToAppMarket(Context context) {
        String chanelName = ChannelUtils.getChannelName(context);
        if (TextUtils.isEmpty(chanelName))
            chanelName = "default";
        String marketPkg = "";
        if (chanelName.equals("yingyongbao")) {
            marketPkg = "com.tencent.android.qqdownloader";
        } else if (chanelName.equals("huawei")) {
            marketPkg = "com.huawei.appmarket";
        } else if (chanelName.equals("xiaomi")) {
            marketPkg = "com.xiaomi.market";
        } else if (chanelName.equals("wandoujia")) {
            marketPkg = "com.wandoujia.phoenix2";
        } else if (chanelName.equals("oppo")) {
            marketPkg = "com.oppo.market";
        } else if (chanelName.equals("baidu")) {
            marketPkg = "com.baidu.appsearch";
        } else if (chanelName.equals("_360")) {
            marketPkg = "com.qihoo.appstore";
        } else if (chanelName.equals("vivo")) {
            marketPkg = "com.bbk.appstore";
        } else if (chanelName.equals("meizu")) {
            marketPkg = "com.meizu.store";
        }
//        else if (chanelName.equals("leshi")) {
//            marketPkg = "com.lenovo.leos.appstore";
//        } else if (chanelName.equals("google")) {
//            marketPkg = "com.android.vending";
//        }
        jumpToMarket(context, context.getPackageName(), marketPkg);
    }

    private static void jumpToMarket(Context context, String appPkg, String marketPkg) {
        try {
            if (TextUtils.isEmpty(appPkg))
                return;
            Uri uri = Uri.parse("market://details?id=" + appPkg);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (!TextUtils.isEmpty(marketPkg))
                intent.setPackage(marketPkg);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
