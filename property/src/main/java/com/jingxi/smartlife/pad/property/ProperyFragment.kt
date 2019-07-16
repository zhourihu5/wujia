package com.jingxi.smartlife.pad.property

import android.os.Bundle

import com.jingxi.smartlife.pad.property.mvp.ProperyHomeFragment
import com.wujia.lib_common.base.BaseMainFragment
import com.wujia.lib_common.utils.LogUtil

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12 20:06
 * description ：物业服务 home
 */
class ProperyFragment : BaseMainFragment() {


    override fun getLayoutId(): Int {
        LogUtil.i("ProperyFragment getLayoutId")
        return R.layout.fragment_frame_layout
    }

    override fun initEventAndData() {

    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        if (findChildFragment(ProperyHomeFragment::class.java) == null) {
            loadRootFragment(R.id.fl_first_container, ProperyHomeFragment.newInstance(currentTab))
        }

    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        // 当对用户可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！
        LogUtil.i("ProperyFragment onSupportVisible")

    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        // 当对用户不可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！
        LogUtil.i("ProperyFragment onSupportInvisible")

    }

    companion object {

        fun newInstance(): ProperyFragment {
            val fragment = ProperyFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
