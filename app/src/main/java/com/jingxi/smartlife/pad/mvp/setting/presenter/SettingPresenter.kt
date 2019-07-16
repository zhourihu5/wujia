package com.jingxi.smartlife.pad.mvp.setting.presenter

import com.jingxi.smartlife.pad.mvp.setting.contract.SettingContract
import com.wujia.businesslib.base.RxPresenter
import com.wujia.businesslib.data.VersionBean
import com.wujia.businesslib.model.BusModel
import com.wujia.lib_common.data.network.SimpleRequestSubscriber
import com.wujia.lib_common.data.network.exception.ApiException

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-26
 * description ：
 */
class SettingPresenter : RxPresenter<SettingContract.View>(), SettingContract.Presenter {

    private val mModel: BusModel = BusModel()


    override fun checkVersion() {

        addSubscribe(mModel.checkVersion().subscribeWith(object : SimpleRequestSubscriber<VersionBean>(mView, ActionConfig(false, SHOWERRORMESSAGE)) {
            override fun onResponse(response: VersionBean) {
                super.onResponse(response)
                mView?.onDataLoadSucc(0, response)
            }

            override fun onFailed(apiException: ApiException) {
                mView?.onDataLoadFailed(0, apiException)
                super.onFailed(apiException)
            }
        }))
    }
}
