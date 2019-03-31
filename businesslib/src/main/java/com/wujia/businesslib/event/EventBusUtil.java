package com.wujia.businesslib.event;

import org.greenrobot.eventbus.EventBus;
/**
* author ：shenbingkai@163.com
* date ：2019-03-31 01:30
* description ：
*/
public class EventBusUtil {

    /**
     * 普通事件通知方法
     *
     * @param body 消息
     */
    public static void post(AbsEventEntiry body) {
        EventBus.getDefault().post(body);
    }

    /**
     * 粘性事件通知方法
     *
     * @param body 消息
     */
    public static void postSticky(AbsEventEntiry body) {
        EventBus.getDefault().postSticky(body);
    }

    /**
     * 注册事件监听类
     *
     * @param o 被监听对象
     */
    public static void register(AbsEventEntiry o) {
        if (!EventBus.getDefault().isRegistered(o))
            EventBus.getDefault().register(o);
    }

    /**
     * 注销事件监听类
     *
     * @param o 被监听对象
     */
    public static void unregister(AbsEventEntiry o) {
        EventBus.getDefault().unregister(o);
    }


}
