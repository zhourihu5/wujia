package com.wujia.lib.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.LinearLayout

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12
 * description ：左侧菜单
 */
class HorizontalTabBar(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    private var mTabParams: LayoutParams? = null
    private var mOnTabSelectedListener: OnTabSelectedListener? = null
    private var mCurrentPos: Int = 0


    init {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        orientation = HORIZONTAL
        mTabParams = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    fun addItem(item: HorizontalTabItem?): HorizontalTabBar {

        if (null != item) {
            item.setOnClickListener(OnClickListener {
                if (null == mOnTabSelectedListener)
                    return@OnClickListener
                val pos = item.tabPosition
                if (pos != mCurrentPos) {

                    item.isSelected = true
                    mOnTabSelectedListener!!.onTabSelected(pos, mCurrentPos)
                    getChildAt(mCurrentPos).isSelected = false
                    mCurrentPos = pos
                }
            })
            item.tabPosition = childCount
            addView(item, mTabParams)
        }
        return this
    }


    fun setOnTabSelectedListener(mOnTabSelectedListener: OnTabSelectedListener) {
        this.mOnTabSelectedListener = mOnTabSelectedListener
    }

    interface OnTabSelectedListener {
        fun onTabSelected(position: Int, prePosition: Int)
    }
}
