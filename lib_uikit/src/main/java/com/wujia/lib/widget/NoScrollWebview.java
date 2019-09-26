package com.wujia.lib.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class NoScrollWebview extends WebView {
    public NoScrollWebview(Context context) {
        super(context);
    }

    public NoScrollWebview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollWebview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NoScrollWebview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public NoScrollWebview(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
    }
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }
}
