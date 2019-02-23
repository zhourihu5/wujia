package com.wujia.intellect.terminal.market.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.wujia.intellect.terminal.market.R;
import com.wujia.intellect.terminal.market.mvp.MarketHomeFragment;
import com.wujia.intellect.terminal.market.mvp.adapter.MarketAdapter;
import com.wujia.intellect.terminal.market.mvp.data.MarketBean;
import com.wujia.lib.widget.HorizontalTabBar;
import com.wujia.lib.widget.HorizontalTabItem;
import com.wujia.lib_common.base.BaseFragment;
import com.wujia.lib_common.base.baseadapter.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
public class AllMarketFragment extends BaseFragment implements HorizontalTabBar.OnTabSelectedListener {

    HorizontalTabBar tabBar;
    RecyclerView recyclerView;
    MarketAdapter mAdapter;

    public AllMarketFragment() {

    }

    public static AllMarketFragment newInstance() {
        AllMarketFragment fragment = new AllMarketFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_market_all;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();

        tabBar = $(R.id.tab_layout);
        recyclerView = $(R.id.rv1);

        tabBar.addItem(new HorizontalTabItem(mContext, R.string.feature_service));
        tabBar.addItem(new HorizontalTabItem(mContext, R.string.around_service));

        tabBar.setOnTabSelectedListener(this);

        List<MarketBean> datas = new ArrayList<>();
        datas.add(new MarketBean());
        datas.add(new MarketBean());
        datas.add(new MarketBean());
        datas.add(new MarketBean());
        datas.add(new MarketBean());

        mAdapter = new MarketAdapter(mActivity, datas);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnRVItemClickListener() {
            @Override
            public void onItemClick(@Nullable RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                parentStart(ShopDetailsFragment.newInstance("id"));
            }
        });
    }

    @Override
    public void onTabSelected(int position, int prePosition) {

    }
}
