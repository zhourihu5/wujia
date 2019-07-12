package com.wujia.lib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;

import com.wujia.lib.uikit.R;

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-21
 * description ： 带pressed效果，四周drawable
 */
public class WJButton extends androidx.appcompat.widget.AppCompatTextView {

    private static final int BACKGROUND = 0;
    private static final int TOP = 1;
    private static final int RIGHT = 2;
    private static final int BOTTOM = 3;
    private static final int LEFT = 4;

    public WJButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WJButton);
        int bg_normal = typedArray.getResourceId(R.styleable.WJButton_button_normal, 0);
        int bg_press = typedArray.getResourceId(R.styleable.WJButton_button_press, 0);
        int mode = typedArray.getInt(R.styleable.WJButton_button_mode, 0);
        typedArray.recycle();

        StateListDrawable sld = new StateListDrawable();
        sld.addState(new int[]{android.R.attr.state_focused}, context.getDrawable(bg_normal));
        sld.addState(new int[]{android.R.attr.state_pressed}, context.getDrawable(bg_press));
        sld.addState(new int[]{0}, context.getDrawable(bg_normal));

        switch (mode) {
            case BACKGROUND:
                setBackground(sld);
                break;
            case TOP:
                setCompoundDrawablesWithIntrinsicBounds(null, sld, null, null);
                break;
            case RIGHT:
                setCompoundDrawablesWithIntrinsicBounds(sld, null, sld, null);
                break;
            case BOTTOM:
                setCompoundDrawablesWithIntrinsicBounds(null, null, null, sld);
                break;
            case LEFT:
                setCompoundDrawablesWithIntrinsicBounds(sld, null, null, null);
                break;
        }
    }

    public void setBackgroundImage(int bg_normal, int bg_press) {
        StateListDrawable sld = new StateListDrawable();
        sld.addState(new int[]{android.R.attr.state_focused}, getContext().getDrawable(bg_normal));
        sld.addState(new int[]{android.R.attr.state_pressed}, getContext().getDrawable(bg_press));
        sld.addState(new int[]{0}, getContext().getDrawable(bg_normal));

        setCompoundDrawablesWithIntrinsicBounds(null, sld, null, null);
    }
}
