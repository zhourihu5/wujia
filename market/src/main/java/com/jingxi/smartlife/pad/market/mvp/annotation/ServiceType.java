package com.jingxi.smartlife.pad.market.mvp.annotation;

import androidx.annotation.StringDef;

import com.jingxi.smartlife.pad.market.mvp.view.AllServiceFragment;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
@StringDef({AllServiceFragment.TYPE_ALL, AllServiceFragment.TYPE_GOV, AllServiceFragment.TYPE_MY})
public @interface ServiceType {
}
