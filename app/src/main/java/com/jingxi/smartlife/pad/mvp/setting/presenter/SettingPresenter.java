package com.jingxi.smartlife.pad.mvp.setting.presenter;

import com.wujia.businesslib.data.VersionBean;
import com.wujia.businesslib.base.RxPresenter;
import com.jingxi.smartlife.pad.mvp.setting.contract.SettingContract;
import com.wujia.businesslib.model.BusModel;
import com.wujia.lib_common.data.network.SimpleRequestSubscriber;
import com.wujia.lib_common.data.network.exception.ApiException;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-26
 * description ：
 */
public class SettingPresenter extends RxPresenter<SettingContract.View> implements SettingContract.Presenter {

    private BusModel mModel;

    public SettingPresenter() {
        this.mModel = new BusModel();
    }


    @Override
    public void checkVersion() {

        addSubscribe(mModel.checkVersion().subscribeWith(new SimpleRequestSubscriber<VersionBean>(mView, new SimpleRequestSubscriber.ActionConfig(false, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            @Override
            public void onResponse(VersionBean response) {
                super.onResponse(response);
                if (response.isSuccess()) {
                    mView.onDataLoadSucc(0, response);
                }
            }

            @Override
            public void onFailed(ApiException apiException) {
                super.onFailed(apiException);
                mView.onDataLoadFailed(0, apiException);
            }
        }));
    }
}
