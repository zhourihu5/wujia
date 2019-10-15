package com.jingxi.smartlife.pad.market.mvp.model

import com.jingxi.smartlife.pad.property.mvp.data.YellowPage
import com.jingxi.smartlife.pad.property.mvp.model.YellowPageService
import com.wujia.businesslib.base.BaseModel
import com.wujia.businesslib.data.ApiResponse
import com.wujia.lib_common.data.network.RxUtil
import io.reactivex.Flowable

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-27
 * description ：
 */
class YellowPageModel : BaseModel() {

    fun getYellowPageList( communityId:Int): Flowable<ApiResponse<List<YellowPage>>> {
        return mHttpHelper.create(YellowPageService::class.java)!!.getYellowPageList(communityId).compose(RxUtil.rxSchedulerHelper())
    }


}
