package com.jingxi.smartlife.pad.market.mvp.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.jingxi.smartlife.pad.market.R;
import com.jingxi.smartlife.pad.market.mvp.data.GoodsBean;
import com.wujia.lib_common.base.baseadapter.CommonAdapter;
import com.wujia.lib_common.base.baseadapter.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class CarGroupAdapter extends CommonAdapter<GoodsBean> {

    public CarGroupAdapter(Context context, List<GoodsBean> datas) {
        super(context, R.layout.item_car_goods_group, datas);
    }

    @Override
    protected void convert(final ViewHolder holder, GoodsBean item, int pos) {

        RecyclerView rv = holder.getView(R.id.rv2);

        final List<GoodsBean> datas = new ArrayList<>();
        datas.add(new GoodsBean());
        datas.add(new GoodsBean());
        datas.add(new GoodsBean());
        datas.add(new GoodsBean());

        if (isLast(pos)) {
            holder.getView(R.id.l4).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.l4).setVisibility(View.GONE);
        }

        CarChildAdapter adapter = new CarChildAdapter(mContext, datas);
        rv.setAdapter(adapter);

    }

}
