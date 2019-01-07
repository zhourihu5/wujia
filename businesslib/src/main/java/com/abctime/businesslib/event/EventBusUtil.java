package com.abctime.businesslib.event;

import org.greenrobot.eventbus.EventBus;

/**
 * description: 事件总线包装类
 * author: KisenHuang
 * email: KisenHuang@163.com
 * time: 2018/6/4 下午1:44
 */

public class EventBusUtil {

    /**
     * 普通事件通知方法
     *
     * @param body 消息
     */
    public static void post(AbsEventProxyEntity body) {
        EventBus.getDefault().post(body);
    }

    /**
     * 粘性事件通知方法
     *
     * @param body 消息
     */
    public static void postSticky(AbsEventProxyEntity body) {
        EventBus.getDefault().postSticky(body);
    }

    /**
     * 注册事件监听类
     *
     * @param o 被监听对象
     */
    public static void register(AbsEventProxyEntity o) {
        if (!EventBus.getDefault().isRegistered(o))
            EventBus.getDefault().register(o);
    }

    /**
     * 注销事件监听类
     *
     * @param o 被监听对象
     */
    public static void unregister(AbsEventProxyEntity o) {
        EventBus.getDefault().unregister(o);
    }


}
