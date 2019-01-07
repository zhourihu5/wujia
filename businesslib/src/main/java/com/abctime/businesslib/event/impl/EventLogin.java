package com.abctime.businesslib.event.impl;

import com.abctime.businesslib.data.UserEntity;
import com.abctime.businesslib.event.AbsEventProxyEntity;
import com.abctime.businesslib.event.IMessageInvoke;

import org.greenrobot.eventbus.Subscribe;

/**
 * description:
 * author: KisenHuang
 * email: KisenHuang@163.com
 * time: 2018/7/16 下午2:45
 */

public class EventLogin extends AbsEventProxyEntity<EventLogin> {

    public static final int LOGIN_EVENT_STATE = 1001;
    public static final int LOGIN_EVENT_LOOKLIST = 1003;

    UserEntity mUserInfo;
    int mEventId;

    EventLogin(IMessageInvoke<EventLogin> invoke) {
        super(invoke);
    }

    public EventLogin(UserEntity entity, int eventId) {
        super();
        mUserInfo = entity;
        mEventId = eventId;
    }

    @Subscribe
    @Override
    public void onEventBus(EventLogin eventLogin) {
        invoke(eventLogin);
    }
}
