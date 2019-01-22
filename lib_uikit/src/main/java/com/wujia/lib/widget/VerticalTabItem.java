package com.wujia.lib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wujia.lib.uikit.R;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12
 * description ：
 */
public class VerticalTabItem extends LinearLayout {

    private int mTabPosition;
    private Context mContext;
    private ImageView mIcon;
    private TextView mTxt;

    public VerticalTabItem(Context context, @DrawableRes int icon, @StringRes int txt) {
        super(context);
        init(context, icon, txt);
    }

    private void init(Context context, int icon, int txt) {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        mContext = context;
        //水波纹
        TypedArray typedArray = context.obtainStyledAttributes(new int[]{R.attr.selectableItemBackgroundBorderless});
        Drawable drawable = typedArray.getDrawable(0);
        setBackground(drawable);
        typedArray.recycle();

        mIcon = new ImageView(context);
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
        LayoutParams iconParams = new LayoutParams(size, size);
        iconParams.gravity = Gravity.CENTER;
        mIcon.setImageResource(icon);
        mIcon.setColorFilter(ContextCompat.getColor(context, R.color.c9));
        addView(mIcon,iconParams);


        mTxt = new TextView(context);
        int top = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics());

        LayoutParams txtParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mTxt.setGravity(Gravity.CENTER);
        mTxt.setText(txt);
        mTxt.setPadding(0, top, 0, 0);
        mTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        mTxt.setTextColor(ContextCompat.getColor(mContext, R.color.c9));
        addView(mTxt,txtParams);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (selected) {
            mIcon.setColorFilter(ContextCompat.getColor(mContext, R.color.colorAccent));
            mTxt.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));

        } else {
            mIcon.setColorFilter(ContextCompat.getColor(mContext, R.color.c9));
            mTxt.setTextColor(ContextCompat.getColor(mContext, R.color.c9));
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
