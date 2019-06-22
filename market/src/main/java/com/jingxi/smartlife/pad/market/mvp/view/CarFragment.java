package com.jingxi.smartlife.pad.market.mvp.view;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.jingxi.smartlife.pad.market.R;
import com.jingxi.smartlife.pad.market.mvp.adapter.CarGroupAdapter;
import com.jingxi.smartlife.pad.market.mvp.data.GoodsBean;
import com.wujia.businesslib.TitleFragment;

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
