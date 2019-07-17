package com.jingxi.smartlife.pad.family

import android.os.Bundle

import com.jingxi.smartlife.pad.family.mvp.FamilyHomeFragment
import com.wujia.lib_common.base.BaseMainFragment
import com.wujia.lib_common.utils.LogUtil

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12 20:06
 * description ：智能家居 home
 */
class FamilyFragment : BaseMainFragment() {
    override val layoutId: Int
        get() = R.layout.fragment_family

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        // 懒加载
        // 同级Fragment场景、ViewPager场景均适用
        LogUtil.i("FamilyFragment onLazyInitView")

        if (findChildFragment(FamilyHomeFragment::class.java) == null) {
            loadRootFragment(R.id.fl_first_container, FamilyHomeFragment.newInstance(currentTab))
        }
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        // 当对用户可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！
        LogUtil.i("FamilyFragment onSupportVisible")

    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        // 当对用户不可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！
        LogUtil.i("FamilyFragment onSupportInvisible")

    }

    companion object {

        fun newInstance(): FamilyFragment {
            val fragment = FamilyFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
