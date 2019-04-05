package com.wujia.businesslib.event;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-31
 * description ：
 */
public class EventSubscription extends AbsEventEntiry<EventSubscription> {

    public EventSubscription() {

    }

    public EventSubscription(IMiessageInvoke<EventSubscription> invoke) {
        super(invoke);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(EventSubscription event) {
        invoke(event);
    }
}
