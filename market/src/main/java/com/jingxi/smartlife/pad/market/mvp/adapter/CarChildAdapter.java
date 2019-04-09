package com.jingxi.smartlife.pad.market.mvp.adapter;

import android.content.Context;

import com.jingxi.smartlife.pad.market.R;
import com.jingxi.smartlife.pad.market.mvp.data.GoodsBean;
import com.wujia.lib_common.base.baseadapter.CommonAdapter;
import com.wujia.lib_common.base.baseadapter.base.ViewHolder;

import java.util.List;

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class CarChildAdapter extends CommonAdapter<GoodsBean> {
    public CarChildAdapter(Context context, List<GoodsBean> datas) {
        super(context, R.layout.item_car_goods_child, datas);
    }

    @Override
    protected void convert(final ViewHolder holder, GoodsBean item, int pos) {

//        holder.setText(R.id.scene_in_mode_tv,item.title);

    }
}
