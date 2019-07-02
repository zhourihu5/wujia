package com.wujia.businesslib.event;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-31
 * description ：
 */
public class EventBaseButtonClick extends AbsEventEntiry<EventBaseButtonClick> {

    public EventBaseButtonClick() {

    }

    public EventBaseButtonClick(IMiessageInvoke<EventBaseButtonClick> invoke) {
        super(invoke);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(EventBaseButtonClick event) {
        invoke(event);
    }
}
