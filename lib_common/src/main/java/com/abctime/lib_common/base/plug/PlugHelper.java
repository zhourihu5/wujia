package com.abctime.lib_common.base.plug;

import android.view.View;

/**
 * Created by KisenHuang on 2018/5/30.
 * 插件助手类
 */

public class PlugHelper {

    /**
     * 添加视图方法
     * @param titlePlug 标题策略插件
     * @param content 布局内容
     * @param onClickListener 点击事件监听
     * @return 添加后布局
     */
    public static View setupTitle(ITitlePlug titlePlug, View content, View.OnClickListener onClickListener) {
        return titlePlug.getTitlePlug().getContentWithTitle(content, onClickListener);
    }
}
