package com.wujia.lib.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout

class StencilFrameLayout : FrameLayout {
    internal var mPaint: Paint?=null
    private var rect: Rect?=null
    internal var innerRect: Rect?=null
    internal fun init() {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        rect = Rect(left, top, right, bottom)
        //        layoutChildren(left, top, right, bottom, false /* no force left gravity */);
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }

    override fun dispatchDraw(canvas: Canvas) {
        //        mPaint.setAlpha(0);
        //        mPaint.setColor(Color.BLACK);
        //
        ////        Rect innerRect=new Rect(child.getLeft(),rect.top+100,rect.right-100,rect.bottom-100);
        //        canvas.drawRect(innerRect, mPaint);
        mPaint?.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OUT)

        mPaint?.color = Color.BLACK
        mPaint?.alpha = 160
        canvas.drawRect(rect!!, mPaint!!)
        super.dispatchDraw(canvas)//放在super前是后景，相反是前景，前景会覆盖子布局
    }

    companion object {
        private const val DEFAULT_CHILD_GRAVITY = Gravity.TOP or Gravity.START
    }
}
