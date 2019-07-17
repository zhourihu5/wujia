package com.jingxi.smartlife.pad.property.mvp.view

import android.os.Bundle

import com.jingxi.smartlife.pad.property.R
import com.wujia.lib_common.base.BaseFragment


/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
class SimpleTelFragment : BaseFragment() {
    override val layoutId: Int
        get() =  R.layout.fragment_propery_tel_simple

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)

    }

    companion object {

        fun newInstance(): SimpleTelFragment {
            val fragment = SimpleTelFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
