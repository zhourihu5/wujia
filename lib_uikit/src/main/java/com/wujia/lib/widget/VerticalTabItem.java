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
public class VerticalTabItem extends FrameLayout {

    private int mTabPosition;
    private Context mContext;
    private ImageView mIcon;
    private ImageView mPoint;
    private TextView mTxt;
    private int mIconDefault, mIconSelected;

    public VerticalTabItem(Context context, @DrawableRes int iconDefault, @DrawableRes int iconSelected, @StringRes int txt) {
        super(context);
        this.mIconDefault = iconDefault;
        this.mIconSelected = iconSelected;
        init(context, txt);
    }

    private void init(Context context, int txt) {
        mContext = context;
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);

        //水波纹
        TypedArray typedArray = context.obtainStyledAttributes(new int[]{R.attr.selectableItemBackgroundBorderless});
        Drawable drawable = typedArray.getDrawable(0);
        setBackground(drawable);
        typedArray.recycle();

        mIcon = new ImageView(context);
//        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
        LayoutParams iconParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        iconParams.gravity = Gravity.CENTER;
        mIcon.setImageResource(mIconDefault);
//        mIcon.setColorFilter(ContextCompat.getColor(context, R.color.c9));
        layout.addView(mIcon, iconParams);


        mTxt = new TextView(context);
//        int top = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());

        LayoutParams txtParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        mTxt.setGravity(Gravity.CENTER);
        mTxt.setText(txt);
//        mTxt.setPadding(0, top, 0, 0);
        mTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        mTxt.setTextColor(ContextCompat.getColor(mContext, R.color.c9));
        layout.addView(mTxt, txtParams);

        mPoint = new ImageView(context);
//        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
        LayoutParams poingParams = new LayoutParams(16, 16);
        poingParams.gravity = Gravity.RIGHT;
        poingParams.topMargin = 30;
        poingParams.rightMargin = 30;
        mPoint.setImageResource(R.drawable.bg_point_red);
        mPoint.setVisibility(INVISIBLE);

        addView(layout);
        addView(mPoint, poingParams);
    }

    public void setPoint(boolean state) {
        mPoint.setVisibility(state ? VISIBLE : INVISIBLE);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (selected) {
//            mIcon.setColorFilter(ContextCompat.getColor(mContext, R.color.colorAccent));
            mIcon.setImageResource(mIconSelected);
            mTxt.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));

        } else {
            mIcon.setImageResource(mIconDefault);
//            mIcon.setColorFilter(ContextCompat.getColor(mContext, R.color.c9));
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
