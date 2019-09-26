package com.jingxi.smartlife.pad.market.mvp

import android.os.Bundle
import com.jingxi.smartlife.pad.market.R
import com.jingxi.smartlife.pad.market.mvp.view.GovServiceFragment
import com.wujia.lib_common.base.BaseMainFragment

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12 20:06
 * description ：服务市场 home
 */
class GovServiceBaseFragment : BaseMainFragment() {
    override val layoutId: Int
        get() = R.layout.fragment_frame_layout


    override fun initEventAndData() {

    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        // 懒加载
        // 同级Fragment场景、ViewPager场景均适用
        val marketHomeFragment = findChildFragment(GovServiceFragment::class.java)
        if (marketHomeFragment == null) {
            loadRootFragment(R.id.fl_first_container, GovServiceFragment())
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

        fun newInstance(): GovServiceBaseFragment {
            val fragment = GovServiceBaseFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
