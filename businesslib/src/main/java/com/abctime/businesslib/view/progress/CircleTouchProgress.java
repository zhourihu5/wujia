package com.abctime.businesslib.view.progress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.abctime.lib.uikit.R;
import com.abctime.lib_common.utils.sys.DensityUtils;

/**
 * description:
 * author: Kisenhuang
 * email: Kisenhuang@163.com
 * time: 2018/12/4 上午11:15
 */
public class CircleTouchProgress extends AppCompatTextView {

    private Paint mPaint;
    private int mStrokeWidth;
    private int mRaduis;
    private int mWidth;
    private int mCenter;
    private int angle;
    private RectF mRectF;

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
        invalidate();
    }

    public CircleTouchProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mStrokeWidth = DensityUtils.dp2px(context, 12);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);

        int c0 = ContextCompat.getColor(context, R.color.clr_f49d6d);
        int c1 = ContextCompat.getColor(context, R.color.clr_f7ad5a);
        SweepGradient sweep = new SweepGradient(mCenter, mCenter, c0, c1);
        mPaint.setShader(sweep);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mRaduis = w / 2 - mStrokeWidth / 2;
        mCenter = w / 2;
        mRectF = new RectF();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mRectF.left = mStrokeWidth / 2;
        mRectF.top = mStrokeWidth / 2;
        mRectF.right = mWidth - mStrokeWidth / 2;
        mRectF.bottom = mWidth - mStrokeWidth / 2;

        canvas.drawArc(mRectF, -90, angle, false, mPaint);
    }

    public void reset() {
        angle = 0;
        invalidate();
    }
}
