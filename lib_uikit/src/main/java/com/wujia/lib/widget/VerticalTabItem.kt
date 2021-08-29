package com.wujia.lib.widget

import android.content.Context
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.wujia.lib.uikit.R

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12
 * description ：
 */
class VerticalTabItem(context: Context, @param:DrawableRes private val mIconDefault: Int, @param:DrawableRes private val mIconSelected: Int, @StringRes txt: Int) : FrameLayout(context) {

    var tabPosition: Int = 0
        set(position) {
            field = position
            if (position == 0) {
                isSelected = true
            }
        }
    private var mContext: Context? = null
    private var mIcon: ImageView? = null
    private var mPoint: ImageView? = null
    private var mTxt: TextView? = null

    init {
        init(context, txt)
    }

    private fun init(context: Context, txt: Int) {
        mContext = context
        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL
        layout.gravity = Gravity.CENTER

        //水波纹
        val typedArray = context.obtainStyledAttributes(intArrayOf(R.attr.selectableItemBackgroundBorderless))
        val drawable = typedArray.getDrawable(0)
        background = drawable
        typedArray.recycle()

        mIcon = ImageView(context)
        //        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
        val iconParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        iconParams.gravity = Gravity.CENTER
        mIcon!!.setImageResource(mIconDefault)
        //        mIcon.setColorFilter(ContextCompat.getColor(context, R.color.c9));
        layout.addView(mIcon, iconParams)


        mTxt = TextView(context)
        //        int top = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, getResources().getDisplayMetrics());

        val txtParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        mTxt!!.gravity = Gravity.CENTER
        mTxt!!.setText(txt)
        //        mTxt.setPadding(0, top, 0, 0);
        mTxt!!.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
        mTxt!!.setTextColor(ContextCompat.getColor(mContext!!, R.color.c9))
        layout.addView(mTxt, txtParams)

        mPoint = ImageView(context)
        //        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
        val poingParams = LayoutParams(16, 16)
        poingParams.gravity = Gravity.RIGHT
        poingParams.topMargin = 30
        poingParams.rightMargin = 30
        mPoint!!.setImageResource(R.drawable.bg_point_red)
        mPoint!!.visibility = View.INVISIBLE

        addView(layout)
        addView(mPoint, poingParams)
    }

    fun setPoint(state: Boolean) {
        mPoint!!.visibility = if (state) View.VISIBLE else View.INVISIBLE
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        if (selected) {
            //            mIcon.setColorFilter(ContextCompat.getColor(mContext, R.color.colorAccent));
            mIcon!!.setImageResource(mIconSelected)
            mTxt!!.setTextColor(ContextCompat.getColor(mContext!!, R.color.colorAccent))

        } else {
            mIcon!!.setImageResource(mIconDefault)
            //            mIcon.setColorFilter(ContextCompat.getColor(mContext, R.color.c9));
            mTxt!!.setTextColor(ContextCompat.getColor(mContext!!, R.color.c9))
        }
    }
}
