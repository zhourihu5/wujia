package com.jingxi.smartlife.pad.safe.mvp.view

import android.os.Bundle

import com.jingxi.smartlife.pad.safe.R
import com.wujia.lib_common.base.BaseFragment

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-02
 * description ：
 */
class SafeOtherFragment : BaseFragment() {
    override val layoutId: Int
        get() = R.layout.fragment_safe_other

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
    }

    companion object {

        fun newInstance(): SafeOtherFragment {
            val fragment = SafeOtherFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
