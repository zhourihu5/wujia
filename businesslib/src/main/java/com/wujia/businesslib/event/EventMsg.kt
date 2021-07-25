package com.wujia.businesslib.event

import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-31
 * description ：
 */
class EventMsg : AbsEventEntiry<EventMsg> {

    var type = 0

    constructor(type: Int) {
        this.type = type
    }

    constructor(invoke: IMiessageInvoke<EventMsg>) : super(invoke)

    @Subscribe(threadMode = ThreadMode.MAIN)
    override fun onEventBus(event: EventMsg) {
        invoke(event)
    }

    companion object {

        const val TYPE_NEW_MSG = 1
        const val TYPE_READ = 2
    }
}
