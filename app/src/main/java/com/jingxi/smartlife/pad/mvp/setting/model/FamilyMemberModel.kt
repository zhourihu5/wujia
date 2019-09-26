package com.jingxi.smartlife.pad.mvp.setting.model

import com.jingxi.smartlife.pad.mvp.MainAppApiService
import com.jingxi.smartlife.pad.mvp.home.data.HomeUserInfoBean
import com.wujia.businesslib.base.BaseModel
import com.wujia.businesslib.data.ApiResponse
import com.wujia.lib_common.data.network.RxUtil

import io.reactivex.Flowable

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-26
 * description ：
 */
class FamilyMemberModel : BaseModel() {

    fun addFamilyMember(userName: String, familyId: String): Flowable<ApiResponse<String>> {
        return mHttpHelper.create(MainAppApiService::class.java)!!.addFamilyMember(userName, familyId).compose(RxUtil.rxSchedulerHelper())
    }

    fun getFamilyMemberList(familyId: String): Flowable<ApiResponse<List<HomeUserInfoBean.DataBean.UserInfoListBean>>> {
        return mHttpHelper.create(MainAppApiService::class.java)!!.getFamilyMemberList(familyId).compose(RxUtil.rxSchedulerHelper())
    }


}
