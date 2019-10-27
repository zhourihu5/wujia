package com.jingxi.smartlife.pad.mvp.home.contract

import com.wujia.businesslib.base.RxPresenter
import com.wujia.lib_common.base.RootResponse
import com.wujia.lib_common.data.network.SimpleRequestSubscriber
import com.wujia.lib_common.data.network.exception.ApiException

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-27
 * description ：
 */
class SafePresenter : RxPresenter<SafeContract.View>(), SafeContract.Presenter {


    private val mModel: SafeModel = SafeModel()

//    override fun getQuickCard() {
//
//        addSubscribe(mModel.quickCard.subscribeWith(object : SimpleRequestSubscriber<HomeRecBean>(mView, ActionConfig(true, SHOWERRORMESSAGE)) {
//            override fun onResponse(response: HomeRecBean) {
//                super.onResponse(response)
//                if (response.isSuccess) {
//                    mView?.onDataLoadSucc(REQUEST_CDOE_GET_CARD_OTHER, response)
//                }
//            }
//
//            override fun onFailed(apiException: ApiException) {
//                super.onFailed(apiException)
//                mView?.onDataLoadFailed(REQUEST_CDOE_GET_CARD_OTHER, apiException)
//            }
//        }))
//
//    }

    override fun openDoor(fid:String) {
        addSubscribe(mModel.openDoor(fid).subscribeWith(object : SimpleRequestSubscriber<RootResponse>(mView, ActionConfig(true, SHOWERRORMESSAGE)) {
            override fun onResponse(response: RootResponse) {
                super.onResponse(response)
                if (response.isSuccess) {
                    mView?.onDataLoadSucc(100, response)
                }
            }

            override fun onFailed(apiException: ApiException) {
                super.onFailed(apiException)
                mView?.onDataLoadFailed(100, apiException)
            }
        }))
    }

}
