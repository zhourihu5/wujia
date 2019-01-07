package com.abctime.lib_common.utils.font;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.widget.TextView;

import com.abctime.lib_common.utils.LogUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by xmren on 2018/5/17.
 */

public class FontUtils {
    public static final String Font_TYPE_FZRUISYJW_CU = "fzruisyjw_cu";
    public static final String FONT_TYPE_FZRUISYJW_DA = "fzruisyjw_da";
    public static final String FONT_TYPE_FZRUISYJW_XIAN = "fzruisyjw_xian";
    public static final String FONT_TYPE_FZRUISYJW_ZHONG = "fzruisyjw_zhong";
    public static final String FONT_TYPE_FZRUISYJW_ZHUN = "fzruisyjw_zhun";
    public static final String FONT_TYPE_FZRUISYZW = "fzruisyzw";
    public static final String FONT_TYPE_MT_BOLD = "mt_bold";


    @FontType
    private String type;

    @StringDef({Font_TYPE_FZRUISYJW_CU, FONT_TYPE_FZRUISYJW_DA, FONT_TYPE_FZRUISYJW_XIAN, FONT_TYPE_FZRUISYJW_ZHONG, FONT_TYPE_FZRUISYJW_ZHUN, FONT_TYPE_FZRUISYZW, FONT_TYPE_MT_BOLD})
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
            case Font_TYPE_FZRUISYJW_CU:
                return "fonts/fzruisyjw_cu.ttf";
            case FONT_TYPE_FZRUISYJW_DA:
                return "fonts/fzruisyjw_da.ttf";
            case FONT_TYPE_FZRUISYJW_XIAN:
                return "fonts/fzruisyjw_xian.ttf";
            case FONT_TYPE_FZRUISYJW_ZHONG:
                return "fonts/fzruisyjw_zhong.ttf";
            case FONT_TYPE_FZRUISYJW_ZHUN:
                return "fonts/fzruisyjw_zhun.ttf";
            case FONT_TYPE_FZRUISYZW:
                return "fonts/fzruisyzw.ttf";
            case FONT_TYPE_MT_BOLD:
                return "fonts/arial_rounded_mt_ bold.ttf";
            default:
                return "";
        }
    }

    @Nullable
    public static Typeface getTypeface(Context c, @FontType String fontType) {
        Typeface fromAsset = null;
        try {
//            String assetPath = getFontAssetPath(fontType);
//            fromAsset = Typeface.createFromAsset(c.getAssets(), assetPath);
            fromAsset = Typeface.SANS_SERIF;
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

