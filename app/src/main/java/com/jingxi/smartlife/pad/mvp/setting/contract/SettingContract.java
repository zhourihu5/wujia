package com.jingxi.smartlife.pad.mvp.setting.contract;

import com.jingxi.smartlife.pad.mvp.setting.data.VersionBean;
import com.jingxi.smartlife.pad.mvp.setting.data.VersionBean;
import com.wujia.lib_common.base.BasePresenter;
import com.wujia.lib_common.base.CommonDataLoadView;
import com.wujia.lib_common.base.IBaseModle;

import io.reactivex.Flowable;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-26
 * description ：
 */
public interface SettingContract {
    interface Model extends IBaseModle {
        Flowable<VersionBean> checkVersion();

    }

    interface View extends CommonDataLoadView {
    }

    interface Presenter extends BasePresenter<SettingContract.View> {

        void checkVersion();

    }
}
