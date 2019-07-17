package com.wujia.lib.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.appcompat.widget.AppCompatTextView
import android.util.AttributeSet

import com.wujia.lib.uikit.R

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-29
 * description ：两道弧
 */
class HomeArcView(context: Context, attrs: AttributeSet?) : AppCompatTextView(context, attrs) {

    private var mOutStrokeWidth: Float = 0.toFloat()
    private var mInnerStrokeWidth: Float = 0.toFloat()
    private val mCenterY: Float = 0.toFloat()
    private var mOutArcPaint: Paint? = null
    private var mInnerArcPaint: Paint? = null
    private var mOutRectF: RectF? = null
    private var mInnerRectF: RectF? = null
    private var mColor: Int = 0


    fun setColor(@ColorRes color: Int) {

        mColor = ContextCompat.getColor(context, color)
        mOutArcPaint!!.color = mColor
        mInnerArcPaint!!.color = mColor
        setTextColor(mColor)
    }

    init {

        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {

        mColor = ContextCompat.getColor(getContext(), R.color.colorAccent)

        mOutStrokeWidth = 2f
        mOutArcPaint = Paint()
        mOutArcPaint!!.isAntiAlias = true
        mOutArcPaint!!.strokeWidth = mOutStrokeWidth
        mOutArcPaint!!.style = Paint.Style.STROKE
        mOutArcPaint!!.strokeCap = Paint.Cap.ROUND
        mOutArcPaint!!.color = mColor
        mOutArcPaint!!.alpha = 51


        mInnerStrokeWidth = 8f
        mInnerArcPaint = Paint()
        mInnerArcPaint!!.isAntiAlias = true
        mInnerArcPaint!!.strokeWidth = mInnerStrokeWidth
        mInnerArcPaint!!.style = Paint.Style.STROKE
        mInnerArcPaint!!.strokeCap = Paint.Cap.ROUND
        mInnerArcPaint!!.color = mColor

        setTextColor(mColor)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        val safeW = w - paddingLeft - paddingRight
        val safeH = h - paddingTop - paddingBottom

        mOutRectF = RectF()
        mOutRectF!!.left = mOutStrokeWidth / 2
        mOutRectF!!.top = mOutStrokeWidth / 2
        mOutRectF!!.right = safeW - mOutStrokeWidth / 2
        mOutRectF!!.bottom = safeH - mOutStrokeWidth / 2

        val padding = 10
        mInnerRectF = RectF()
        mInnerRectF!!.left = mInnerStrokeWidth / 2 + padding
        mInnerRectF!!.top = mInnerStrokeWidth / 2 + padding
        mInnerRectF!!.right = safeW.toFloat() - mInnerStrokeWidth / 2 - padding.toFloat()
        mInnerRectF!!.bottom = safeH.toFloat() - mInnerStrokeWidth / 2 - padding.toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawArc(mOutRectF!!, 125f, 290f, false, mOutArcPaint!!)
        canvas.drawArc(mInnerRectF!!, 125f, 290f, false, mInnerArcPaint!!)
    }
}
