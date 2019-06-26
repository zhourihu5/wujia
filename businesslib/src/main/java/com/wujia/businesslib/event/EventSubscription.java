package com.wujia.businesslib.event;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-31
 * description ：
 */
public class EventSubscription extends AbsEventEntiry<EventSubscription> {
    public static final int TYPE_FIND = 1;
    public static final int TYPE_GOV = 2;

    public static final int TYPE_NOTIFY = 99;

    public static int PUSH_NOTIFY = 99;
    public int eventType;

    private int type;

    public int getType() {
        return type;
    }
    //    private EventSubscription(){
//
//    }

    public EventSubscription(int type) {
        this.type = type;
    }

    public EventSubscription(IMiessageInvoke<EventSubscription> invoke) {
        super(invoke);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(EventSubscription event) {
        invoke(event);
    }
}
