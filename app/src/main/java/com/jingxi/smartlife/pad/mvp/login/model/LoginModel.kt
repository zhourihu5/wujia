package com.jingxi.smartlife.pad.mvp.login.model

import com.jingxi.smartlife.pad.mvp.MainAppApiService
import com.jingxi.smartlife.pad.mvp.login.contract.LoginContract
import com.wujia.businesslib.base.BaseModel
import com.wujia.businesslib.data.LoginDTO
import com.wujia.lib_common.base.RootResponse
import com.wujia.lib_common.data.network.RxUtil
import com.wujia.lib_common.utils.SystemUtil

import io.reactivex.Flowable

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-27
 * description ：
 */
class LoginModel : BaseModel(), LoginContract.Model {


    override fun login(mobile: String, captcha: String, padSn: String): Flowable<LoginDTO> {
        return mHttpHelper.create(MainAppApiService::class.java)!!.login(mobile, captcha, padSn).compose(RxUtil.rxSchedulerHelper())
    }

    override fun getCode(mobile: String): Flowable<RootResponse> {
        return mHttpHelper.create(MainAppApiService::class.java)!!.getCode(mobile, SystemUtil.getSerialNum()!!).compose(RxUtil.rxSchedulerHelper())
    }


}
