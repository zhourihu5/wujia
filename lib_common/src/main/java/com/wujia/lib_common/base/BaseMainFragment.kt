package com.wujia.lib_common.base

import android.content.Context
import android.os.Bundle

/**
 * 懒加载
 * Created by YoKeyword on 16/6/5.
 */
abstract class BaseMainFragment : BaseFragment() {
    protected var currentTab = 0

    protected var _mBackToFirstListener: OnBackToFirstListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_TAB_POSITION, currentTab)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            currentTab = savedInstanceState.getInt(KEY_TAB_POSITION, currentTab)
        }
    }

    /**
     * 处理回退事件
     *
     * @return
     */
    //    @Override
    //    public boolean onBackPressedSupport() {
    //        if (getChildFragmentManager().getBackStackEntryCount() > 1) {
    //            popChild();
    //        } else {
    //            _mActivity.finish();
    //        }
    //        return true;
    //    }

    interface OnBackToFirstListener {
        fun onBackToFirstFragment()
    }


    fun switchTab(pos: Int) {
        currentTab = pos
    }

    companion object {

        private val KEY_TAB_POSITION = "tabPosition"
    }
}
