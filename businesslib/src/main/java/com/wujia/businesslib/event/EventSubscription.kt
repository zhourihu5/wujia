package com.wujia.businesslib.event

import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-31
 * description ：
 */
class EventSubscription : AbsEventEntiry<EventSubscription> {
    var eventType: Int = 0

    var type: Int = 0
    //    private EventSubscription(){
    //
    //    }

    constructor(type: Int) {
        this.type = type
    }

    constructor(invoke: IMiessageInvoke<EventSubscription>) : super(invoke) {}

    @Subscribe(threadMode = ThreadMode.MAIN)
    override fun onEventBus(event: EventSubscription) {
        invoke(event)
    }

    companion object {
        val TYPE_FIND = 1
        val TYPE_GOV = 2

        val TYPE_NOTIFY = 99

        var PUSH_NOTIFY = 99
    }
}
