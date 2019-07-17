package com.wujia.lib.widget

import android.content.Context
import android.content.res.TypedArray
import androidx.appcompat.widget.AppCompatImageView
import android.util.AttributeSet

import com.wujia.lib.uikit.R

/**
 * Author: shenbingkai
 * CreateDate: 2019-04-06 00:51
 * Description:
 */
class SquareImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatImageView(context, attrs, defStyleAttr) {
    private val mBasic: Int

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SquareImageView)
        mBasic = typedArray.getInt(R.styleable.SquareImageView_basis, BASIC_WIDTH)
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var measureSpec = widthMeasureSpec
        if (mBasic == BASIC_HEIGHT) {
            measureSpec = heightMeasureSpec
        }
        super.onMeasure(measureSpec, measureSpec)
    }

    companion object {

        private val BASIC_WIDTH = 0
        private val BASIC_HEIGHT = 1
    }
}
