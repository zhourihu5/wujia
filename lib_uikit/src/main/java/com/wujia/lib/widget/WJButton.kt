package com.wujia.lib.widget

import android.content.Context
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import com.wujia.lib.uikit.R

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-21
 * description ： 带pressed效果，四周drawable
 */
class WJButton(context: Context, attrs: AttributeSet) : androidx.appcompat.widget.AppCompatTextView(context, attrs) {

    init {

        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet) {

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.WJButton)
        val bg_normal = typedArray.getResourceId(R.styleable.WJButton_button_normal, 0)
        val bg_press = typedArray.getResourceId(R.styleable.WJButton_button_press, 0)
        val mode = typedArray.getInt(R.styleable.WJButton_button_mode, 0)
        typedArray.recycle()

        val sld = StateListDrawable()
        sld.addState(intArrayOf(android.R.attr.state_focused), context.getDrawable(bg_normal))
        sld.addState(intArrayOf(android.R.attr.state_pressed), context.getDrawable(bg_press))
        sld.addState(intArrayOf(0), context.getDrawable(bg_normal))

        when (mode) {
            BACKGROUND -> background = sld
            TOP -> setCompoundDrawablesWithIntrinsicBounds(null, sld, null, null)
            RIGHT -> setCompoundDrawablesWithIntrinsicBounds(sld, null, sld, null)
            BOTTOM -> setCompoundDrawablesWithIntrinsicBounds(null, null, null, sld)
            LEFT -> setCompoundDrawablesWithIntrinsicBounds(sld, null, null, null)
        }
    }

    fun setBackgroundImage(bg_normal: Int, bg_press: Int) {
        val sld = StateListDrawable()
        sld.addState(intArrayOf(android.R.attr.state_focused), context.getDrawable(bg_normal))
        sld.addState(intArrayOf(android.R.attr.state_pressed), context.getDrawable(bg_press))
        sld.addState(intArrayOf(0), context.getDrawable(bg_normal))

        setCompoundDrawablesWithIntrinsicBounds(null, sld, null, null)
    }

    companion object {

        private const val BACKGROUND = 0
        private const val TOP = 1
        private const val RIGHT = 2
        private const val BOTTOM = 3
        private const val LEFT = 4
    }
}
