package com.wujia.lib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wujia.lib.uikit.R;
import com.wujia.lib_common.utils.ScreenUtil;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12
 * description ：左侧菜单
 */
public class HorizontalTabBar extends LinearLayout {

    private LayoutParams mTabParams;
    private OnTabSelectedListener mOnTabSelectedListener;
    private int mCurrentPos;


    public HorizontalTabBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        setOrientation(HORIZONTAL);
        mTabParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public HorizontalTabBar addItem(final HorizontalTabItem item) {

        if (null != item) {
            item.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (null == mOnTabSelectedListener)
                        return;
                    int pos = item.getTabPosition();
                    if (pos != mCurrentPos) {

                        item.setSelected(true);
                        mOnTabSelectedListener.onTabSelected(pos, mCurrentPos);
                        getChildAt(mCurrentPos).setSelected(false);
                        mCurrentPos = pos;
                    }


                }
            });
            item.setTabPosition(getChildCount());
            addView(item, mTabParams);
        }
        return this;
    }


    public void setCurrentItem(final int position) {
        post(new Runnable() {
            @Override
            public void run() {
                getChildAt(position).performClick();
            }
        });
    }

    public void setOnTabSelectedListener(OnTabSelectedListener mOnTabSelectedListener) {
        this.mOnTabSelectedListener = mOnTabSelectedListener;
    }

    public interface OnTabSelectedListener {
        void onTabSelected(int position, int prePosition);
    }
}
