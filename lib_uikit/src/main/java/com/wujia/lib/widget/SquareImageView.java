package com.wujia.lib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.wujia.lib.uikit.R;

/**
 * Author: shenbingkai
 * CreateDate: 2019-04-06 00:51
 * Description:
 */
public class SquareImageView extends AppCompatImageView {

    private static int BASIC_WIDTH = 0;
    private static int BASIC_HEIGHT = 1;
    private int mBasic;

    public SquareImageView(Context context) {
        this(context, null);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SquareImageView);
        mBasic = typedArray.getInt(R.styleable.SquareImageView_basis, BASIC_WIDTH);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureSpec = widthMeasureSpec;
        if (mBasic == BASIC_HEIGHT) {
            measureSpec = heightMeasureSpec;
        }
        super.onMeasure(measureSpec, measureSpec);
    }
}
