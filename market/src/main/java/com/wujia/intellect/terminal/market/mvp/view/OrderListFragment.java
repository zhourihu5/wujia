package com.wujia.intellect.terminal.market.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wujia.businesslib.TitleFragment;
import com.wujia.intellect.terminal.market.R;
import com.wujia.intellect.terminal.market.mvp.adapter.OrderAdapter;
import com.wujia.intellect.terminal.market.mvp.data.OrderBean;
import com.wujia.lib.widget.HorizontalTabBar;
import com.wujia.lib.widget.HorizontalTabItem;
import com.wujia.lib_common.base.BaseFragment;
import com.wujia.lib_common.base.baseadapter.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: created by shenbingkai on 2019/2/23 14 38
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class OrderListFragment extends TitleFragment implements View.OnClickListener, HorizontalTabBar.OnTabSelectedListener {

    HorizontalTabBar tabBar;
    RecyclerView recyclerView;
    OrderAdapter mAdapter;

    public static OrderListFragment newInstance() {
        OrderListFragment fragment = new OrderListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_market_order;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();

        tabBar = $(R.id.tab_layout);
        recyclerView = $(R.id.rv1);

        tabBar.addItem(new HorizontalTabItem(mContext, R.string.all_order));
        tabBar.addItem(new HorizontalTabItem(mContext, R.string.wait_pay));
        tabBar.addItem(new HorizontalTabItem(mContext, R.string.wait_send));
        tabBar.addItem(new HorizontalTabItem(mContext, R.string.wait_receive));
        tabBar.addItem(new HorizontalTabItem(mContext, R.string.wait_evaluate));

        tabBar.setOnTabSelectedListener(this);

        List<OrderBean> datas = new ArrayList<>();
        datas.add(new OrderBean());
        datas.add(new OrderBean());
        datas.add(new OrderBean());
        datas.add(new OrderBean());
        datas.add(new OrderBean());

        mAdapter = new OrderAdapter(mActivity, datas);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnRVItemClickListener() {
            @Override
            public void onItemClick(@Nullable RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                start(OrderDetailsFragment.newInstance("id"));
            }
        });
    }

    @Override
    public int getTitle() {
        return R.string.family_order;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

    }

    @Override
    public void onTabSelected(int position, int prePosition) {

    }
}
