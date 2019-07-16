package com.jingxi.smartlife.pad.safe.mvp.view

import android.os.Bundle

import com.jingxi.smartlife.pad.safe.R
import com.wujia.lib_common.base.BaseFragment

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-02
 * description ：
 */
class SafeParkFragment : BaseFragment() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_safe_park
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
    }

    companion object {

        fun newInstance(): SafeParkFragment {
            val fragment = SafeParkFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
