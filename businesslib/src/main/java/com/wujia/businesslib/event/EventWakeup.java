package com.wujia.businesslib.event;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-31
 * description ：
 */
public class EventWakeup extends AbsEventEntiry<EventWakeup> {


    public EventWakeup() {

    }

    public EventWakeup(IMiessageInvoke<EventWakeup> invoke) {
        super(invoke);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(EventWakeup event) {
        invoke(event);
    }
}
