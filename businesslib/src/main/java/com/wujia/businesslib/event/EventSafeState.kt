package com.wujia.businesslib.event

import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-31
 * description ：
 */
class EventSafeState : AbsEventEntiry<EventSafeState> {

    var online: Boolean = false

    constructor() {

    }

    constructor(online: Boolean) {
        this.online = online
    }

    constructor(invoke: IMiessageInvoke<EventSafeState>) : super(invoke) {}

    @Subscribe(threadMode = ThreadMode.MAIN)
    override fun onEventBus(event: EventSafeState) {
        invoke(event)
    }
}
