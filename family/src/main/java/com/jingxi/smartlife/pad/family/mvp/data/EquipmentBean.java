package com.jingxi.smartlife.pad.family.mvp.data;

import androidx.annotation.IdRes;

import java.util.ArrayList;
import java.util.List;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-23
 * description ：
 */
public class EquipmentBean {

    public EquipmentType type;
    public String title;
    @IdRes
    public int icon;
    public List<Menu> menus;

    public EquipmentBean(EquipmentType type) {
        this.type = type;
    }

    public EquipmentBean(EquipmentType type, String title, int icon) {
        this.type = type;
        this.title = title;
        this.icon = icon;
    }

    public EquipmentBean addMenu(Menu menu) {
        if (null == menus)
            menus = new ArrayList<>();

        menus.add(menu);
        return this;
    }

    public static class Menu {
        public String title;
        @IdRes
        public int icon;

        public Menu(String title, int icon) {
            this.title = title;
            this.icon = icon;
        }
    }
}
