package com.wujia.businesslib.event

import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-31
 * description ：
 */
class EventWakeup : AbsEventEntiry<EventWakeup> {


    constructor() {

    }

    constructor(invoke: IMiessageInvoke<EventWakeup>) : super(invoke) {}

    @Subscribe(threadMode = ThreadMode.MAIN)
    override fun onEventBus(event: EventWakeup) {
        invoke(event)
    }
}
