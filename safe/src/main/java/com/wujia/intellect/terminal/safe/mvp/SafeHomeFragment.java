package com.wujia.intellect.terminal.safe.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jingxi.smartlife.pad.sdk.JXPadSdk;
import com.jingxi.smartlife.pad.sdk.doorAccess.DoorAccessManager;
import com.jingxi.smartlife.pad.sdk.doorAccess.base.bean.DoorDevice;
import com.jingxi.smartlife.pad.sdk.doorAccess.base.ui.DoorAccessListUI;
import com.jingxi.smartlife.pad.sdk.doorAccess.base.ui.DoorAccessListener;
import com.wujia.intellect.terminal.safe.R;
import com.wujia.lib.widget.VerticalTabBar;
import com.wujia.lib.widget.VerticalTabItem;
import com.wujia.lib_common.base.BaseFragment;
import com.wujia.lib_common.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12 20:06
 * description ：智能家居 home
 */
public class SafeHomeFragment extends BaseFragment implements
        DoorAccessListUI, DoorAccessListener {
    private VerticalTabBar mTabBar;
    private SupportFragment[] mFragments = new SupportFragment[4];

    private DoorAccessManager manager;
    private List<DoorDevice> mDevices = new ArrayList<>();

    public SafeHomeFragment() {

    }

    public static SafeHomeFragment newInstance() {
        SafeHomeFragment fragment = new SafeHomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        LogUtil.i("SafeHomeFragment getLayoutId");

        return R.layout.fragment_safe_home;
    }

    @Override
    protected void initEventAndData() {
        mTabBar = $(R.id.family_main_tab_bar);

        SupportFragment firstFragment = findFragment(SafeOutsideFragment.class);
        if (firstFragment == null) {
            mFragments[0] = SafeOutsideFragment.newInstance();
            loadRootFragment(R.id.family_container, mFragments[0]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题

            // 这里我们需要拿到mFragments的引用
            mFragments[0] = firstFragment;
        }

        mTabBar.addItem(new VerticalTabItem(mActivity, R.mipmap.icon_safe_leftnav_call_default, R.string.outside_machine))
                .addItem(new VerticalTabItem(mActivity, R.mipmap.icon_safe_leftnav_camera_default, R.string.backyard))
                .addItem(new VerticalTabItem(mActivity, R.mipmap.icon_safe_leftnav_camera_default, R.string.ease_garth))
                .addItem(new VerticalTabItem(mActivity, R.mipmap.icon_safe_leftnav_camera_default, R.string.park));


        mTabBar.setOnTabSelectedListener(new VerticalTabBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[0], mFragments[prePosition]);
            }
        });

        JXPadSdk.init(mActivity.getApplication());
        JXPadSdk.initDoorAccess("001901109CDB0000", "01");
        manager = JXPadSdk.getDoorAccessManager();
        manager.setListUIListener(this);
        manager.setDoorAccessListener(this);

        setData();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        // 懒加载
        // 同级Fragment场景、ViewPager场景均适用
        LogUtil.i("SafeHomeFragment onLazyInitView");

    }


    private void setData() {
        mDevices.clear();
        List<DoorDevice> devices = manager.getDevices();
        if (devices != null) {
            mDevices.addAll(devices);
        }
        LogUtil.i("devices size = " + mDevices.size());
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        // 当对用户可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！
        LogUtil.i("SafeHomeFragment onSupportVisible");

    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        // 当对用户不可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！
        LogUtil.i("SafeHomeFragment onSupportInvisible");

    }

    @Override
    public void refreshList() {

    }

    @Override
    public void onRinging(String s) {

    }

    @Override
    public void onUnLock() {

    }

    @Override
    public void onDeviceChanged(boolean b, boolean b1, boolean b2) {

    }

    @Override
    public void onBaseButtonClick(String s) {

    }
}
