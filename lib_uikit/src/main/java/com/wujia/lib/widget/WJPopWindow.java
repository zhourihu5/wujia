package com.wujia.lib.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import androidx.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-24
 * description ：
 */
public class WJPopWindow {
    private Context mContext;
    private int mWidth;
    private int mHeight;
    private boolean mIsFocusable = true;
    private boolean mIsOutside = true;
    private int mResLayoutId = -1;
    private View mContentView;
    private PopupWindow mPopupWindow;
    private int mAnimationStyle = -1;

    private boolean mClippEnable = true;//default is true
    private boolean mIgnoreCheekPress = false;
    private int mInputMode = -1;
    private PopupWindow.OnDismissListener mOnDismissListener;
    private int mSoftInputMode = -1;
    private boolean mTouchable = true;//default is ture
    private View.OnTouchListener mOnTouchListener;

    private WJPopWindow(Context context) {
        mContext = context;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    /**
     * @param anchor
     * @param xOff
     * @param yOff
     * @return
     */
    public WJPopWindow showAsDropDown(View anchor, int xOff, int yOff) {
        if (mPopupWindow != null) {
            mPopupWindow.showAsDropDown(anchor, xOff, yOff);
        }
        return this;
    }

    public WJPopWindow showAsDropDown(View anchor) {
        if (mPopupWindow != null) {
            mPopupWindow.showAsDropDown(anchor);
        }
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public WJPopWindow showAsDropDown(View anchor, int xOff, int yOff, int gravity) {
        if (mPopupWindow != null) {
            mPopupWindow.showAsDropDown(anchor, xOff, yOff, gravity);
        }
        return this;
    }


    /**
     * 相对于父控件的位置（通过设置Gravity.CENTER，下方Gravity.BOTTOM等 ），可以设置具体位置坐标
     *
     * @param parent
     * @param gravity
     * @param x       the popup's x location offset
     * @param y       the popup's y location offset
     * @return
     */
    public WJPopWindow showAtLocation(View parent, int gravity, int x, int y) {
        if (mPopupWindow != null) {
            mPopupWindow.showAtLocation(parent, gravity, x, y);
        }
        return this;
    }

    /**
     * 添加一些属性设置
     *
     * @param popupWindow
     */
    private void apply(PopupWindow popupWindow) {
        popupWindow.setClippingEnabled(mClippEnable);
        if (mIgnoreCheekPress) {
            popupWindow.setIgnoreCheekPress();
        }
        if (mInputMode != -1) {
            popupWindow.setInputMethodMode(mInputMode);
        }
        if (mSoftInputMode != -1) {
            popupWindow.setSoftInputMode(mSoftInputMode);
        }
        if (mOnDismissListener != null) {
            popupWindow.setOnDismissListener(mOnDismissListener);
        }
        if (mOnTouchListener != null) {
            popupWindow.setTouchInterceptor(mOnTouchListener);
        }
        popupWindow.setTouchable(mTouchable);


    }

    private PopupWindow build() {

        if (mContentView == null) {
            mContentView = LayoutInflater.from(mContext).inflate(mResLayoutId, null);
        }

        if (mWidth != 0 && mHeight != 0) {
            mPopupWindow = new PopupWindow(mContentView, mWidth, mHeight);
        } else {
            mPopupWindow = new PopupWindow(mContentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        if (mAnimationStyle != -1) {
            mPopupWindow.setAnimationStyle(mAnimationStyle);
        }

        apply(mPopupWindow);//设置一些属性

        mPopupWindow.setFocusable(mIsFocusable);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopupWindow.setOutsideTouchable(mIsOutside);

        if (mWidth == 0 || mHeight == 0) {
            mPopupWindow.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            //如果外面没有设置宽高的情况下，计算宽高并赋值
            mWidth = mPopupWindow.getContentView().getMeasuredWidth();
            mHeight = mPopupWindow.getContentView().getMeasuredHeight();
        }

        mPopupWindow.update();

        return mPopupWindow;
    }

    /**
     * 关闭popWindow
     */
    public void dissmiss() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
    }


    public static class PopupWindowBuilder {
        private WJPopWindow mWJPopWindow;

        public PopupWindowBuilder(Context context) {
            mWJPopWindow = new WJPopWindow(context);
        }

        public PopupWindowBuilder size(int width, int height) {
            mWJPopWindow.mWidth = width;
            mWJPopWindow.mHeight = height;
            return this;
        }


        public PopupWindowBuilder setFocusable(boolean focusable) {
            mWJPopWindow.mIsFocusable = focusable;
            return this;
        }


        public PopupWindowBuilder setView(int resLayoutId) {
            mWJPopWindow.mResLayoutId = resLayoutId;
            mWJPopWindow.mContentView = null;
            return this;
        }

        public PopupWindowBuilder setView(View view) {
            mWJPopWindow.mContentView = view;
            mWJPopWindow.mResLayoutId = -1;
            return this;
        }

        public PopupWindowBuilder setOutsideTouchable(boolean outsideTouchable) {
            mWJPopWindow.mIsOutside = outsideTouchable;
            return this;
        }

        /**
         * 设置弹窗动画
         *
         * @param animationStyle
         * @return
         */
        public PopupWindowBuilder setAnimationStyle(int animationStyle) {
            mWJPopWindow.mAnimationStyle = animationStyle;
            return this;
        }


        public PopupWindowBuilder setClippingEnable(boolean enable) {
            mWJPopWindow.mClippEnable = enable;
            return this;
        }


        public PopupWindowBuilder setIgnoreCheekPress(boolean ignoreCheekPress) {
            mWJPopWindow.mIgnoreCheekPress = ignoreCheekPress;
            return this;
        }

        public PopupWindowBuilder setInputMethodMode(int mode) {
            mWJPopWindow.mInputMode = mode;
            return this;
        }

        public PopupWindowBuilder setOnDissmissListener(PopupWindow.OnDismissListener onDissmissListener) {
            mWJPopWindow.mOnDismissListener = onDissmissListener;
            return this;
        }


        public PopupWindowBuilder setSoftInputMode(int softInputMode) {
            mWJPopWindow.mSoftInputMode = softInputMode;
            return this;
        }


        public PopupWindowBuilder setTouchable(boolean touchable) {
            mWJPopWindow.mTouchable = touchable;
            return this;
        }

        public PopupWindowBuilder setTouchIntercepter(View.OnTouchListener touchIntercepter) {
            mWJPopWindow.mOnTouchListener = touchIntercepter;
            return this;
        }


        public WJPopWindow create() {
            //构建PopWindow
            mWJPopWindow.build();
            return mWJPopWindow;
        }

    }
}
