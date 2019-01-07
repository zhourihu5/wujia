package com.abctime.lib.viewdelegate.menu;

/**
 * Created by KisenHuang on 2018/5/30.
 * 标题栏右侧菜单提供者
 * 子类Activity如果需要自定义右侧菜单，需要实现该接口
 */

public interface IRightMenuProvider {

    /**
     * 在该方法中实例化RightMenuGroup，并添加menu菜单
     */
    RightMenuGroup getRightMenuGroup();
}
