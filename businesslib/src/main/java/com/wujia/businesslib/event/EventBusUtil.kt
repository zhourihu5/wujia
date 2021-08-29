package com.wujia.businesslib.event

import org.greenrobot.eventbus.EventBus

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-31 01:30
 * description ：
 */
object EventBusUtil {

    /**
     * 普通事件通知方法
     *
     * @param body 消息
     */
    fun post(body: AbsEventEntiry<*>) {
        EventBus.getDefault().post(body)
    }

    /**
     * 注册事件监听类
     *
     * @param o 被监听对象
     */
    fun register(o: AbsEventEntiry<*>) {
        if (!EventBus.getDefault().isRegistered(o))
            EventBus.getDefault().register(o)
    }

    /**
     * 注销事件监听类
     *
     * @param o 被监听对象
     */
    fun unregister(o: AbsEventEntiry<*>) {
        EventBus.getDefault().unregister(o)
    }


}
