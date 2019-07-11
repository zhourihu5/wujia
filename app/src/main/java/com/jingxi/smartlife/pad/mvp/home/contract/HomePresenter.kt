package com.jingxi.smartlife.pad.mvp.home.contract

import com.jingxi.smartlife.pad.mvp.home.data.HomeRecBean
import com.jingxi.smartlife.pad.mvp.home.data.HomeUserInfoBean
import com.jingxi.smartlife.pad.mvp.home.data.WeatherInfoBean
import com.wujia.businesslib.base.RxPresenter
import com.wujia.lib_common.base.RootResponse
import com.wujia.lib_common.data.network.SimpleRequestSubscriber
import com.wujia.lib_common.data.network.exception.ApiException

import java.util.concurrent.TimeUnit

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-27
 * description ：
 */
class HomePresenter : RxPresenter<HomeContract.View>(), HomeContract.Presenter {


    private val mModel: HomeModel

    init {
        this.mModel = HomeModel()
    }

    override fun getQuickCard() {

        addSubscribe(mModel.quickCard.subscribeWith(object : SimpleRequestSubscriber<HomeRecBean>(mView, SimpleRequestSubscriber.ActionConfig(true, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            override fun onResponse(response: HomeRecBean) {
                super.onResponse(response)
                if (response.isSuccess) {
                    mView.onDataLoadSucc(REQUEST_CDOE_GET_CARD_OTHER, response)
                }
            }

            override fun onFailed(apiException: ApiException) {
                super.onFailed(apiException)
                mView.onDataLoadFailed(REQUEST_CDOE_GET_CARD_OTHER, apiException)
            }
        }))

    }

    override fun getWeather() {
        addSubscribe(Flowable.interval(0, (60 * 60).toLong(), TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    addSubscribe(mModel.weather.subscribeWith(object : SimpleRequestSubscriber<WeatherInfoBean>(mView, SimpleRequestSubscriber.ActionConfig(false, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
                        override fun onResponse(response: WeatherInfoBean) {
                            super.onResponse(response)
                            if (response.isSuccess) {
                                mView.onDataLoadSucc(REQUEST_CDOE_WEATHER, response)
                            }
                        }

                        override fun onFailed(apiException: ApiException) {
                            super.onFailed(apiException)
                            mView.onDataLoadFailed(REQUEST_CDOE_WEATHER, apiException)
                        }
                    }))
                })

    }

    override fun getHomeUserInfo(key: String) {
        addSubscribe(mModel.getHomeUserInfo(key).subscribeWith(object : SimpleRequestSubscriber<HomeUserInfoBean>(mView, SimpleRequestSubscriber.ActionConfig(false, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            override fun onResponse(response: HomeUserInfoBean) {
                super.onResponse(response)
                if (response.isSuccess) {
                    mView.onDataLoadSucc(REQUEST_CDOE_HOME_USER, response)
                }
            }

            override fun onFailed(apiException: ApiException) {
                super.onFailed(apiException)
                mView.onDataLoadFailed(REQUEST_CDOE_HOME_USER, apiException)
            }
        }))

    }

    override fun getUserQuickCard() {
        addSubscribe(mModel.userQuickCard.subscribeWith(object : SimpleRequestSubscriber<HomeRecBean>(mView, SimpleRequestSubscriber.ActionConfig(true, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            override fun onResponse(response: HomeRecBean) {
                super.onResponse(response)
                if (response.isSuccess) {
                    mView.onDataLoadSucc(REQUEST_CDOE_GET_CARD_MY, response)
                }
            }

            override fun onFailed(apiException: ApiException) {
                super.onFailed(apiException)
                mView.onDataLoadFailed(REQUEST_CDOE_GET_CARD_MY, apiException)
            }
        }))
    }

    override fun addUserQuickCard(quickCardId: String) {
        addSubscribe(mModel.addUserQuickCard(quickCardId).subscribeWith(object : SimpleRequestSubscriber<RootResponse>(mView, SimpleRequestSubscriber.ActionConfig(true, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            override fun onResponse(response: RootResponse) {
                super.onResponse(response)
                if (response.isSuccess) {
                    mView.onDataLoadSucc(REQUEST_CDOE_ADD_CARD, response)
                }
            }

            override fun onFailed(apiException: ApiException) {
                super.onFailed(apiException)
                mView.onDataLoadFailed(REQUEST_CDOE_ADD_CARD, apiException)
            }
        }))
    }

    override fun removeUserQuickCard(quickCardId: String) {
        addSubscribe(mModel.removeUserQuickCard(quickCardId).subscribeWith(object : SimpleRequestSubscriber<RootResponse>(mView, SimpleRequestSubscriber.ActionConfig(true, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            override fun onResponse(response: RootResponse) {
                super.onResponse(response)
                if (response.isSuccess) {
                    mView.onDataLoadSucc(REQUEST_CDOE_REMOVE_CARD, response)
                }
            }

            override fun onFailed(apiException: ApiException) {
                super.onFailed(apiException)
                mView.onDataLoadFailed(REQUEST_CDOE_REMOVE_CARD, apiException)
            }
        }))
    }

    companion object {

        val REQUEST_CDOE_GET_CARD_MY = 1
        val REQUEST_CDOE_GET_CARD_OTHER = 2
        val REQUEST_CDOE_ADD_CARD = 3
        val REQUEST_CDOE_REMOVE_CARD = 4
        val REQUEST_CDOE_WEATHER = 5
        val REQUEST_CDOE_MESSAGE = 6
        val REQUEST_CDOE_SCREEN_AD = 7
        val REQUEST_CDOE_HOME_USER = 8
    }

}
