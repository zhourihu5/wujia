package com.jingxi.smartlife.pad.market.mvp

import android.os.Bundle
import com.jingxi.smartlife.pad.market.R
import com.jingxi.smartlife.pad.market.mvp.view.GroupBuyFragment
import com.wujia.lib_common.base.BaseMainFragment

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12 20:06
 * description ：服务市场 home
 */
class GroupBuyServiceBaseFragment : BaseMainFragment() {
    override val layoutId: Int
        get() = R.layout.fragment_frame_layout


    override fun initEventAndData() {

    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        // 懒加载
        // 同级Fragment场景、ViewPager场景均适用
        val marketHomeFragment = findChildFragment(GroupBuyFragment::class.java)
        if (marketHomeFragment == null) {
            loadRootFragment(R.id.fl_first_container, GroupBuyFragment())
        }
    }

    companion object {

        fun newInstance(): GroupBuyServiceBaseFragment {
            val fragment = GroupBuyServiceBaseFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
