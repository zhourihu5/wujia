package com.wujia.lib_common.base

import com.wujia.lib_common.data.network.exception.ApiException

/**
 * Created by xmren on 2018/5/29.
 */

interface CommonDataLoadView : BaseView {
    /**
     * @param requestCode 区分不同的调用
     * @param object      根据不同的调用返回对应的请求对象
     */
    fun onDataLoadSucc(requestCode: Int, `object`: Any)

    fun onDataLoadFailed(requestCode: Int, apiException: ApiException)
}
