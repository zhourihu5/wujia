package com.wujia.businesslib.event

import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-31
 * description ：
 */
class EventDoorDevice : AbsEventEntiry<EventDoorDevice> {

    constructor() {

    }

    constructor(invoke: IMiessageInvoke<EventDoorDevice>) : super(invoke) {}

    @Subscribe(threadMode = ThreadMode.MAIN)
    override fun onEventBus(event: EventDoorDevice) {
        invoke(event)
    }
}
