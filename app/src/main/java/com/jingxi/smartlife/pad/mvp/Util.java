package com.jingxi.smartlife.pad.mvp;

import android.content.Context;

import com.jingxi.smartlife.pad.market.mvp.data.ServiceDto;
import com.wujia.businesslib.data.ApiResponse;
import com.wujia.businesslib.model.BusModel;
import com.wujia.lib_common.base.BaseView;
import com.wujia.lib_common.data.network.SimpleRequestSubscriber;
import com.wujia.lib_common.data.network.exception.ApiException;
import com.wujia.lib_common.utils.SystemUtil;
import com.wujia.lib_common.utils.VersionUtil;

import io.reactivex.disposables.Disposable;

public class Util {

    public static Disposable getUpdateVesion() {
        String versionName = VersionUtil.getVersionName();
        String key = SystemUtil.getSerialNum();
        int versionCode = VersionUtil.getVersionCode();
        SimpleRequestSubscriber<ApiResponse<Object>> subscribe = new BusModel().updateVer(versionName, key, versionCode + "").subscribeWith(new SimpleRequestSubscriber<ApiResponse<Object>>(new BaseView() {
            @Override
            public void showErrorMsg(String msg) {

            }

            @Override
            public void showLoadingDialog(String text) {

            }

            @Override
            public void hideLoadingDialog() {

            }

            @Override
            public Context getContext() {
                return null;
            }

            @Override
            public void onLoginStatusError() {

            }
        }, new SimpleRequestSubscriber.ActionConfig(false, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            @Override
            public void onResponse(ApiResponse<Object> response) {
                super.onResponse(response);

            }

            @Override
            public void onFailed(ApiException apiException) {
                super.onFailed(apiException);
            }
        });
        return subscribe;
    }

}
