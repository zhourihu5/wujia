package com.abctime.businesslib.animview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.os.SystemClock;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.abctime.lib_common.utils.LogUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * author:Created by xmren on 2018/7/4.
 * email :renxiaomin@100tal.com
 */

public abstract class BaseAnimView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    public static final int RESTART = 1;
    public static final int REVERSE = 2;
    private static final int DRAW_INTERVAL = 30;
    private Thread workThread;
    private SurfaceHolder surfaceHolder;
    protected boolean isRunning;
    private long mDuration = 0;
    private int mRepeatMode;
    private int mRepeatCount = -1;

    private long mCurrentPosition = 0;
    private int mCurrentRepeatCount = 0;

    @IntDef({RESTART, REVERSE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RepeatMode {
    }

    public BaseAnimView(Context context) {
        this(context, null);
    }

    public BaseAnimView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.surfaceHolder = this.getHolder();
        setZOrderOnTop(true);
        this.surfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
        this.surfaceHolder.addCallback(this);
        this.workThread = new Thread(this);
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        LogUtil.i("BaseAnimView surfaceCreated call");
        isRunning = true;
        this.workThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        LogUtil.i("BaseAnimView surfaceChanged call");
    }

    public void setDuration(long duration) {
        mDuration = duration;
    }

    public void setRepeadMode(@RepeatMode int value) {
        mRepeatMode = value;
    }

    public void setRepeatCount(int repeatCount) {
        mRepeatCount = repeatCount;
    }

    public void start() {
        if (!isRunning) {
            isRunning = true;
            this.workThread.start();
        }
        mCurrentPosition = 0;
        mCurrentRepeatCount = 0;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        LogUtil.i("BaseAnimView surfaceDestroyed call");
        isRunning = false;
        try {
            workThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long lastDrawTime = 0;
        while (isRunning) {
            long l = SystemClock.elapsedRealtime();

            try {
                Canvas canvas = this.surfaceHolder.lockCanvas();
                if (canvas != null) {
                    canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
                    doDraw(canvas, getFraction());
                }
                lastDrawTime = SystemClock.elapsedRealtime() - l;
                this.surfaceHolder.unlockCanvasAndPost(canvas);

//                if (isFinish()) {
//                    isRunning = false;
//                    continue;
//                }
                long millis = DRAW_INTERVAL - lastDrawTime;
//                if (mCurrentPosition + lastDrawTime >= mDuration) {
//                    millis = 0;
//                    mCurrentPosition = mDuration;
//                } else if ((mCurrentPosition + (millis > 0 ? DRAW_INTERVAL : lastDrawTime)) >= mDuration) {
//                    if (millis > 0) {
//                        millis = mDuration - mCurrentPosition;
//                    }
//                    mCurrentPosition = mDuration;
//                }

                Thread.sleep(DRAW_INTERVAL);//33 frame
                if (millis > 0) {
//                    mCurrentPosition += DRAW_INTERVAL;
//                    Thread.sleep(DRAW_INTERVAL);//33 frame
                } else {
                    LogUtil.i("BaseAnimView draw time so long " + lastDrawTime + "ms");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isFinish() {
        return mRepeatCount != -1
                && mCurrentPosition >= mDuration
                && mCurrentRepeatCount >= mRepeatCount;
    }

    private float getFraction() {
//        return mCurrentPosition == 0 ? 0 : mCurrentPosition / mDuration;
        return 0;
    }

    protected abstract void doDraw(Canvas canvas, float fraction);


}
