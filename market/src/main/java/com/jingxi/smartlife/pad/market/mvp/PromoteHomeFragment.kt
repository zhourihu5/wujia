package com.jingxi.smartlife.pad.market.mvp

import android.os.Bundle
import com.jingxi.smartlife.pad.market.R
import com.jingxi.smartlife.pad.market.mvp.annotation.ServiceType
import com.jingxi.smartlife.pad.market.mvp.view.AllServiceFragment
import com.wujia.businesslib.TabFragment
import com.wujia.lib.widget.VerticalTabItem
import com.wujia.lib_common.utils.LogUtil
import me.yokeyword.fragmentation.SupportFragment

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
class PromoteHomeFragment : TabFragment() {
    override val layoutId: Int
        get() = R.layout.fragment_tab_home

    private val mFragments = arrayOfNulls<SupportFragment>(6)



    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        // 懒加载
        // 同级Fragment场景、ViewPager场景均适用
        LogUtil.i("MarketHomeFragment onLazyInitView")
        mTabBar = `$`(R.id.tab_home_tab_bar)

        mTabBar.addItem(VerticalTabItem(mActivity, R.mipmap.icon_market_leftnav_groupbuy_default, R.mipmap.icon_market_leftnav_groupbuy_highlight, R.string.group_buy_service))
                .addItem(VerticalTabItem(mActivity, R.mipmap.icon_market_leftnav_my_order_default, R.mipmap.icon_market_leftnav_my_order_highlight, R.string.order_service))
        if (currentTab >= mTabBar.childCount) {
            currentTab = 0
        }
        mFragments[0] = findChildFragment(GroupBuyServiceBaseFragment::class.java)
        mFragments[1] = findChildFragment(OrderBaseFragment::class.java)
        if (mFragments[0] == null) {
            mFragments[0] = GroupBuyServiceBaseFragment.newInstance()
            mFragments[1] = OrderBaseFragment()

            loadMultipleRootFragment(R.id.tab_content_container, currentTab,
                    mFragments[0],
                    mFragments[1]
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

        fun newInstance(pos: Int): PromoteHomeFragment {
            val fragment = PromoteHomeFragment()
            fragment.currentTab = pos
            //        Bundle args = new Bundle();
            //        args.putInt(Constants.ARG_PARAM_1, pos);
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