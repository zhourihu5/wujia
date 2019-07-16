package com.jingxi.smartlife.pad.message

import android.os.Bundle

import com.jingxi.smartlife.pad.message.mvp.MessageHomeFragment
import com.wujia.lib_common.base.BaseMainFragment

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12 20:06
 * description ：消息通知 home
 */
class MessageFragment : BaseMainFragment() {


    override fun getLayoutId(): Int {
        return R.layout.fragment_frame_layout
    }

    override fun initEventAndData() {

    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        // 懒加载
        // 同级Fragment场景、ViewPager场景均适用
        if (findChildFragment(MessageHomeFragment::class.java) == null) {
            loadRootFragment(R.id.fl_first_container, MessageHomeFragment.newInstance(currentTab))
        }
    }
    companion object {

        fun newInstance(): MessageFragment {
            val fragment = MessageFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
