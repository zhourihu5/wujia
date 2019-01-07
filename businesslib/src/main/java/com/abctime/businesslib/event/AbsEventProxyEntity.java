package com.abctime.businesslib.event;


/**
 * description: 事件代理抽象类
 * author: KisenHuang
 * email: KisenHuang@163.com
 * time: 2018/6/4 下午3:16
 * <p>
 * 自定义事件，继承该抽象类，完成onEventBus实现并标记 @Subscribe 注解，定义线程等
 * </p>
 */

public abstract class AbsEventProxyEntity<T> {

    private IMessageInvoke<T> mInvoke;

    public AbsEventProxyEntity(IMessageInvoke<T> invoke) {
        mInvoke = invoke;
    }

    public AbsEventProxyEntity() {
    }

    /**
     * description: 子类实现，需要@Subscribe注解标注
     * author: KisenHuang
     */
    public abstract void onEventBus(T t);

    protected void invoke(T t) {
        if (mInvoke != null)
            mInvoke.eventBus(t);
    }
}
