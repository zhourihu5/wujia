package com.wujia.businesslib.event;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-31
 * description ：
 */
public class EventDoorDevice extends AbsEventEntiry<EventDoorDevice> {

    public EventDoorDevice() {

    }

    public EventDoorDevice(IMiessageInvoke<EventDoorDevice> invoke) {
        super(invoke);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(EventDoorDevice event) {
        invoke(event);
    }
}
