package com.abctime.lib.viewdelegate.title;

import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.view.View;


/**
 * Created by KisenHuang on 2018/5/29.
 * 标题代理工具方法接口
 */

public interface ITitle {
    //设置标题
    void setTitle(String text);

    void setTitle(@StringRes int resId);

    //获取标题布局
    @LayoutRes
    int getLayoutId();

    //设置返回点击事件
    void setBackListener(View.OnClickListener listener);

    /**
     * 布局填充完毕回调，一般与执行UI初始化
     *
     * @param title 标题布局
     */
    void inflateFinished(View title);

    /**
     * 布局填充时需要添加右侧菜单布局时调用。
     * @param group 右侧菜单布局
     */
    void inflateRightMenu(View group);
}
