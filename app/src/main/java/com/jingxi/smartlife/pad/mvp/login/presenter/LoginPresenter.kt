package com.jingxi.smartlife.pad.mvp.login.presenter

import com.jingxi.smartlife.pad.mvp.login.contract.LoginContract
import com.jingxi.smartlife.pad.mvp.login.model.LoginModel
import com.wujia.businesslib.base.RxPresenter
import com.wujia.businesslib.data.LoginDTO
import com.wujia.lib_common.base.RootResponse
import com.wujia.lib_common.data.network.SimpleRequestSubscriber
import com.wujia.lib_common.data.network.exception.ApiException
import com.wujia.lib_common.utils.DateUtil


/**
 * author ：shenbingkai@163.com
 * date ：2019-01-27
 * description ：
 */
class LoginPresenter : RxPresenter<LoginContract.View>(), LoginContract.Presenter {
    private val mModel: LoginModel

    init {
        this.mModel = LoginModel()
    }


    override fun doTimeChange() {
        mView.timeChange(DateUtil.getCurrentTimeHHMM())
    }

    override fun doLogin(mobile: String, captcha: String, padSn: String) {
        addSubscribe(mModel.login(mobile, captcha, padSn).subscribeWith(object : SimpleRequestSubscriber<LoginDTO>(mView, SimpleRequestSubscriber.ActionConfig(true, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            override fun onResponse(response: LoginDTO) {
                super.onResponse(response)
                if (response.isSuccess) {
                    mView.onDataLoadSucc(REQUEST_CDOE_LOGIN, response)
                }
            }

            override fun onFailed(apiException: ApiException) {
                super.onFailed(apiException)
                mView.onDataLoadFailed(REQUEST_CDOE_LOGIN, apiException)
            }
        }))

    }

    override fun doGetCode(mobile: String) {
        addSubscribe(mModel.getCode(mobile).subscribeWith(object : SimpleRequestSubscriber<RootResponse>(mView, SimpleRequestSubscriber.ActionConfig(true, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            override fun onResponse(response: RootResponse) {
                super.onResponse(response)
                if (response.isSuccess) {
                    mView.onDataLoadSucc(REQUEST_CDOE_GET_CODE, response)
                }
            }

            override fun onFailed(apiException: ApiException) {
                super.onFailed(apiException)
                mView.onDataLoadFailed(REQUEST_CDOE_GET_CODE, apiException)
            }
        }))

    }

    companion object {
        val REQUEST_CDOE_LOGIN = 1
        val REQUEST_CDOE_GET_CODE = 2
        val REQUEST_CDOE_TIMER = 3
        val REQUEST_CDOE_TOKEN = 4
    }
}
