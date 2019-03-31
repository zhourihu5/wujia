package com.wujia.businesslib.event;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-31
 * description ：
 */
public abstract class AbsEventEntiry<T> {

    public AbsEventEntiry() {
    }

    public AbsEventEntiry(IMiessageInvoke<T> mInvoke) {
        this.mInvoke = mInvoke;
    }

    private IMiessageInvoke mInvoke;

    public abstract void onEventBus(T t);

    protected void invoke(T t) {
        if (mInvoke != null)
            mInvoke.eventBus(t);
    }
}
