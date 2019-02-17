package com.wujia.lib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wujia.lib.uikit.R;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12
 * description ：
 */
public class HorizontalTabItem extends android.support.v7.widget.AppCompatTextView {

    private int mTabPosition;
    private Context mContext;
    private int textColorDefault, textColorSelected;

    public HorizontalTabItem(Context context, @StringRes int txt) {
        super(context);
        init(context, txt);
    }

    private void init(Context context, int txt) {
        textColorDefault = ContextCompat.getColor(context, R.color.c8);
        textColorSelected = ContextCompat.getColor(context, R.color.white);

        setGravity(Gravity.CENTER);
        mContext = context;
        //水波纹
//        TypedArray typedArray = context.obtainStyledAttributes(new int[]{R.attr.selectableItemBackgroundBorderless});
//        Drawable drawable = typedArray.getDrawable(0);
//        setBackground(drawable);
//        typedArray.recycle();

        setTextSize(TypedValue.COMPLEX_UNIT_SP, 15.0f);
        setTextColor(textColorDefault);
        setPadding(20, 5, 20, 5);
        setText(txt);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (selected) {
            setTextColor(textColorSelected);
            setBackgroundResource(R.drawable.bg_accent_raduis_24_shape);
        } else {
            setTextColor(textColorDefault);
            setBackgroundResource(R.drawable.bg_transparent_shape);
        }
    }

    public void setTabPosition(int position) {
        mTabPosition = position;
        if (position == 0) {
            setSelected(true);
        }
    }

    public int getTabPosition() {
        return mTabPosition;
    }
}
