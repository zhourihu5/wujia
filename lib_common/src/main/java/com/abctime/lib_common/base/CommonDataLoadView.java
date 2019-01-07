package com.abctime.lib_common.base;

import com.abctime.lib_common.data.network.exception.ApiException;

/**
 * Created by xmren on 2018/5/29.
 */

public interface CommonDataLoadView extends BaseView {
    /**
     * @param requestCode 区分不同的调用
     * @param object      根据不同的调用返回对应的请求对象
     */
    public void onDataLoadSucc(int requestCode, Object object);

    public void onDataLoadFailed(int requestCode, ApiException apiException);
}
