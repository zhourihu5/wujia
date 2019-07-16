package com.jingxi.smartlife.pad.message.mvp

import android.os.Bundle
import com.jingxi.smartlife.pad.message.R
import com.jingxi.smartlife.pad.message.mvp.view.AllMsgFragment
import com.jingxi.smartlife.pad.message.mvp.view.MsgType
import com.wujia.businesslib.TabFragment
import com.wujia.lib.widget.VerticalTabItem
import com.wujia.lib_common.utils.LogUtil


/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
class MessageHomeFragment : TabFragment() {

    override fun getLayoutId(): Int {
        LogUtil.i("FamilyFragment getLayoutId")

        return R.layout.fragment_tab_home
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        LogUtil.i("FamilyFragment onLazyInitView")
        mTabBar = `$`(R.id.tab_home_tab_bar)//must be initilized


        var msgFragment = findFragment(AllMsgFragment::class.java)
        if (msgFragment == null) {
            msgFragment = AllMsgFragment.newInstance()
            loadRootFragment(R.id.tab_content_container, msgFragment)
        }

        mTabBar.addItem(VerticalTabItem(mActivity, R.mipmap.icon_news_leftnav_all_default, R.mipmap.icon_news_leftnav_all_highlight, R.string.all_msg))
                .addItem(VerticalTabItem(mActivity, R.mipmap.icon_news_leftnav_property_default, R.mipmap.icon_news_leftnav_property_highlight, R.string.wuye_notify))
                .addItem(VerticalTabItem(mActivity, R.mipmap.icon_news_leftnav_community_default, R.mipmap.icon_news_leftnav_community_highlight, R.string.shequ_notify))
                .addItem(VerticalTabItem(mActivity, R.mipmap.icon_news_leftnav_system_default, R.mipmap.icon_news_leftnav_system_highlight, R.string.app_notify))

        mTabBar.setOnTabSelectedListener { position, prePosition ->
            val type = getType(position)
            msgFragment.type = type
            currentTab = position
            parentSwitchTab()
        }
        switchTab(currentTab)

    }

    @MsgType
    protected fun getType(position: Int): String {
        var type = AllMsgFragment.MSG_TYPE_ALL
        when (position) {
            1 -> type = AllMsgFragment.MSG_TYPE_PROPERTY
            2 -> type = AllMsgFragment.MSG_TYPE_COMMUNITY
            3 -> type = AllMsgFragment.MSG_TYPE_SYSTEM//系统消息
        }
        return type
    }

    companion object {

        fun newInstance(currentTab: Int): MessageHomeFragment {
            val fragment = MessageHomeFragment()
            val args = Bundle()
            fragment.currentTab = currentTab
            fragment.arguments = args
            return fragment
        }
    }

}
