package com.jingxi.smartlife.pad.mvp.util

import androidx.annotation.IntDef

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

@Retention(RetentionPolicy.SOURCE)
@IntDef(ScreenManager.LIGHT_MODE_AUTO, ScreenManager.LIGHT_MODE_MANUAL, ScreenManager.LIGHT_MODE_FAILED)
annotation class LightMode
