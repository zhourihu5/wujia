package com.wujia.lib.widget

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout

class StencilFrameLayout : FrameLayout {
    internal var mPaint: Paint?=null
    internal var rect: Rect?=null
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

    internal fun layoutChildren(left: Int, top: Int, right: Int, bottom: Int, forceLeftGravity: Boolean) {
        val count = childCount
        if (count > 1) {
            throw RuntimeException("can not be more than one child")
        }

        val parentLeft = paddingLeft
        val parentRight = right - left - paddingRight

        val parentTop = paddingTop
        val parentBottom = bottom - top - paddingBottom

        for (i in 0 until count) {
            val child = getChildAt(i)
            if (child.visibility != View.GONE) {
                val lp = child.layoutParams as FrameLayout.LayoutParams

                val width = child.measuredWidth
                val height = child.measuredHeight

                var childLeft: Int
                val childTop: Int

                var gravity = lp.gravity
                if (gravity == -1) {
                    gravity = DEFAULT_CHILD_GRAVITY
                }

                val layoutDirection = layoutDirection
                val absoluteGravity = Gravity.getAbsoluteGravity(gravity, layoutDirection)
                val verticalGravity = gravity and Gravity.VERTICAL_GRAVITY_MASK

                when (absoluteGravity and Gravity.HORIZONTAL_GRAVITY_MASK) {
                    Gravity.CENTER_HORIZONTAL -> childLeft = parentLeft + (parentRight - parentLeft - width) / 2 +
                            lp.leftMargin - lp.rightMargin
                    Gravity.RIGHT -> right@{
                        if (!forceLeftGravity) {
                            childLeft = parentRight - width - lp.rightMargin
                            return@right
                        }
                        childLeft = parentLeft + lp.leftMargin
                    }
                    Gravity.LEFT -> childLeft = parentLeft + lp.leftMargin
                    else -> childLeft = parentLeft + lp.leftMargin
                }

                when (verticalGravity) {
                    Gravity.TOP -> childTop = parentTop + lp.topMargin
                    Gravity.CENTER_VERTICAL -> childTop = parentTop + (parentBottom - parentTop - height) / 2 +
                            lp.topMargin - lp.bottomMargin
                    Gravity.BOTTOM -> childTop = parentBottom - height - lp.bottomMargin
                    else -> childTop = parentTop + lp.topMargin
                }

                child.layout(childLeft, childTop, childLeft + width, childTop + height)
                innerRect = Rect(childLeft, childTop, childLeft + width, childTop + height)
            }
        }
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
        canvas.drawRect(rect, mPaint)
        super.dispatchDraw(canvas)//放在super前是后景，相反是前景，前景会覆盖子布局
    }

    companion object {
        private val DEFAULT_CHILD_GRAVITY = Gravity.TOP or Gravity.START
    }
}
