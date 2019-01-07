package com.abctime.businesslib.home;

/**
 * description:
 * author: KisenHuang
 * email: KisenHuang@163.com
 * time: 2018/7/19 上午10:58
 */

public interface DrawerStateListener extends DrawerListener {

    void onDrawerOpenFinish(boolean anim);

    void onDrawerCloseFinish(boolean anim);

}
