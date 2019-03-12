package com.wujia.intellect.terminal.market.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wujia.businesslib.Constants;
import com.wujia.intellect.terminal.market.R;
import com.wujia.intellect.terminal.market.mvp.adapter.FindServiceChildAdapter;
import com.wujia.intellect.terminal.market.mvp.adapter.FindServiceGroupAdapter;
import com.wujia.intellect.terminal.market.mvp.data.MarketBean;
import com.wujia.lib.widget.HorizontalTabBar;
import com.wujia.lib_common.base.BaseFragment;
import com.wujia.lib_common.base.baseadapter.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
public class AllServiceFragment extends BaseFragment implements HorizontalTabBar.OnTabSelectedListener {

    RecyclerView recyclerView;
    FindServiceChildAdapter mAdapter;

    public AllServiceFragment() {

    }

    public static AllServiceFragment newInstance(String id) {
        AllServiceFragment fragment = new AllServiceFragment();
        Bundle args = new Bundle();
        args.putString(Constants.ARG_PARAM_1, id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_service_all;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        recyclerView = $(R.id.rv1);

        List<MarketBean> datas = new ArrayList<>();
        datas.add(new MarketBean());
        datas.add(new MarketBean());
        datas.add(new MarketBean());
        datas.add(new MarketBean());
        datas.add(new MarketBean());
        datas.add(new MarketBean());
        datas.add(new MarketBean());

        mAdapter = new FindServiceChildAdapter(mActivity, datas);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnRVItemClickListener() {
            @Override
            public void onItemClick(@Nullable RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
//                parentStart(ShopDetailsFragment.newInstance("id"));
            }
        });
    }

    @Override
    public void onTabSelected(int position, int prePosition) {

    }
}
