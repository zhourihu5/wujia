package com.wujia.intellect.terminal.mvp.home.contract;

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
public interface HomeContract {
    interface Model extends IBaseModle {




    }

    interface View extends CommonDataLoadView {
    }

    interface Presenter extends BasePresenter<View> {

    }
}
