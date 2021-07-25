package com.wujia.lib.imageloader

import android.content.Context
import android.util.TypedValue

/**
 * Created by xmren on 2017/8/9.
 */

class DensityUtil private constructor() {
    init {
        throw UnsupportedOperationException("cannot be instantiated")
    }

    companion object {

        /**
         * dp转px
         */
        fun dp2px(context: Context, dpVal: Float): Int {
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    dpVal, context.resources.displayMetrics).toInt()
        }

    }

}

