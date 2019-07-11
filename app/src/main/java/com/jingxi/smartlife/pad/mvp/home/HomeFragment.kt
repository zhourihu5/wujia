package com.jingxi.smartlife.pad.mvp.home

import android.os.Bundle

import com.jingxi.smartlife.pad.R
import com.wujia.lib_common.base.BaseMainFragment


/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12 20:06
 * description ： home
 */
class HomeFragment : BaseMainFragment() {


    override fun getLayoutId(): Int {
        return R.layout.fragment_frame_layout
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        // 懒加载
        // 同级Fragment场景、ViewPager场景均适用
        if (findChildFragment(HomeHomeFragment::class.java) == null) {
            loadRootFragment(R.id.fl_first_container, HomeHomeFragment.newInstance())
        }
    }


    override fun onSupportVisible() {
        super.onSupportVisible()
        // 当对用户可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！

    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        // 当对用户不可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！

    }

    companion object {

        fun newInstance(): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

}
