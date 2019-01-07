package com.abctime.businesslib.wechat;

import com.abctime.businesslib.event.AbsEventProxyEntity;
import com.abctime.businesslib.event.IMessageInvoke;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by four on 2018/7/4.
 */

public class EventWeChat extends AbsEventProxyEntity<EventWeChat> {

    public int resultCode = -1;

    public EventWeChat(int resultCode) {
        this.resultCode = resultCode;
    }

    public EventWeChat(IMessageInvoke<EventWeChat> invoke) {
        super(invoke);
    }

    @Subscribe
    public void onEventBus(EventWeChat eventWeChat) {

        invoke(eventWeChat);
    }
}
