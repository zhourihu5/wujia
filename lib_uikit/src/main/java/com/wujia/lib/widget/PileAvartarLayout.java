package com.wujia.lib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by wanghaofei on 17/6/29.
 */

public class PileAvartarLayout extends ViewGroup {

    private final Context context;


    private boolean flag = false;//false表示右边增加，true表示左边增加

    private int spWidth = 20;
    //在new的时候会调用此方法
    public PileAvartarLayout(Context context) {
        super(context);
        this.context = context;
    }

    public PileAvartarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public PileAvartarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        //wrap_content
        int width = 0;
        int height = 0;

        //每一行的，宽，高
        int lineWidth = 0;
        int lineHeight = 0;
        //获取所有的子view
        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            //测量子view
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            //得到layoutparams
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            if (lineWidth + childWidth- spWidth > sizeWidth - getPaddingLeft() - getPaddingRight()) {
                //换行判断
                height += lineHeight;
                //新的行高
                lineHeight = childHeight;
                width = Math.max(width, lineWidth);
                lineWidth = childWidth- spWidth;

            } else {
                lineWidth += childWidth- spWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }

            if (i == cCount - 1) {
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }
        }

        setMeasuredDimension(modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width,
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height);
    }


    /**
     * 存储所有的View
     */
    private final List<List<View>> mAllViews = new ArrayList<>();
    /**
     * 每一行的高度
     */
    private final List<Integer> mLineHeight = new ArrayList<>();

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        mAllViews.clear();
        mLineHeight.clear();

        int width = getWidth();
        int lineWidth = 0;
        int lineHeight = 0;
        List<View> lineViews = new ArrayList<>();
        int cCount = getChildCount();

        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            if (childWidth + lineWidth + lp.leftMargin + lp.rightMargin > width - getPaddingRight() - getPaddingLeft()) {
                mLineHeight.add(lineHeight);
                mAllViews.add(lineViews);
                lineViews = new ArrayList<>();
                lineWidth = 0;
                lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
            }
            lineWidth += childWidth + lp.leftMargin + lp.rightMargin - spWidth;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin
                    + lp.bottomMargin);
            lineViews.add(child);
        }

        // 处理最后一行
        mLineHeight.add(lineHeight);
        mAllViews.add(lineViews);

        // 设置子View的位置

        int left = getPaddingLeft();
        int top = getPaddingTop();
        // 行数
        int lineNum = mAllViews.size();

        for (int i = 0; i < lineNum; i++) {
            lineViews = mAllViews.get(i);
            lineHeight = mLineHeight.get(i);

            //反序显示
            if(flag){
                Collections.reverse(lineViews);
            }
//            Collections.reverse(lineViews);

            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                if (child.getVisibility() == View.GONE) {
                    continue;
                }

                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();

                child.layout(lc, tc, rc, bc);

                left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin - spWidth;
            }
            left = getPaddingLeft();
            top += lineHeight;
        }
    }

    public void setSpWidth(int spWidth) {
        this.spWidth = spWidth;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }





    Adapter adapter;

    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
        removeAllViews();
        removeAllViews();
        if(adapter!=null){
            for (int i = 0; i < adapter.getCount(); i++) {
                View itemView=adapter.getView(i);
                if(itemView!=null){
                    this.addView(itemView);
                }
            }
        }
    }

    public interface Adapter{
        View getView(int position);
        int getCount();
    }



    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
