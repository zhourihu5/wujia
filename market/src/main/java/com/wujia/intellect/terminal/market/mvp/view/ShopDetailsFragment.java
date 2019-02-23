package com.wujia.intellect.terminal.market.mvp.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.wujia.intellect.terminal.market.R;
import com.wujia.intellect.terminal.market.mvp.adapter.MarketAdapter;
import com.wujia.intellect.terminal.market.mvp.adapter.MarketGoodsAdapter;
import com.wujia.intellect.terminal.market.mvp.adapter.TagAdapter;
import com.wujia.intellect.terminal.market.mvp.data.GoodsBean;
import com.wujia.intellect.terminal.market.mvp.data.MarketBean;
import com.wujia.intellect.terminal.market.mvp.data.TagBean;
import com.wujia.lib.imageloader.DensityUtil;
import com.wujia.lib_common.base.BaseFragment;
import com.wujia.lib_common.base.baseadapter.MultiItemTypeAdapter;
import com.wujia.lib_common.base.view.HorizontalDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: created by shenbingkai on 2019/2/23 14 38
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class ShopDetailsFragment extends BaseFragment implements View.OnClickListener {

    private RecyclerView rvTag;
    private RecyclerView rvGoods;
    private MarketGoodsAdapter goodsAdapter;

    public static ShopDetailsFragment newInstance(String id) {
        ShopDetailsFragment fragment = new ShopDetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shop_details;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();

        rvTag = $(R.id.rv1);
        rvGoods = $(R.id.rv2);
        $(R.id.tv1).setOnClickListener(this);
        $(R.id.btn1).setOnClickListener(this);
        $(R.id.btn2).setOnClickListener(this);
        $(R.id.btn3).setOnClickListener(this);

        List<TagBean> tags = new ArrayList<>();
        tags.add(new TagBean());
        tags.add(new TagBean());
        tags.add(new TagBean());

        rvTag.addItemDecoration(new HorizontalDecoration(20));
        rvTag.setAdapter(new TagAdapter(mActivity, tags));


        List<GoodsBean> datas = new ArrayList<>();
        datas.add(new GoodsBean());
        datas.add(new GoodsBean());
        datas.add(new GoodsBean());
        datas.add(new GoodsBean());
        datas.add(new GoodsBean());

        goodsAdapter = new MarketGoodsAdapter(mActivity, datas);
        rvGoods.setAdapter(goodsAdapter);
        goodsAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnRVItemClickListener() {
            @Override
            public void onItemClick(@Nullable RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                GoodsDetailsDialog dialog = new GoodsDetailsDialog(mActivity);

                dialog.show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv1) {
            pop();
        } else if (id == R.id.btn1) {
            //搜索
        } else if (id == R.id.btn2) {
            //购物车
        } else if (id == R.id.btn3) {
            //订单
            start(OrderListFragment.newInstance());
        }
    }
}
