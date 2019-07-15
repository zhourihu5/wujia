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

        fun newInstance(): MessageFragment {
            val fragment = MessageFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
