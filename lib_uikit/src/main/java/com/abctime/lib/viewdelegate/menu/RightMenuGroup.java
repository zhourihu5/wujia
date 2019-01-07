package com.abctime.lib.viewdelegate.menu;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.abctime.lib.viewdelegate.menu.logic.IMenuLogin;


/**
 * Created by KisenHuang on 2018/5/29.
 * 标题栏右侧菜单组
 */

public abstract class RightMenuGroup<E extends MenuItemEntity> {

    private SparseArray<RightMenu> menus = new SparseArray<>();
    protected ViewGroup mViewGroup;
    protected Context mContext;
    protected IMenuLogin mMenuLogic;

    public RightMenuGroup(Context context) {
        mContext = context;
    }

    /**
     * 根据位置获取menuItem
     *
     * @param id menuItem的id
     * @return menuItem
     */
    public RightMenu getMenuById(int id) {
        return menus.get(id);
    }

    public void setupMenuLogin(IMenuLogin login){
        mMenuLogic = login;
    }

    public SparseArray<RightMenu> getMenus() {
        return menus;
    }

    public View getMenuViewGroup() {
        return mViewGroup;
    }

    private void generateView() {
        mViewGroup = getMenuGroup();
        layoutMenu(mViewGroup, menus);
    }

    public RightMenuGroup addMenuEntity(E entity) {
        menus.append(entity.id, getRightMenu(mContext, entity));
        return this;
    }

    public RightMenuGroup create(){
        generateView();
        return this;
    }

    protected abstract ViewGroup getMenuGroup();

    protected abstract void layoutMenu(ViewGroup group,SparseArray<RightMenu> menus);

    protected abstract RightMenu getRightMenu(Context context, E entity);

    public abstract void setOnMenuClickListener(OnMenuClickListener listener);

}
