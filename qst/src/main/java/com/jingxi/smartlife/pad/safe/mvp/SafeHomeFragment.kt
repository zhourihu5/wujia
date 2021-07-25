package com.jingxi.smartlife.pad.safe.mvp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jingxi.smartlife.pad.safe.R
import com.jingxi.smartlife.pad.safe.mvp.view.SafeOutsideFragment
import com.wujia.businesslib.TabFragment
import com.wujia.lib.widget.VerticalTabItem
import com.wujia.lib_common.utils.LogUtil
import me.yokeyword.fragmentation.SupportFragment

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12 20:06
 * description ：智能家居 home
 */
class SafeHomeFragment : TabFragment() {
    override val layoutId: Int
        get() = R.layout.fragment_tab_home
    private val mFragments = arrayOfNulls<SupportFragment>(4)


    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        mTabBar = `$`(R.id.tab_home_tab_bar)

        val firstFragment = findFragment(SafeOutsideFragment::class.java)
        if (firstFragment == null) {
            mFragments[0] = SafeOutsideFragment.newInstance()
            //            mFragments[1] = SafeOtherFragment.newInstance();
            //            mFragments[2] = SafeOtherFragment.newInstance();
            //            mFragments[3] = SafeParkFragment.newInstance();
            //            loadMultipleRootFragment(R.id.tab_content_container, 0, mFragments[0], mFragments[1], mFragments[2], mFragments[3]);
            loadMultipleRootFragment(R.id.tab_content_container, 0, mFragments[0])
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用
            mFragments[0] = firstFragment
            //            mFragments[1] = findFragment(SafeOtherFragment.class);
            //            mFragments[2] = findFragment(SafeOtherFragment.class);
            //            mFragments[3] = findFragment(SafeParkFragment.class);
        }

        mTabBar.addItem(VerticalTabItem(mActivity, R.mipmap.icon_safe_leftnav_call_default, R.mipmap.icon_safe_leftnav_call_selected, R.string.outside_machine))
        //                .addItem(new VerticalTabItem(mActivity, R.mipmap.icon_safe_leftnav_camera_default, R.mipmap.icon_safe_leftnav_camera_selected, R.string.backyard))
        //                .addItem(new VerticalTabItem(mActivity, R.mipmap.icon_safe_leftnav_camera_default, R.mipmap.icon_safe_leftnav_camera_selected, R.string.ease_garth))
        //                .addItem(new VerticalTabItem(mActivity, R.mipmap.icon_safe_leftnav_camera_default, R.mipmap.icon_safe_leftnav_camera_selected, R.string.park));


        mTabBar.setOnTabSelectedListener { position, prePosition ->
            showHideFragment(mFragments[position], mFragments[prePosition])
            currentTab = position
            parentSwitchTab()
        }
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        // 当对用户可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！
        LogUtil.i("SafeHomeFragment onSupportVisible")

    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
        // 当对用户不可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！
        LogUtil.i("SafeHomeFragment onSupportInvisible")

    }

    companion object {

        fun newInstance(currentTag: Int): SafeHomeFragment {
            val fragment = SafeHomeFragment()
            fragment.currentTab = currentTag
            //        Bundle args = new Bundle();
            //        args.putInt(KEY_TAB, currentTag);
            //        fragment.setArguments(args);
            return fragment
        }
    }

}
