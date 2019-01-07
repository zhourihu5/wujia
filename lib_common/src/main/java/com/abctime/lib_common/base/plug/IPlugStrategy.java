package com.abctime.lib_common.base.plug;

import android.view.View;

/**
 * Created by KisenHuang on 2018/5/30.
 * 插件策略接口
 */

public interface IPlugStrategy {

    /**
     * 将标题栏和界面布局绑定
     * @param content 界面布局
     * @param onClickListener 点击事件
     * @return 绑定后布局，用于代替content填充
     */
    View getContentWithTitle(View content, View.OnClickListener onClickListener);

}
