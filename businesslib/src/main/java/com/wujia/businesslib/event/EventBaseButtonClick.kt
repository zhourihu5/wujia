package com.wujia.businesslib.event

import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-31
 * description ：
 */
class EventBaseButtonClick : AbsEventEntiry<EventBaseButtonClick> {

    lateinit var keyCmd: String

    constructor(kButtonPickup: String) {
        this.keyCmd = kButtonPickup
    }

    constructor(invoke: IMiessageInvoke<EventBaseButtonClick>) : super(invoke) {}

    @Subscribe(threadMode = ThreadMode.MAIN)
    override fun onEventBus(event: EventBaseButtonClick) {
        invoke(event)
    }
}
