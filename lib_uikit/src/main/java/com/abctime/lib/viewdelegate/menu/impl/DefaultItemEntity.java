package com.abctime.lib.viewdelegate.menu.impl;

import android.support.annotation.DrawableRes;

import com.abctime.lib.viewdelegate.menu.MenuItemEntity;

/**
 * Created by KisenHuang on 2018/5/30.
 */

public class DefaultItemEntity extends MenuItemEntity {
    public String text;
    @DrawableRes
    public int imageResId;
    @DefaultRightMenu.MenuType
    public int menuType;

    public DefaultItemEntity(int id, String text) {
        this(id, text, 0);
    }

    public DefaultItemEntity(int id, String text, int imageResId) {
        this(id, text, imageResId, DefaultRightMenu.TYPE_HORIZONTAL | DefaultRightMenu.TYPE_TEXT_FIRST);
    }

    public DefaultItemEntity(int id, String text, int imageResId, int menuType) {
        this.id = id;
        this.text = text;
        this.imageResId = imageResId;
        this.menuType = menuType;
    }

}
