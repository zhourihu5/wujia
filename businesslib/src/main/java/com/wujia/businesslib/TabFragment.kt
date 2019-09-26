package com.wujia.businesslib

import android.os.Bundle
import com.wujia.lib.widget.VerticalTabBar
import com.wujia.lib_common.base.BaseFragment
import com.wujia.lib_common.base.BaseMainFragment

/**
 * 懒加载
 * Created by YoKeyword on 16/6/5.
 */
abstract class TabFragment : BaseFragment() {
    protected var currentTab = 0
    protected lateinit var mTabBar: VerticalTabBar
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_TAB_POSITION, currentTab)
    }

    protected fun getCurrentTab(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            currentTab = savedInstanceState.getInt(KEY_TAB_POSITION, currentTab)
        }
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        getCurrentTab(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getCurrentTab(savedInstanceState)
    }

    protected fun parentSwitchTab() {
        val fragment = parentFragment
        if (fragment is BaseMainFragment) {
            fragment.switchTab(currentTab)
        }
    }


    fun switchTab(pos: Int) {
        currentTab = pos
        parentSwitchTab()
        if (mTabBar != null) {
            mTabBar!!.getChildAt(pos).performClick()
        }
    }

    companion object {

        protected val KEY_TAB_POSITION = "tabPosition"
    }


}
