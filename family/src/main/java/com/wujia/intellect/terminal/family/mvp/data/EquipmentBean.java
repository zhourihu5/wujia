package com.wujia.intellect.terminal.family.mvp.data;

import android.support.annotation.IdRes;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-23
 * description ：
 */
public class EquipmentBean {
    public String title;
    @IdRes
    public int icon;

    public EquipmentBean(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

}
