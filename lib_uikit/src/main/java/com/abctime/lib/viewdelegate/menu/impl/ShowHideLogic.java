package com.abctime.lib.viewdelegate.menu.impl;

import android.view.View;

import com.abctime.lib.viewdelegate.menu.RightMenu;
import com.abctime.lib.viewdelegate.menu.logic.IMenuLogin;

/**
 * Created by giraffe911 on 2018/5/30.
 */

public class ShowHideLogic implements IMenuLogin {

    private RightMenu menu;

    @Override
    public void setupRightMenu(RightMenu menu) {
        this.menu = menu;
    }

    public void show() {
        menu.getMenuContainer().setVisibility(View.VISIBLE);
    }

    public void hide() {
        menu.getMenuContainer().setVisibility(View.GONE);
    }
}
