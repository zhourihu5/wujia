package com.wujia.lib_common.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-02
 * description ：
 */
public class FontUtils {
    public static final String Font_TYPE_EXTRA_LIGHT = "extra_light";


    @FontType
    private String type;

    @StringDef({Font_TYPE_EXTRA_LIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FontType {
    }

    public void setFontType(@FontType String type) {
        this.type = type;
    }

    @FontType
    public String getFontType() {
        return type;
    }

    private static String getFontAssetPath(@FontType String fontType) {
        switch (fontType) {
            case Font_TYPE_EXTRA_LIGHT:
                return "fonts/" + fontType + ".ttf";

            default:
                return "";
        }
    }

    @Nullable
    private static Typeface getTypeface(Context c, @FontType String fontType) {
        Typeface fromAsset = null;
        try {
            String assetPath = getFontAssetPath(fontType);
            fromAsset = Typeface.createFromAsset(c.getAssets(), assetPath);
//            fromAsset = Typeface.SANS_SERIF;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.w("Typeface error from " + fontType);
        }
        return fromAsset;
    }

    public static void changeFontTypeface(TextView textView, @FontType String fontType) {
        if (textView == null)
            return;
        Typeface typeface = getTypeface(textView.getContext(), fontType);
        textView.setTypeface(typeface);
    }

    public static void changeFontTypeface(@FontType String fontType, TextView... textViews) {
        for (TextView textView : textViews) {
            changeFontTypeface(textView, fontType);
        }
    }

}
