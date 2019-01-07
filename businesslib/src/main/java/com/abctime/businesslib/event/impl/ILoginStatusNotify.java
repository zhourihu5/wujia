package com.abctime.businesslib.event.impl;

import com.abctime.businesslib.data.UserEntity;

/**
 * description:
 * author: KisenHuang
 * email: KisenHuang@163.com
 * time: 2018/7/16 上午11:16
 */

public interface ILoginStatusNotify {

    /**
     * @param userInfo 最新用户数据
     */
    void onUserLoginStatusChanged(UserEntity userInfo, int eventId);
}
