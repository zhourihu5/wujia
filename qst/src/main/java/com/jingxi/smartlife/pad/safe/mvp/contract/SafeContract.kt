package com.jingxi.smartlife.pad.safe.mvp.contract

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
interface SafeContract {
    interface Model : IBaseModle {

        fun openDoor(fid:String): Flowable<RootResponse>

    }

    interface View : CommonDataLoadView

    interface Presenter : BasePresenter<View> {

        fun openDoor(fid:String)

    }
}
