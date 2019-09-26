package com.jingxi.smartlife.pad.mvp.setting.contract

import com.wujia.businesslib.data.VersionBean
import com.wujia.lib_common.base.BasePresenter
import com.wujia.lib_common.base.CommonDataLoadView
import com.wujia.lib_common.base.IBaseModle

import io.reactivex.Flowable

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-26
 * description ：
 */
interface SettingContract {

    interface View : CommonDataLoadView

    interface Presenter : BasePresenter<View> {

        fun checkVersion()

    }
}
