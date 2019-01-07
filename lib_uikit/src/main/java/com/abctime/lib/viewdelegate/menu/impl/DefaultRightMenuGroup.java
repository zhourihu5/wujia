package com.abctime.lib.viewdelegate.menu.impl;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.abctime.lib.viewdelegate.menu.OnMenuClickListener;
import com.abctime.lib.viewdelegate.menu.RightMenu;
import com.abctime.lib.viewdelegate.menu.RightMenuGroup;

/**
 * Created by KisenHuang on 2018/5/30.
 * 标题栏右侧菜单默认实现
 */

public class DefaultRightMenuGroup extends RightMenuGroup<DefaultItemEntity> {

    private LinearLayout mLinearGroup;
    private OnMenuClickListener menuClickListener;
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (menuClickListener != null && view.getTag() != null && view.getTag() instanceof RightMenu)
                menuClickListener.onMenuClick((RightMenu) view.getTag());
        }
    };

    public DefaultRightMenuGroup(Context context) {
        super(context);
    }

    @Override
    protected ViewGroup getMenuGroup() {
        mLinearGroup = new LinearLayout(mContext);
        mLinearGroup.setOrientation(LinearLayout.HORIZONTAL);
        return mLinearGroup;
    }

    @Override
    protected void layoutMenu(ViewGroup group, SparseArray<RightMenu> menus) {
        for (int i = 0; i < menus.size(); i++) {
            final RightMenu menu = menus.valueAt(i);
            View menuView = menu.getMenuContainer();
            menuView.setTag(menu);
            menuView.setOnClickListener(clickListener);
            mViewGroup.addView(menuView);
        }
    }

    @Override
    protected RightMenu getRightMenu(Context context, DefaultItemEntity entity) {
        return new DefaultRightMenu(context, entity);
    }

    @Override
    public void setOnMenuClickListener(OnMenuClickListener listener) {
        menuClickListener = listener;
    }

}
