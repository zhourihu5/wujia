package com.wujia.businesslib.event;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-31
 * description ：
 */
public class EventCardChange extends AbsEventEntiry<EventCardChange> {

    public EventCardChange() {

    }

    public EventCardChange(IMiessageInvoke<EventCardChange> invoke) {
        super(invoke);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(EventCardChange event) {
        invoke(event);
    }
}
