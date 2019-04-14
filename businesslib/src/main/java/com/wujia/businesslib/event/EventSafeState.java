package com.wujia.businesslib.event;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-31
 * description ：
 */
public class EventSafeState extends AbsEventEntiry<EventSafeState> {

    public boolean online;

    public EventSafeState() {

    }

    public EventSafeState(boolean online) {
        this.online = online;
    }

    public EventSafeState(IMiessageInvoke<EventSafeState> invoke) {
        super(invoke);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(EventSafeState event) {
        invoke(event);
    }
}
