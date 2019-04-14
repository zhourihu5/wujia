package com.wujia.businesslib.event;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-31
 * description ：
 */
public class EventMsg extends AbsEventEntiry<EventMsg> {

    public static final int TYPE_NEW_MSG = 1;
    public static final int TYPE_READ = 2;

    public int type = 0;

    public EventMsg() {

    }

    public EventMsg(int type) {
        this.type = type;
    }

    public EventMsg(IMiessageInvoke<EventMsg> invoke) {
        super(invoke);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(EventMsg event) {
        invoke(event);
    }
}
