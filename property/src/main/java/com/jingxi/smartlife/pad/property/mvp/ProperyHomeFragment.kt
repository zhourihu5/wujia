package com.jingxi.smartlife.pad.property.mvp

import android.os.Bundle
import com.jingxi.smartlife.pad.property.R
import com.jingxi.smartlife.pad.property.mvp.view.SimpleFixFragment
import com.jingxi.smartlife.pad.property.mvp.view.YellowPageFragment
import com.wujia.businesslib.TabFragment
import com.wujia.lib.widget.VerticalTabItem
import me.yokeyword.fragmentation.SupportFragment

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
class ProperyHomeFragment : TabFragment() {
    override val layoutId: Int
        get() = R.layout.fragment_tab_home

    private val mFragments = arrayOfNulls<SupportFragment>(6)

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        mTabBar = `$`(R.id.tab_home_tab_bar)
        mTabBar.addItem(VerticalTabItem(mActivity, R.mipmap.yellow_page, R.mipmap.yellow_page, R.string.community_yellow_page))
//                .addItem(VerticalTabItem(mActivity, R.mipmap.icon_serve_leftnav_phone_default, R.mipmap.icon_serve_leftnav_phone_highlight, R.string.tel_select))

        if (currentTab >= mFragments.size) {
            currentTab = 0
        }

        mFragments[0] = findChildFragment(SimpleFixFragment::class.java)
//        mFragments[1] = findChildFragment(SimpleTelFragment::class.java)
        if (mFragments[0] == null) {
            mFragments[0] = YellowPageFragment.newInstance()
//            mFragments[1] = SimpleTelFragment.newInstance()
            loadMultipleRootFragment(R.id.tab_content_container, currentTab, mFragments[0])
        }

        mTabBar.setOnTabSelectedListener { position, prePosition ->
            showHideFragment(mFragments[position], mFragments[prePosition])
            currentTab = position
            parentSwitchTab()
        }
        switchTab(currentTab)
    }

    companion object {

        fun newInstance(tabIndex: Int): ProperyHomeFragment {
            val fragment = ProperyHomeFragment()
            fragment.currentTab = tabIndex
            //        Bundle args = new Bundle();
            //        args.putInt(Constants.ARG_PARAM_1, tabIndex);
            //        fragment.setArguments(args);
            return fragment
        }
    }


    //    @Override
    //    public void onSupportVisible() {
    //        super.onSupportVisible();
    //        if (currentTab > 0) {
    //            mTabBar.getChildAt(currentTab).performClick();
    //        }
    //    }

}
