package com.wujia.intellect.terminal.host;

import com.wujia.businesslib.base.BaseApplication;
import com.wujia.lib_common.utils.NetworkUtil;

/**
 * author ：shenbingkai
 * date ：2019-01-08
 * description ：
 */
public class HostApp extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        NetworkUtil.getNetWork(instance);

    }
}
