package com.wujia.lib.widget

import android.content.Context
import android.util.AttributeSet

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-23
 * description ：
 */
class WjSwitch(context: Context, attrs: AttributeSet)//        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.WjSwitch);
//        float radius = a.getDimension(R.styleable.WjSwitch_radius, 0);
//        a.recycle();
//        setBackgroundResource(R.drawable.bg_switch_checkor);
    : androidx.appcompat.widget.SwitchCompat(context, attrs)
