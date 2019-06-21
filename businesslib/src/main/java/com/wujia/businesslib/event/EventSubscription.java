package com.wujia.businesslib.event;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-31
 * description ：
 */
public class EventSubscription extends AbsEventEntiry<EventSubscription> {
    public static final int TYPE_FIND=1;
    public static final int TYPE_GOV=2;


    private int type;

    public int getType() {
        return type;
    }
    //    private EventSubscription(){
//
//    }

    public EventSubscription(int type) {//todo 加个服务类型，判断哪个服务类型的列表数据需要刷新
        this.type=type;
    }

    public EventSubscription(IMiessageInvoke<EventSubscription> invoke) {
        super(invoke);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBus(EventSubscription event) {
        invoke(event);
    }
}
