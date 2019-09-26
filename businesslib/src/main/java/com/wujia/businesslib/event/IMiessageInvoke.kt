package com.wujia.businesslib.event

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-31
 * description ：
 */
interface IMiessageInvoke<T> {

    fun eventBus(event: T)
}
