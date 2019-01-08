package com.wujia.lib.viewdelegate;

/**
 * Created by KisenHuang on 2018/5/29.
 * 视图代理接口
 */

public interface IDelegate<V> {

    /**
     * 设置添加布局样式
     * @param view 要显示视图
     */
    void setView(V view);
}
