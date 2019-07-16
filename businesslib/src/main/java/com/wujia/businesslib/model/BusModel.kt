package com.wujia.businesslib.model

import com.wujia.businesslib.BusApiService
import com.wujia.businesslib.base.BaseModel
import com.wujia.businesslib.data.ApiResponse
import com.wujia.businesslib.data.MsgDto
import com.wujia.businesslib.data.VersionBean
import com.wujia.businesslib.data.VersionUpdate
import com.wujia.lib_common.data.network.RxUtil

import io.reactivex.Flowable

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-27
 * description ：
 */
class BusModel : BaseModel() {

    val isUnReadMessage: Flowable<ApiResponse<Boolean>>
        get() = mHttpHelper.create(BusApiService::class.java)!!.isUnReadMsg.compose(RxUtil.rxSchedulerHelper())


    fun getMsg(familyId: String, type: String, status: String, pageNo: Int, pageSize: Int): Flowable<ApiResponse<MsgDto>> {
        return mHttpHelper.create(BusApiService::class.java)!!.getMsg(familyId, type, status, pageNo, pageSize).compose(RxUtil.rxSchedulerHelper())

    }

    fun getTop3UnReadMsg(familyId: String): Flowable<ApiResponse<List<MsgDto.ContentBean>>> {
        return mHttpHelper.create(BusApiService::class.java)!!.getTop3UnReadMsg(familyId).compose(RxUtil.rxSchedulerHelper())

    }

    fun readMsg(id: String): Flowable<ApiResponse<Any>> {
        return mHttpHelper.create(BusApiService::class.java)!!.readMsg(id).compose(RxUtil.rxSchedulerHelper())

    }

    /**
     * /v1/system/updateVer
     *
     * private String versionName;
     * private String key;
     * private String versionCode;
     * @return
     */
    fun updateVer(versionName: String, key: String, versionCode: String): Flowable<ApiResponse<Any>> {
        val versionUpdate = VersionUpdate()
        versionUpdate.key = key
        versionUpdate.versionCode = versionCode
        versionUpdate.versionName = versionName

        return mHttpHelper.create(BusApiService::class.java)!!.updateVer(versionName, key, versionCode).compose(RxUtil.rxSchedulerHelper())
        //        return mHttpHelper.create(BusApiService.class).updateVer(versionUpdate).compose(RxUtil.<ApiResponse<Object>>rxSchedulerHelper());

    }

    fun subscribe(serviceId: String, isSubscribe: String): Flowable<ApiResponse<Any>> {
        return mHttpHelper.create(BusApiService::class.java)!!.subscribe(serviceId, isSubscribe).compose(RxUtil.rxSchedulerHelper())

    }

    fun checkVersion(): Flowable<VersionBean> {
        return mHttpHelper.create(BusApiService::class.java)!!.checkVersion().compose(RxUtil.rxSchedulerHelper())
    }


}
