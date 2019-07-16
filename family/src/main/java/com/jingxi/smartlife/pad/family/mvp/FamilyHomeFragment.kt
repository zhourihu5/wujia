package com.jingxi.smartlife.pad.family.mvp

import android.os.Bundle

import com.jingxi.smartlife.pad.family.R
import com.wujia.businesslib.TabFragment
import com.wujia.lib.widget.VerticalTabBar
import com.wujia.lib.widget.VerticalTabItem
import com.wujia.lib_common.utils.LogUtil

import me.yokeyword.fragmentation.SupportFragment

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12 20:06
 * description ：智能家居 home
 */
class FamilyHomeFragment : TabFragment() {
    private val mFragments = arrayOfNulls<SupportFragment>(6)


    override fun getLayoutId(): Int {
        LogUtil.i("FamilyFragment getLayoutId")

        return R.layout.fragment_tab_home
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        // 懒加载
        // 同级Fragment场景、ViewPager场景均适用
        LogUtil.i("FamilyFragment onLazyInitView")
        mTabBar = `$`(R.id.tab_home_tab_bar)

        val firstFragment = findFragment(AllFragment::class.java)
        if (firstFragment == null) {
            mFragments[0] = AllFragment.newInstance()
            loadMultipleRootFragment(R.id.tab_content_container, 0, mFragments[0])
        } else {
            mFragments[0] = firstFragment
        }

        mTabBar.addItem(VerticalTabItem(mActivity, R.mipmap.icon_smart_leftnav_all_default, R.mipmap.icon_smart_leftnav_all_selected, R.string.home))
                .addItem(VerticalTabItem(mActivity, R.mipmap.icon_smart_leftnav_living_default, R.mipmap.icon_smart_leftnav_living_selected, R.string.drawing_room))
                .addItem(VerticalTabItem(mActivity, R.mipmap.icon_smart_leftnav_bed2_default, R.mipmap.icon_smart_leftnav_bed2_selected, R.string.master_room))
                .addItem(VerticalTabItem(mActivity, R.mipmap.icon_smart_leftnav_bed2_default, R.mipmap.icon_smart_leftnav_bed2_selected, R.string.second_room))
                .addItem(VerticalTabItem(mActivity, R.mipmap.icon_smart_leftnav_kitchen_default, R.mipmap.icon_smart_leftnav_kitchen_selected, R.string.kitchen))
                .addItem(VerticalTabItem(mActivity, R.mipmap.icon_smart_leftnav_bath_default, R.mipmap.icon_smart_leftnav_bath_selected, R.string.washroom))


        mTabBar.setOnTabSelectedListener { position, prePosition ->
            //                showHideFragment(mFragments[0], mFragments[prePosition]);//
            parentSwitchTab()
        }
        switchTab(currentTab)
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

        fun newInstance(tab: Int): FamilyHomeFragment {
            val fragment = FamilyHomeFragment()
            fragment.currentTab = tab
            //        Bundle args = new Bundle();
            //        args.putInt(Constants.ARG_PARAM_1, tab);
            //        fragment.setArguments(args);
            return fragment
        }
    }

}
