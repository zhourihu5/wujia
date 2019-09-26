package com.wujia.lib.widget

import android.content.Context
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import android.util.TypedValue
import android.view.Gravity

import com.wujia.lib.uikit.R

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12
 * description ：
 */
class HorizontalTabItem(context: Context, @StringRes txt: Int) : androidx.appcompat.widget.AppCompatTextView(context) {

    var tabPosition: Int = 0
        set(position) {
            field = position
            if (position == 0) {
                isSelected = true
            }
        }
    private var mContext: Context? = null
    private var textColorDefault: Int = 0
    private var textColorSelected: Int = 0

    init {
        init(context, txt)
    }

    private fun init(context: Context, txt: Int) {
        textColorDefault = ContextCompat.getColor(context, R.color.c8)
        textColorSelected = ContextCompat.getColor(context, R.color.white)

        gravity = Gravity.CENTER
        mContext = context
        //水波纹
        //        TypedArray typedArray = context.obtainStyledAttributes(new int[]{R.attr.selectableItemBackgroundBorderless});
        //        Drawable drawable = typedArray.getDrawable(0);
        //        setBackground(drawable);
        //        typedArray.recycle();

        setTextSize(TypedValue.COMPLEX_UNIT_SP, 15.0f)
        setTextColor(textColorDefault)
        setPadding(25, 10, 25, 10)
        setText(txt)
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        if (selected) {
            setTextColor(textColorSelected)
            setBackgroundResource(R.drawable.bg_accent_raduis_24_shape)
        } else {
            setTextColor(textColorDefault)
            setBackgroundResource(R.drawable.bg_transparent_shape)
        }
    }
}
