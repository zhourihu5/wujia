package com.abctime.businesslib.event;

/**
 * description: 消息总线接口
 * author: KisenHuang
 * email: KisenHuang@163.com
 * time: 2018/6/4 下午2:17
 */

public interface IMessageInvoke<T> {
    void eventBus(T event);
}
