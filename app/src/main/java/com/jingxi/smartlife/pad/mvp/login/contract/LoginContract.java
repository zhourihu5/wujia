package com.jingxi.smartlife.pad.mvp.login.contract;

import com.wujia.businesslib.data.RootResponse;
import com.wujia.businesslib.data.TokenBean;
import com.wujia.businesslib.data.UserBean;
import com.wujia.lib_common.base.BasePresenter;
import com.wujia.lib_common.base.CommonDataLoadView;
import com.wujia.lib_common.base.IBaseModle;

import io.reactivex.Flowable;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-27
 * description ：
 */
public interface LoginContract {
    interface Model extends IBaseModle {

        Flowable<TokenBean> getAccessToken();

        Flowable<UserBean> login(String mobile, String captcha, String padSn);

        Flowable<RootResponse> login(String mobile);


    }

    interface View extends CommonDataLoadView {
        void timeChange(String time);
    }

    interface Presenter extends BasePresenter<View> {
        void doGetAccessToken();

        void doTimeChange();

        void doLogin(String mobile, String captcha, String padSn);

        void doGetCode(String mobile);
    }
}
