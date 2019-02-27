package com.wujia.intellect.terminal.market.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.wujia.intellect.terminal.market.R;
import com.wujia.intellect.terminal.market.mvp.adapter.MyServiceAdapter;
import com.wujia.intellect.terminal.market.mvp.adapter.NoMyServiceAdapter;
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
public class FindServiceFragment extends BaseFragment implements HorizontalTabBar.OnTabSelectedListener {

    RecyclerView recyclerView;
    NoMyServiceAdapter mAdapter;

    public FindServiceFragment() {

    }

    public static FindServiceFragment newInstance() {
        FindServiceFragment fragment = new FindServiceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_service_find;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();

        recyclerView = $(R.id.rv1);

        List<MarketBean> datas = new ArrayList<>();
        datas.add(new MarketBean());
        datas.add(new MarketBean());
        datas.add(new MarketBean());
        datas.add(new MarketBean());
        datas.add(new MarketBean());

        mAdapter = new NoMyServiceAdapter(mActivity, datas);
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
