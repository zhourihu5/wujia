package com.jingxi.smartlife.pad.market.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.jingxi.smartlife.pad.market.mvp.adapter.MyServiceAdapterOld;
import com.jingxi.smartlife.pad.market.mvp.data.ServiceBean;
import com.wujia.businesslib.base.MvpFragment;
import com.jingxi.smartlife.pad.market.R;
import com.jingxi.smartlife.pad.market.mvp.contract.MarketPresenter;
import com.wujia.lib.widget.HorizontalTabBar;
import com.wujia.lib.widget.HorizontalTabItem;

import java.util.ArrayList;
import java.util.List;

/**
 * author ：shenbingkai@163.com
 * date ：2019-02-17
 * description ：
 */
@Deprecated
public class MyServiceFragmentOld extends MvpFragment<MarketPresenter> implements HorizontalTabBar.OnTabSelectedListener {

    private HorizontalTabBar tabBar;
    private RecyclerView recyclerView;
    private MyServiceAdapterOld mAdapter;

    public MyServiceFragmentOld() {

    }

    public static MyServiceFragmentOld newInstance() {
        MyServiceFragmentOld fragment = new MyServiceFragmentOld();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_market_my;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        tabBar = $(R.id.tab_layout);
        recyclerView = $(R.id.rv1);

        tabBar.addItem(new HorizontalTabItem(mContext, R.string.feature_service));
        tabBar.addItem(new HorizontalTabItem(mContext, R.string.around_service));

        tabBar.setOnTabSelectedListener(this);

        List<ServiceBean> datas = new ArrayList<>();
        datas.add(new ServiceBean());
        datas.add(new ServiceBean());
        datas.add(new ServiceBean());
        datas.add(new ServiceBean());
        datas.add(new ServiceBean());

        mAdapter = new MyServiceAdapterOld(mActivity, datas);
        recyclerView.setAdapter(mAdapter);
//        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnRVItemClickListener() {
//            @Override
//            public void onItemClick(@Nullable RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
//                parentStart(ShopDetailsFragment.newInstance("id"));
//            }
//        });
    }

    @Override
    public void onTabSelected(int position, int prePosition) {

    }

    @Override
    protected MarketPresenter createPresenter() {
        return new MarketPresenter();
    }
}
