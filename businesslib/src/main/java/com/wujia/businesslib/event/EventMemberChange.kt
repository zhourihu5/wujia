package com.wujia.businesslib.event

import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-31
 * description ：
 */
class EventMemberChange : AbsEventEntiry<EventMemberChange> {

    constructor() {

    }

    constructor(invoke: IMiessageInvoke<EventMemberChange>) : super(invoke) {}

    @Subscribe(threadMode = ThreadMode.MAIN)
    override fun onEventBus(event: EventMemberChange) {
        invoke(event)
    }
}
