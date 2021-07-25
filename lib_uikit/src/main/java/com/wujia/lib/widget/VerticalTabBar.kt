package com.wujia.lib.widget

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout
import com.wujia.lib.uikit.R
import com.wujia.lib_common.utils.ScreenUtil

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12
 * description ：左侧菜单
 */
class VerticalTabBar(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    private var mTabParams: LayoutParams? = null
    private var mOnTabSelectedListener: ((position: Int, prePosition: Int)->Unit)? = null
    private var mCurrentPos: Int = 0


    init {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        orientation = VERTICAL
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.VerticalTabBar)
        val style = typedArray.getInt(R.styleable.VerticalTabBar_tab_height, 0)
        typedArray.recycle()
        if (style == 0) {
            mTabParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0)
            mTabParams!!.weight = 1f
        } else {
            mTabParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ScreenUtil.dip2px(style.toFloat()))
        }
    }

    fun addItem(item: VerticalTabItem?): VerticalTabBar {

        if (null != item) {
            item.setOnClickListener {
                //                    if (null == mOnTabSelectedListener)
                //                        return;
                val pos = item.tabPosition
                if (pos != mCurrentPos) {

                    item.isSelected = true
                    mOnTabSelectedListener?.invoke(pos, mCurrentPos)
                    getChildAt(mCurrentPos).isSelected = false
                    mCurrentPos = pos
                }
            }
            item.tabPosition = childCount
            addView(item, mTabParams)
        }
        return this
    }


    fun setOnTabSelectedListener(mOnTabSelectedListener: ((position: Int, prePosition: Int)->Unit)?) {
        this.mOnTabSelectedListener = mOnTabSelectedListener
    }

    interface OnTabSelectedListener {
        fun onTabSelected(position: Int, prePosition: Int)
    }
}
