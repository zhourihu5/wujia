package com.jingxi.smartlife.pad.mvp

import android.os.Bundle

import com.jingxi.smartlife.pad.R
import com.jingxi.smartlife.pad.mvp.setting.view.SettingHomeFragment
import com.wujia.lib_common.base.BaseMainFragment


/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12 20:06
 * description ：物业服务 home
 */
class SettingFragment : BaseMainFragment() {
    override val layoutId: Int
        get() = R.layout.fragment_setting

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        // 懒加载
        // 同级Fragment场景、ViewPager场景均适用
        if (findChildFragment(SettingHomeFragment::class.java) == null) {
            loadRootFragment(R.id.fl_first_container, SettingHomeFragment.newInstance())
        }
    }

    companion object {

        fun newInstance(): SettingFragment {
            val fragment = SettingFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}
