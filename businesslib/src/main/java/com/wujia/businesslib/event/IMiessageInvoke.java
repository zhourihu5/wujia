package com.wujia.businesslib.event;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-31
 * description ：
 */
public interface IMiessageInvoke<T> {

    void eventBus(T event);
}
