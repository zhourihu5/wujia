package com.abctime.lib.viewdelegate.menu.impl;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abctime.lib.viewdelegate.menu.RightMenu;
import com.abctime.lib.viewdelegate.menu.logic.IMenuLogin;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by KisenHuang on 2018/5/30.
 * 标题栏菜单Item
 */

public class DefaultRightMenu extends RightMenu<DefaultItemEntity> {

    public static final int TYPE_VERTICAL = 0x00000001;
    public static final int TYPE_HORIZONTAL = TYPE_VERTICAL << 1;
    public static final int TYPE_IMAGE_FIRST = TYPE_HORIZONTAL << 1;
    public static final int TYPE_TEXT_FIRST = TYPE_IMAGE_FIRST << 1;

    private static final int FORMAT_ORIENTATION = 0x00000011;
    private static final int FORMAT_WHO_FIRST = 0x00001100;

    @IntDef({TYPE_VERTICAL, TYPE_HORIZONTAL})
    @Retention(RetentionPolicy.RUNTIME)
    @interface MenuType {
    }

    private ImageView imageView;
    private TextView textView;

    public DefaultRightMenu(Context context, DefaultItemEntity entity) {
        super(context, entity);
    }

    @Override
    protected void createView(Context context, DefaultItemEntity entity) {
        if (entity.imageResId != 0) {
            imageView = new ImageView(context);
            imageView.setImageDrawable(ContextCompat.getDrawable(context, entity.imageResId));
        }
        textView = new TextView(context);
        textView.setText(entity.text);
    }

    @Override
    protected ViewGroup generateContainer(Context context, DefaultItemEntity entity) {
        LinearLayout group = new LinearLayout(context);
        int menuType = entity.menuType;
        int orientation = (menuType & FORMAT_ORIENTATION) == TYPE_VERTICAL ? LinearLayout.VERTICAL : LinearLayout.HORIZONTAL;
        group.setOrientation(orientation);
        if ((menuType & FORMAT_WHO_FIRST) == TYPE_TEXT_FIRST) {
            if (imageView != null)
                group.addView(imageView);
            group.addView(textView);
        } else {
            group.addView(textView);
            if (imageView != null)
                group.addView(imageView);
        }
        return group;    }

    @Override
    public void setupMenuLogic(IMenuLogin login) {
        if (login != null)
            login.setupRightMenu(this);
    }

}
