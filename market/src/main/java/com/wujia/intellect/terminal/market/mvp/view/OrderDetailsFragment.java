package com.wujia.intellect.terminal.market.mvp.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wujia.businesslib.TitleFragment;
import com.wujia.intellect.terminal.market.R;
import com.wujia.intellect.terminal.market.mvp.adapter.MarketGoodsAdapter;
import com.wujia.intellect.terminal.market.mvp.adapter.TagAdapter;
import com.wujia.intellect.terminal.market.mvp.data.GoodsBean;
import com.wujia.intellect.terminal.market.mvp.data.TagBean;
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
public class OrderDetailsFragment extends TitleFragment {

    private RecyclerView rvTag;
    private RecyclerView rvGoods;
    private MarketGoodsAdapter goodsAdapter;

    public static OrderDetailsFragment newInstance(String id) {
        OrderDetailsFragment fragment = new OrderDetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_details;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        showBack();
    }

    @Override
    public int getTitle() {
        return R.string.family_order;
    }
}
