package com.jingxi.smartlife.pad.mvp.home.contract

import com.jingxi.smartlife.pad.mvp.MainAppApiService
import com.jingxi.smartlife.pad.mvp.home.data.HomeRecBean
import com.jingxi.smartlife.pad.mvp.home.data.HomeUserInfoBean
import com.jingxi.smartlife.pad.mvp.home.data.LockADBean
import com.jingxi.smartlife.pad.mvp.home.data.WeatherInfoBean
import com.wujia.businesslib.base.BaseModel
import com.wujia.businesslib.data.ApiResponse
import com.wujia.businesslib.data.CardDetailBean
import com.wujia.lib_common.base.RootResponse
import com.wujia.lib_common.data.network.RxUtil

import io.reactivex.Flowable

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-27
 * description ：
 */
class HomeModel : BaseModel(), HomeContract.Model {


    override val quickCard: Flowable<HomeRecBean>
        get() = mHttpHelper.create(MainAppApiService::class.java)!!.quickCard.compose(RxUtil.rxSchedulerHelper())

    override val userQuickCard: Flowable<HomeRecBean>
        get() = mHttpHelper.create(MainAppApiService::class.java)!!.userQuickCard.compose(RxUtil.rxSchedulerHelper())

    override val weather: Flowable<WeatherInfoBean>
        get() = mHttpHelper.create(MainAppApiService::class.java)!!.weather.compose(RxUtil.rxSchedulerHelper())

    override val screenSaverByCommunityId: Flowable<LockADBean>
        get() = mHttpHelper.create(MainAppApiService::class.java)!!.screenSaverByCommunityId.compose(RxUtil.rxSchedulerHelper())

    fun getCardDetail(cardId: String): Flowable<ApiResponse<CardDetailBean>> {
        return mHttpHelper.create(MainAppApiService::class.java)!!.getCardDetail(cardId).compose(RxUtil.rxSchedulerHelper())

    }

    override fun addUserQuickCard(quickCardId: String): Flowable<RootResponse> {
        return mHttpHelper.create(MainAppApiService::class.java)!!.addUserQuickCard(quickCardId).compose(RxUtil.rxSchedulerHelper())

    }

    override fun removeUserQuickCard(quickCardId: String): Flowable<RootResponse> {
        return mHttpHelper.create(MainAppApiService::class.java)!!.removeUserQuickCard(quickCardId).compose(RxUtil.rxSchedulerHelper())
    }

    override fun getHomeUserInfo(key: String): Flowable<HomeUserInfoBean> {
        return mHttpHelper.create(MainAppApiService::class.java)!!.getHomeUserInfo(key).compose(RxUtil.rxSchedulerHelper())
    }


}
