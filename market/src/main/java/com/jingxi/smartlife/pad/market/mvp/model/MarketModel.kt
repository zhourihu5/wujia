package com.jingxi.smartlife.pad.market.mvp.model

import com.jingxi.smartlife.pad.market.mvp.MarketApiService
import com.jingxi.smartlife.pad.market.mvp.data.ServiceDto
import com.wujia.businesslib.base.BaseModel
import com.wujia.businesslib.data.ApiResponse
import com.wujia.lib_common.data.network.RxUtil

import io.reactivex.Flowable

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-27
 * description ：
 */
class MarketModel : BaseModel() {

    fun getServiceList(type: String, pageIndex: Int, pageSize: Int): Flowable<ApiResponse<ServiceDto>> {
        return mHttpHelper.create(MarketApiService::class.java)!!.getServiceList(type, pageIndex, pageSize).compose(RxUtil.rxSchedulerHelper())
    }

}
