package com.wujia.lib.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.core.content.ContextCompat
import com.wujia.lib.uikit.R
import kotlin.math.max


class ShadowContainer @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ViewGroup(context, attrs, defStyleAttr) {
    private val deltaLength: Float
    private val cornerRadius: Float
    private val mShadowPaint: Paint
    private var drawShadow: Boolean = false

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.ShadowContainer)
        val shadowColor = a.getColor(R.styleable.ShadowContainer_containerShadowColor, ContextCompat.getColor(context, R.color.container_shadow_color))
        //        int shadowColor = Color.RED;
        val shadowRadius = a.getDimension(R.styleable.ShadowContainer_containerShadowRadius, context.resources.getDimensionPixelSize(R.dimen.container_shadow_radius).toFloat())
        deltaLength = a.getDimension(R.styleable.ShadowContainer_containerDeltaLength, context.resources.getDimensionPixelSize(R.dimen.container_delta_length).toFloat())
        cornerRadius = a.getDimension(R.styleable.ShadowContainer_containerCornerRadius, context.resources.getDimensionPixelSize(R.dimen.container_corner_radius).toFloat())
        val dx = a.getDimension(R.styleable.ShadowContainer_deltaX, 0f)
        val dy = a.getDimension(R.styleable.ShadowContainer_deltaY, context.resources.getDimensionPixelSize(R.dimen.delta_y).toFloat())
        drawShadow = a.getBoolean(R.styleable.ShadowContainer_enable, true)
        a.recycle()
        mShadowPaint = Paint()
        mShadowPaint.style = Paint.Style.FILL
        mShadowPaint.isAntiAlias = true
        mShadowPaint.color = shadowColor
        mShadowPaint.setShadowLayer(shadowRadius, dx, dy, shadowColor)
    }

    override fun dispatchDraw(canvas: Canvas) {
        if (drawShadow) {
            if (layerType != View.LAYER_TYPE_SOFTWARE) {
                setLayerType(View.LAYER_TYPE_SOFTWARE, null)
            }
            val child = getChildAt(0)
            val left = child.left
            val top = child.top
            val right = child.right
            val bottom = child.bottom
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                canvas.drawRoundRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), cornerRadius, cornerRadius, mShadowPaint)
            } else {
                val drawablePath = Path()
                drawablePath.moveTo(left + cornerRadius, top.toFloat())
                drawablePath.arcTo(RectF(left.toFloat(), top.toFloat(), left + 2 * cornerRadius, top + 2 * cornerRadius), -90f, -90f, false)
                drawablePath.lineTo(left.toFloat(), bottom - cornerRadius)
                drawablePath.arcTo(RectF(left.toFloat(), bottom - 2 * cornerRadius, left + 2 * cornerRadius, bottom.toFloat()), 180f, -90f, false)
                drawablePath.lineTo(right - cornerRadius, bottom.toFloat())
                drawablePath.arcTo(RectF(right - 2 * cornerRadius, bottom - 2 * cornerRadius, right.toFloat(), bottom.toFloat()), 90f, -90f, false)
                drawablePath.lineTo(right.toFloat(), top + cornerRadius)
                drawablePath.arcTo(RectF(right - 2 * cornerRadius, top.toFloat(), right.toFloat(), top + 2 * cornerRadius), 0f, -90f, false)
                drawablePath.close()
                canvas.drawPath(drawablePath, mShadowPaint)
            }
        }
        super.dispatchDraw(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (childCount != 1) {
            throw IllegalStateException("子View只能有一个")
        }
        val measuredWidth = measuredWidth
        val measuredHeight = measuredHeight
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val child = getChildAt(0)
        val layoutParams = child.layoutParams as LayoutParams
        val childBottomMargin = (max(deltaLength, layoutParams.bottomMargin.toFloat()) + 1).toInt()
        val childLeftMargin = (max(deltaLength, layoutParams.leftMargin.toFloat()) + 1).toInt()
        val childRightMargin = (max(deltaLength, layoutParams.rightMargin.toFloat()) + 1).toInt()
        val childTopMargin = (max(deltaLength, layoutParams.topMargin.toFloat()) + 1).toInt()
        val widthMeasureSpecMode: Int
        val widthMeasureSpecSize: Int
        val heightMeasureSpecMode: Int
        val heightMeasureSpecSize: Int
        if (widthMode == MeasureSpec.UNSPECIFIED) {
            widthMeasureSpecMode = MeasureSpec.UNSPECIFIED
            widthMeasureSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        } else {
            if (layoutParams.width == MATCH_PARENT) {
                widthMeasureSpecMode = MeasureSpec.EXACTLY
                widthMeasureSpecSize = measuredWidth - childLeftMargin - childRightMargin
            } else if (WRAP_CONTENT == layoutParams.width) {
                widthMeasureSpecMode = MeasureSpec.AT_MOST
                widthMeasureSpecSize = measuredWidth - childLeftMargin - childRightMargin
            } else {
                widthMeasureSpecMode = MeasureSpec.EXACTLY
                widthMeasureSpecSize = layoutParams.width
            }
        }
        if (heightMode == MeasureSpec.UNSPECIFIED) {
            heightMeasureSpecMode = MeasureSpec.UNSPECIFIED
            heightMeasureSpecSize = MeasureSpec.getSize(heightMeasureSpec)
        } else {
            if (layoutParams.height == MATCH_PARENT) {
                heightMeasureSpecMode = MeasureSpec.EXACTLY
                heightMeasureSpecSize = measuredHeight - childBottomMargin - childTopMargin
            } else if (WRAP_CONTENT == layoutParams.height) {
                heightMeasureSpecMode = MeasureSpec.AT_MOST
                heightMeasureSpecSize = measuredHeight - childBottomMargin - childTopMargin
            } else {
                heightMeasureSpecMode = MeasureSpec.EXACTLY
                heightMeasureSpecSize = layoutParams.height
            }
        }
        measureChild(child, MeasureSpec.makeMeasureSpec(widthMeasureSpecSize, widthMeasureSpecMode), MeasureSpec.makeMeasureSpec(heightMeasureSpecSize, heightMeasureSpecMode))
        val parentWidthMeasureSpec = MeasureSpec.getMode(widthMeasureSpec)
        val parentHeightMeasureSpec = MeasureSpec.getMode(heightMeasureSpec)
        var height = measuredHeight
        var width = measuredWidth
        val childHeight = child.measuredHeight
        val childWidth = child.measuredWidth
        if (parentHeightMeasureSpec == MeasureSpec.AT_MOST) {
            height = childHeight + childTopMargin + childBottomMargin
        }
        if (parentWidthMeasureSpec == MeasureSpec.AT_MOST) {
            width = childWidth + childRightMargin + childLeftMargin
        }
        if (width < childWidth + 2 * deltaLength) {
            width = (childWidth + 2 * deltaLength).toInt()
        }
        if (height < childHeight + 2 * deltaLength) {
            height = (childHeight + 2 * deltaLength).toInt()
        }
        if (height != measuredHeight || width != measuredWidth) {
            setMeasuredDimension(width, height)
        }
    }

    internal class LayoutParams : MarginLayoutParams {

        constructor(c: Context, attrs: AttributeSet) : super(c, attrs)

        constructor(width: Int, height: Int) : super(width, height)

        constructor(source: MarginLayoutParams) : super(source)

        constructor(source: ViewGroup.LayoutParams) : super(source)
    }

    override fun generateDefaultLayoutParams(): ViewGroup.LayoutParams {
        return LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
    }

    override fun generateLayoutParams(p: ViewGroup.LayoutParams): ViewGroup.LayoutParams {
        return LayoutParams(p)
    }

    override fun generateLayoutParams(attrs: AttributeSet): ViewGroup.LayoutParams {
        return LayoutParams(context, attrs)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val child = getChildAt(0)
        val measuredWidth = measuredWidth
        val measuredHeight = measuredHeight
        val childMeasureWidth = child.measuredWidth
        val childMeasureHeight = child.measuredHeight
        child.layout((measuredWidth - childMeasureWidth) / 2, (measuredHeight - childMeasureHeight) / 2, (measuredWidth + childMeasureWidth) / 2, (measuredHeight + childMeasureHeight) / 2)
    }

}