package com.abctime.businesslib.view.progress;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.abctime.aoplib.annotation.Tracker;
import com.abctime.businesslib.R;

/**
 * description:
 * author: Kisenhuang
 * email: Kisenhuang@163.com
 * time: 2018/12/4 上午11:22
 */
public class CircleTouchProgressView extends RelativeLayout {

    private View fingerPrintView;

    private CircleTouchProgress progressBar;

    private ObjectAnimator animator;
    private boolean isCanel;

    private String eventId = "";

    public CircleTouchProgressView(Context context) {
        this(context, null);
    }

    public CircleTouchProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleTouchProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleTouchProgressView, defStyleAttr, 0);
        eventId = a.getString(R.styleable.CircleTouchProgressView_ctp_point_eventId);
        a.recycle();

        initData(context);
    }

    private void initData(final Context context) {

        LayoutInflater.from(context).inflate(R.layout.progress_circle_touch, this);

        progressBar = findViewById(R.id.payment_main_process_2);

        animator = ObjectAnimator.ofInt(progressBar, "angle", 0, -360).setDuration(2000);
        animator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                isCanel = false;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (!isCanel) {
                    setVisibility(GONE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isCanel = true;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        fingerPrintView = findViewById(R.id.process_finger_print_view);

        fingerPrintView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                final Rect rect = new Rect();

                view.getFocusedRect(rect);

                float x = motionEvent.getX();

                float y = motionEvent.getY();

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                    progressBarDown(rect, x, y);

                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL || motionEvent.getAction() == MotionEvent.ACTION_OUTSIDE) {

                    stopAnimation();

                } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {

                    if (!inside((float) rect.width(), x, y)) {
                        stopAnimation();
                    }
                }

                return true;
            }
        });
    }

    private void progressBarDown(Rect rect, float x, float y) {
        if (inside((float) rect.width(), x, y)) {
            animator.start();
        }
        if (!TextUtils.isEmpty(eventId))
            addPoint();
    }

    @Tracker(eventType = "click")
    private String addPoint() {
        return eventId;
    }

    private void stopAnimation() {
        animator.cancel();

        progressBar.reset();
    }

    private boolean inside(float size, float x, float y) {

        float r = size / 2.0f;

        float xx = x - r;

        float yy = y - r;

        return r * r > xx * xx + yy * yy;
    }
}
