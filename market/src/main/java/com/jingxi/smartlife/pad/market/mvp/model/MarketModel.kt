package com.jingxi.smartlife.pad.market.mvp.model

import com.jingxi.smartlife.pad.market.mvp.MarketApiService
import com.jingxi.smartlife.pad.market.mvp.data.*
import com.wujia.businesslib.base.BaseModel
import com.wujia.businesslib.base.DataManager
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
    fun getGroupBuyList( pageIndex: Int, pageSize: Int): Flowable<ApiResponse<GroupBuyVo>> {
        return mHttpHelper.create(MarketApiService::class.java)!!.getGroupBuyList( pageIndex, pageSize,DataManager.communtityId!!).compose(RxUtil.rxSchedulerHelper())
    }
    fun getGroupBuyOtherList( ): Flowable<ApiResponse<GroupBuyVo>> {
        return mHttpHelper.create(MarketApiService::class.java)!!.getGroupBuyOtherList( DataManager.communtityId!!).compose(RxUtil.rxSchedulerHelper())
    }

    fun getGroupBuyDetail(id: String?): Flowable<ApiResponse<GroupBuyDetailVo>> {
        return mHttpHelper.create(MarketApiService::class.java)!!.getGroupBuyDetail( id).compose(RxUtil.rxSchedulerHelper())

    }

    fun generateOrderConfirmQrCode(id: String): Flowable<ApiResponse<String>>  {
        return mHttpHelper.create(MarketApiService::class.java)!!.generateQrCodeOrderConfirm( id).compose(RxUtil.rxSchedulerHelper())
    }
    fun generateOrderDetailQrCode(id: String): Flowable<ApiResponse<String>>  {
        return mHttpHelper.create(MarketApiService::class.java)!!.generateQrCodeOrderDetail( id).compose(RxUtil.rxSchedulerHelper())
    }

    fun getOrderList(status: String, page: Int, pageSize: Int): Flowable<ApiResponse<OrderVo>> {
        return mHttpHelper.create(MarketApiService::class.java)!!.getOrderList( status,page,pageSize).compose(RxUtil.rxSchedulerHelper())
    }

    fun getOrderDetail(id: String?): Flowable<ApiResponse<OrderDetailVo>> {
        return mHttpHelper.create(MarketApiService::class.java)!!
                .getOrderDetail(id).compose(RxUtil.rxSchedulerHelper())
    }

    fun rereiveOrder(id: String?): Flowable<ApiResponse<Any>> {
        return mHttpHelper.create(MarketApiService::class.java)!!
                .rereiveOrder(id).compose(RxUtil.rxSchedulerHelper())
    }

    fun cancelOrder(id: String?): Flowable<ApiResponse<Any>>
    {
        return mHttpHelper.create(MarketApiService::class.java)!!
                .cancelOrder(id).compose(RxUtil.rxSchedulerHelper())
    }

}
