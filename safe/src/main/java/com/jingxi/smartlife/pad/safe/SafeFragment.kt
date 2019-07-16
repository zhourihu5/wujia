package com.jingxi.smartlife.pad.safe

import android.os.Bundle

import com.jingxi.smartlife.pad.safe.mvp.SafeHomeFragment
import com.wujia.lib_common.base.BaseMainFragment
import com.wujia.lib_common.utils.LogUtil

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12 20:06
 * description ：可视安防 home
 */
class SafeFragment : BaseMainFragment() {


    override fun getLayoutId(): Int {
        LogUtil.i("SafeFragment getLayoutId")
        return R.layout.fragment_safe
    }

    override fun initEventAndData() {

    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        // 懒加载
        // 同级Fragment场景、ViewPager场景均适用
        LogUtil.i("SafeFragment onLazyInitView")

        if (findChildFragment(SafeHomeFragment::class.java) == null) {
            loadRootFragment(R.id.fl_first_container, SafeHomeFragment.newInstance(currentTab))
        }
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        // 当对用户可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！
        LogUtil.i("SafeFragment onSupportVisible")

    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        // 当对用户不可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！
        LogUtil.i("SafeFragment onSupportInvisible")

    }

    companion object {

        fun newInstance(): SafeFragment {
            val fragment = SafeFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
