package com.wujia.businesslib.event;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-31
 * description ：
 */
public class EventMemberChange extends AbsEventEntiry<EventMemberChange> {

    public EventMemberChange() {

    }

    public EventMemberChange(IMiessageInvoke<EventMemberChange> invoke) {
        super(invoke);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(EventMemberChange event) {
        invoke(event);
    }
}
