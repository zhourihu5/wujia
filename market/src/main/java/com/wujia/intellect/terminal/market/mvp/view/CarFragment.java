package com.wujia.intellect.terminal.market.mvp.view;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.wujia.businesslib.TitleFragment;
import com.wujia.intellect.terminal.market.R;
import com.wujia.intellect.terminal.market.mvp.adapter.CarGroupAdapter;
import com.wujia.intellect.terminal.market.mvp.adapter.OrderGoodsAdapter;
import com.wujia.intellect.terminal.market.mvp.data.GoodsBean;
import com.wujia.intellect.terminal.market.mvp.data.OrderBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: created by shenbingkai on 2019/2/24 16 05
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class CarFragment extends TitleFragment {

    private RecyclerView rv;
    private CarGroupAdapter mAdapter;

    public static CarFragment newInstance() {
        CarFragment fragment = new CarFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_car;
    }

    @Override
    public int getTitle() {
        return R.string.buy_car;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();

        rv = $(R.id.rv1);

        List<GoodsBean> datas = new ArrayList<>();
        datas.add(new GoodsBean());
        datas.add(new GoodsBean());

        mAdapter = new CarGroupAdapter(mActivity, datas);
        rv.setAdapter(mAdapter);
    }
}
