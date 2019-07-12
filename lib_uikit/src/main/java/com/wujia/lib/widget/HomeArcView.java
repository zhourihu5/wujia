package com.wujia.lib.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.wujia.lib.uikit.R;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-29
 * description ：两道弧
 */
public class HomeArcView extends AppCompatTextView {

    private float mOutStrokeWidth, mInnerStrokeWidth;
    private float mCenterY;
    private Paint mOutArcPaint, mInnerArcPaint;
    private RectF mOutRectF, mInnerRectF;
    private int mColor;


    public void setColor(@ColorRes int color) {

        mColor = ContextCompat.getColor(getContext(), color);
        mOutArcPaint.setColor(mColor);
        mInnerArcPaint.setColor(mColor);
        setTextColor(mColor);
    }

    public HomeArcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        mColor = ContextCompat.getColor(getContext(), R.color.colorAccent);

        mOutStrokeWidth = 2;
        mOutArcPaint = new Paint();
        mOutArcPaint.setAntiAlias(true);
        mOutArcPaint.setStrokeWidth(mOutStrokeWidth);
        mOutArcPaint.setStyle(Paint.Style.STROKE);
        mOutArcPaint.setStrokeCap(Paint.Cap.ROUND);
        mOutArcPaint.setColor(mColor);
        mOutArcPaint.setAlpha(51);


        mInnerStrokeWidth = 8;
        mInnerArcPaint = new Paint();
        mInnerArcPaint.setAntiAlias(true);
        mInnerArcPaint.setStrokeWidth(mInnerStrokeWidth);
        mInnerArcPaint.setStyle(Paint.Style.STROKE);
        mInnerArcPaint.setStrokeCap(Paint.Cap.ROUND);
        mInnerArcPaint.setColor(mColor);

        setTextColor(mColor);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        int safeW = w - getPaddingLeft() - getPaddingRight();
        int safeH = h - getPaddingTop() - getPaddingBottom();

        mOutRectF = new RectF();
        mOutRectF.left = mOutStrokeWidth / 2;
        mOutRectF.top = mOutStrokeWidth / 2;
        mOutRectF.right = safeW - mOutStrokeWidth / 2;
        mOutRectF.bottom = safeH - mOutStrokeWidth / 2;

        int padding = 10;
        mInnerRectF = new RectF();
        mInnerRectF.left = mInnerStrokeWidth / 2 + padding;
        mInnerRectF.top = mInnerStrokeWidth / 2 + padding;
        mInnerRectF.right = safeW - mInnerStrokeWidth / 2 - padding;
        mInnerRectF.bottom = safeH - mInnerStrokeWidth / 2 - padding;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawArc(mOutRectF, 125, 290, false, mOutArcPaint);
        canvas.drawArc(mInnerRectF, 125, 290, false, mInnerArcPaint);
    }
}
