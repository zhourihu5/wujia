package com.jingxi.smartlife.pad.mvp.login.contract

import com.wujia.businesslib.data.LoginDTO
import com.wujia.lib_common.base.BasePresenter
import com.wujia.lib_common.base.CommonDataLoadView
import com.wujia.lib_common.base.IBaseModle
import com.wujia.lib_common.base.RootResponse
import io.reactivex.Flowable

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-27
 * description ：
 */
interface LoginContract {
    interface Model : IBaseModle {


        fun login(mobile: String, captcha: String, padSn: String): Flowable<LoginDTO>

        fun getCode(mobile: String): Flowable<RootResponse>


    }

    interface View : CommonDataLoadView {
        fun timeChange(time: String)
    }

    interface Presenter : BasePresenter<View> {
        //        void doGetAccessToken();

        fun doTimeChange()

        fun doLogin(mobile: String, captcha: String, padSn: String)

        fun doGetCode(mobile: String)
    }
}
