package com.wujia.businesslib.event

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-31
 * description ：
 */
abstract class AbsEventEntiry<T> {

    private lateinit var mInvoke: IMiessageInvoke<T>

    constructor() {}

    constructor(mInvoke: IMiessageInvoke<T>) {
        this.mInvoke = mInvoke
    }

    abstract fun onEventBus(t: T)

    protected operator fun invoke(t: T) {
        mInvoke?.eventBus(t)
    }
}
