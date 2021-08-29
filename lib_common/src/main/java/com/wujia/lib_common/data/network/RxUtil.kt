package com.wujia.lib_common.data.network

import com.google.gson.reflect.TypeToken
import com.wujia.lib_common.utils.GsonUtil
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object RxUtil {

    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
    </T> */
    fun <T> rxSchedulerHelper(): FlowableTransformer<T, T> {    //compose简化线程

        return FlowableTransformer { observable ->
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    /**
     * 处理Json解析
     */
    fun <F, T> rxGsonFormat(typeToken: TypeToken<T>): FlowableTransformer<F, T> {

        return FlowableTransformer { observable -> observable.map { f -> GsonUtil.GsonToType(GsonUtil.GsonString(f!!)!!, typeToken) } }
    }


}
