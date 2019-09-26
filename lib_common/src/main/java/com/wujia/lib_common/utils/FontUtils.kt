package com.wujia.lib_common.utils

import android.content.Context
import android.graphics.Typeface
import android.widget.TextView
import androidx.annotation.StringDef
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-02
 * description ：
 */
class FontUtils {


    @FontType
    @get:FontType
    var fontType: String? = null

    @StringDef(Font_TYPE_EXTRA_LIGHT)
    @Retention(RetentionPolicy.SOURCE)
    annotation class FontType

    companion object {
        const val Font_TYPE_EXTRA_LIGHT = "extra_light"

        private fun getFontAssetPath(@FontType fontType: String): String {
            when (fontType) {
                Font_TYPE_EXTRA_LIGHT -> return "fonts/$fontType.ttf"

                else -> return ""
            }
        }

        private fun getTypeface(c: Context, @FontType fontType: String): Typeface? {
            var fromAsset: Typeface? = null
            try {
                val assetPath = getFontAssetPath(fontType)
                fromAsset = Typeface.createFromAsset(c.assets, assetPath)
                //            fromAsset = Typeface.SANS_SERIF;
            } catch (e: Exception) {
                e.printStackTrace()
                LogUtil.w("Typeface error from $fontType")
            }

            return fromAsset
        }

        fun changeFontTypeface(textView: TextView?, @FontType fontType: String) {
            if (textView == null)
                return
            val typeface = getTypeface(textView.context, fontType)
            textView.typeface = typeface
        }

        fun changeFontTypeface(@FontType fontType: String, vararg textViews: TextView) {
            for (textView in textViews) {
                changeFontTypeface(textView, fontType)
            }
        }
    }

}
