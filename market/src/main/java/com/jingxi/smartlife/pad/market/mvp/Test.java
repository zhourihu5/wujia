package com.jingxi.smartlife.pad.market.mvp;

import androidx.annotation.StringDef;

import com.jingxi.smartlife.pad.market.mvp.view.AllServiceFragment;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@StringDef({AllServiceFragment.TYPE_MY, AllServiceFragment.TYPE_GOV})
public @interface Test {
}
