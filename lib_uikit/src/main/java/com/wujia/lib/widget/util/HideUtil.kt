/*
 * Copyright 2016 yinglan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wujia.lib.widget.util

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.os.IBinder
import androidx.recyclerview.widget.RecyclerView
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AbsListView
import android.widget.EditText
import android.widget.ScrollView

/**
 * Created by yinglan
 */
class HideUtil
/**
 * @param activity
 */
private constructor(activity: Activity, content: ViewGroup?) {

    init {
        var content = content
        if (content == null) {
            content = activity.findViewById(android.R.id.content)
        }
        getScrollView(content, activity)
        content!!.setOnTouchListener { view, motionEvent ->
            dispatchTouchEvent(activity, motionEvent)

            false
        }
    }

    private fun getScrollView(viewGroup: ViewGroup?, activity: Activity) {
        if (null == viewGroup) {
            return
        }
        val count = viewGroup.childCount
        for (i in 0 until count) {
            val view = viewGroup.getChildAt(i)
            if (view is ScrollView) {
                view.setOnTouchListener { view, motionEvent ->
                    dispatchTouchEvent(activity, motionEvent)

                    false
                }
            } else if (view is AbsListView) {
                view.setOnTouchListener { view, motionEvent ->
                    dispatchTouchEvent(activity, motionEvent)

                    false
                }
            } else if (view is RecyclerView) {
                view.setOnTouchListener { view, motionEvent ->
                    dispatchTouchEvent(activity, motionEvent)

                    false
                }
            } else if (view is ViewGroup) {

                this.getScrollView(view, activity)
            }
        }
    }

    /**
     * @param mActivity
     * @param ev
     * @return
     */
    fun dispatchTouchEvent(mActivity: Activity, ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val v = mActivity.currentFocus
            if (null != v && isShouldHideInput(v, ev)) {
                hideSoftInput(mActivity, v.windowToken)
            }
        }
        return false
    }

    /**
     * @param v
     * @param event
     * @return
     */
    private fun isShouldHideInput(v: View, event: MotionEvent): Boolean {
        if (v is EditText) {
            val rect = Rect()
            v.getHitRect(rect)
            return !rect.contains(event.x.toInt(), event.y.toInt())
        }
        return true
    }

    /**
     * @param mActivity
     * @param token
     */
    private fun hideSoftInput(mActivity: Activity, token: IBinder?) {
        if (token != null) {
            val im = mActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    companion object {

        /**
         * Initialization method
         *
         * @param activity
         */
        fun init(activity: Activity) {
            HideUtil(activity, null)
        }

        /**
         * Can pass the outer layout
         *
         * @param activity
         * @param content
         */
        fun init(activity: Activity, content: ViewGroup) {
            HideUtil(activity, content)
        }
    }
}
