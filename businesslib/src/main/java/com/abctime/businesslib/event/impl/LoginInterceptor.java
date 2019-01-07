package com.abctime.businesslib.event.impl;

import com.abctime.businesslib.LifecycleEventManager;
import com.abctime.businesslib.event.EventBusUtil;
import com.abctime.businesslib.event.IMessageInvoke;

import java.util.ArrayList;
import java.util.List;

/**
 * description:
 * author: KisenHuang
 * email: KisenHuang@163.com
 * time: 2018/7/16 下午3:01
 */

public class LoginInterceptor implements LifecycleEventManager.Interceptor {

    private final EventLogin mEventLogin;
    private List<ILoginStatusNotify> mLoginNotify = new ArrayList<>();

    public LoginInterceptor() {
        mEventLogin = new EventLogin(new IMessageInvoke<EventLogin>() {
            @Override
            public void eventBus(EventLogin event) {
                for (ILoginStatusNotify statusNotify : mLoginNotify) {
                    statusNotify.onUserLoginStatusChanged(event.mUserInfo, event.mEventId);

                }
            }
        });
        EventBusUtil.register(mEventLogin);
    }

    @Override
    public void register(Object object) {
        if (hasEvent(object))
            mLoginNotify.add((ILoginStatusNotify) object);
    }

    @Override
    public void unregister(Object object) {
        if (hasEvent(object))
            mLoginNotify.remove(object);
    }

    private boolean hasEvent(Object target) {
        return target != null && target instanceof ILoginStatusNotify;
    }
}
