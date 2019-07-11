package com.jingxi.smartlife.pad.mvp.home.contract

import com.jingxi.smartlife.pad.mvp.home.data.HomeRecBean
import com.jingxi.smartlife.pad.mvp.home.data.HomeUserInfoBean
import com.jingxi.smartlife.pad.mvp.home.data.LockADBean
import com.jingxi.smartlife.pad.mvp.home.data.WeatherInfoBean
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
interface HomeContract {
    interface Model : IBaseModle {

        val quickCard: Flowable<HomeRecBean>

        val userQuickCard: Flowable<HomeRecBean>

        val weather: Flowable<WeatherInfoBean>

        val screenSaverByCommunityId: Flowable<LockADBean>

        fun addUserQuickCard(quickCardId: String): Flowable<RootResponse>

        fun removeUserQuickCard(quickCardId: String): Flowable<RootResponse>

        fun getHomeUserInfo(key: String): Flowable<HomeUserInfoBean>

    }

    interface View : CommonDataLoadView

    interface Presenter : BasePresenter<View> {

        fun getQuickCard()

        fun getWeather()

        fun getHomeUserInfo(key: String)

        fun getUserQuickCard()

        fun addUserQuickCard(quickCardId: String)

        fun removeUserQuickCard(quickCardId: String)

    }
}
