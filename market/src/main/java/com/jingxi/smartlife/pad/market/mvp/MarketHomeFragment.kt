package com.jingxi.smartlife.pad.market.mvp

import android.os.Bundle
import com.jingxi.smartlife.pad.market.R
import com.jingxi.smartlife.pad.market.mvp.annotation.ServiceType
import com.jingxi.smartlife.pad.market.mvp.view.*
import com.wujia.businesslib.TabFragment
import com.wujia.lib.widget.VerticalTabItem
import com.wujia.lib_common.utils.LogUtil
import me.yokeyword.fragmentation.SupportFragment

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
class MarketHomeFragment : TabFragment() {
    override val layoutId: Int
        get() = R.layout.fragment_tab_home

    private val mFragments = arrayOfNulls<SupportFragment>(6)



    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        // 懒加载
        // 同级Fragment场景、ViewPager场景均适用
        LogUtil.i("MarketHomeFragment onLazyInitView")
        mTabBar = `$`(R.id.tab_home_tab_bar)

        mTabBar.addItem(VerticalTabItem(mActivity, R.mipmap.icon_market_leftnav_my_default, R.mipmap.icon_market_leftnav_my_highlight, R.string.my_service))
                .addItem(VerticalTabItem(mActivity, R.mipmap.icon_market_leftnav_find_default, R.mipmap.icon_market_leftnav_find_highlight, R.string.find_service))
                .addItem(VerticalTabItem(mActivity, R.mipmap.icon_market_leftnav_government_default, R.mipmap.icon_market_leftnav_government_highlight, R.string.gov_service))
                .addItem(VerticalTabItem(mActivity, R.mipmap.icon_market_leftnav_all_default, R.mipmap.icon_market_leftnav_all_highlight, R.string.all_service))
                .addItem(VerticalTabItem(mActivity, R.mipmap.icon_market_leftnav_find_default, R.mipmap.icon_market_leftnav_find_highlight, R.string.group_buy_service))
                .addItem(VerticalTabItem(mActivity, R.mipmap.icon_market_leftnav_find_default, R.mipmap.icon_market_leftnav_find_highlight, R.string.order_service))
        if (currentTab >= mTabBar.childCount) {
            currentTab = 0
        }
        mFragments[0] = findChildFragment(MyServiceFragment::class.java)
        mFragments[1] = findChildFragment(FindServiceFragment::class.java)
        mFragments[2] = findChildFragment(GovServiceFragment::class.java)
        mFragments[3] = findChildFragment(AllServiceFragment::class.java)
        mFragments[TAB_GROUP_BUY] = findChildFragment(GroupBuyFragment::class.java)
        mFragments[5] = findChildFragment(OrderFragment::class.java)
        if (mFragments[0] == null) {
//            val type = getServiceType(currentTab)
            mFragments[0] = MyServiceFragment()
            mFragments[1] = FindServiceFragment.newInstance()
            mFragments[2] = GovServiceFragment()
            mFragments[3] = AllServiceFragment()
            mFragments[TAB_GROUP_BUY] = GroupBuyFragment.newInstance()
            mFragments[5] = OrderFragment()

            loadMultipleRootFragment(R.id.tab_content_container, currentTab,
                    mFragments[0],
                    mFragments[1],
                    mFragments[2],
                    mFragments[3],
                    mFragments[4],
                    mFragments[5]
            )
        }

        LogUtil.i("markethomefragment,currentTab==$currentTab")
        mTabBar.setOnTabSelectedListener { position, prePosition ->
            currentTab = position
            showHideFragment(mFragments[position], mFragments[prePosition])
            parentSwitchTab()
        }
        switchTab(currentTab)
    }


    @ServiceType
    protected fun getServiceType(position: Int): String {
        var type = AllServiceFragment.TYPE_MY
        when (position) {
            0 -> type = AllServiceFragment.TYPE_MY
            2 -> type = AllServiceFragment.TYPE_GOV
            3 -> type = AllServiceFragment.TYPE_ALL
        }
        return type
    }

    companion object {

        fun newInstance(pos: Int): MarketHomeFragment {
            val fragment = MarketHomeFragment()
            fragment.currentTab = pos
            //        Bundle args = new Bundle();
            //        args.putInt(Constants.ARG_PARAM_1, pos);
            //        fragment.setArguments(args);
            return fragment
        }
        val TAB_GROUP_BUY:Int=4
    }

    //    @Override
    //    public void onSupportVisible() {
    //        super.onSupportVisible();
    //        if (currentTab > 0) {
    //            mTabBar.getChildAt(currentTab).performClick();
    //        }
    //    }

}