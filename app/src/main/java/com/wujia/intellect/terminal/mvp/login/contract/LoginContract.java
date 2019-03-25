package com.wujia.intellect.terminal.mvp.login.contract;

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


    }

    interface View extends CommonDataLoadView {
        void timeChange(String time);
    }

    interface Presenter extends BasePresenter<View> {
        void doGetAccessToken();

        void doTimeChange();

        void doLogin(String mobile, String captcha, String padSn);
    }
}
