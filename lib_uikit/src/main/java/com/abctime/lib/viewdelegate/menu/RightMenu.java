package com.abctime.lib.viewdelegate.menu;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.abctime.lib.viewdelegate.menu.logic.IMenuLogin;


/**
 * Created by KisenHuang on 2018/5/29.
 * 标题栏右侧菜单类
 */

public abstract class RightMenu<E extends MenuItemEntity> {

    private int menuId;
    private View mContainer;

    public RightMenu(Context context, E entity) {
        init(context, entity);
    }

    private void init(Context context, E entity) {
        menuId = entity.id;
        createView(context, entity);
        mContainer = generateContainer(context, entity);
    }

    protected abstract void createView(Context context, E entity);

    protected abstract ViewGroup generateContainer(Context context, E entity);

    public int getMenuId() {
        return menuId;
    }

    public View getMenuContainer() {
        return mContainer;
    }

    public abstract void setupMenuLogic(IMenuLogin login);

}
