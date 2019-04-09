package com.jingxi.smartlife.pad.market.mvp.view;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wujia.businesslib.TitleFragment;
import com.jingxi.smartlife.pad.market.R;
import com.jingxi.smartlife.pad.market.mvp.adapter.OrderGoodsAdapter;
import com.jingxi.smartlife.pad.market.mvp.data.OrderBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: created by shenbingkai on 2019/2/23 14 38
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class OrderDetailsFragment extends TitleFragment implements PayTypeDialog.PayListener, View.OnClickListener {

    private RecyclerView rv;
    private OrderGoodsAdapter mAdapter;

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

        rv = $(R.id.rv1);
        $(R.id.btn3).setOnClickListener(this);

        List<OrderBean> datas = new ArrayList<>();
        datas.add(new OrderBean());
        datas.add(new OrderBean());
        datas.add(new OrderBean());
        datas.add(new OrderBean());
        datas.add(new OrderBean());

        mAdapter = new OrderGoodsAdapter(mActivity, datas);
        rv.setAdapter(mAdapter);

//        删除订单dialog
//        new SimpleDialog.Builder().title(getString(R.string.delete_order)).message(getString(R.string.order_delete_desc)).listener(new OnDialogListener() {
//            @Override
//            public void dialogSureClick() {
//                ToastUtil.showShort(mContext, getString(R.string.order_delete_ed));
//            }
//        }).build(mContext).show();


//        支付方式dialog
//        PayTypeDialog payTypeDialog = new PayTypeDialog(mContext);
//        payTypeDialog.setListener(this);
//        payTypeDialog.show();

        //二维码dialog
//        new PayQRcodeDialog(mContext).show();
    }

    @Override
    public int getTitle() {
        return R.string.family_order;
    }

    @Override
    public void choosePayType(int type) {

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn3) {
            //支付方式dialog
            PayTypeDialog payTypeDialog = new PayTypeDialog(mContext);
            payTypeDialog.setListener(this);
            payTypeDialog.show();
        }
    }
}
