package com.jingxi.smartlife.pad.safe.mvp.contract

import com.jingxi.smartlife.pad.safe.mvp.SafeApiService
import com.wujia.businesslib.base.BaseModel
import com.wujia.lib_common.base.RootResponse
import com.wujia.lib_common.data.network.RxUtil
import io.reactivex.Flowable

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-27
 * description ：
 */
class SafeModel : BaseModel(), SafeContract.Model {
    override fun openDoor(fid: String): Flowable<RootResponse> {
        return mHttpHelper.create(SafeApiService::class.java)!!.openDoor(fid).compose(RxUtil.rxSchedulerHelper())
    }
}
