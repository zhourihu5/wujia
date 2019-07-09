package com.jingxi.smartlife.pad.market;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.jingxi.smartlife.pad.market.mvp.MarketHomeFragment;
import com.wujia.lib_common.base.BaseMainFragment;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12 20:06
 * description ：服务市场 home
 */
public class MarketFragment extends BaseMainFragment {

    public MarketFragment() {
    }

    public static MarketFragment newInstance() {
        MarketFragment fragment = new MarketFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_frame_layout;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        // 懒加载
        // 同级Fragment场景、ViewPager场景均适用
        Fragment marketHomeFragment=findChildFragment(MarketHomeFragment.class);
        if (marketHomeFragment == null) {
            loadRootFragment(R.id.fl_first_container, MarketHomeFragment.newInstance(currentTab));
        }
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        // 当对用户可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！

    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        // 当对用户不可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！
    }
}
