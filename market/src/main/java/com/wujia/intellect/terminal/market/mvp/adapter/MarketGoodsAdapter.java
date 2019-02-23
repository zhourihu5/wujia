package com.wujia.intellect.terminal.market.mvp.adapter;

import android.content.Context;

import com.wujia.intellect.terminal.market.R;
import com.wujia.intellect.terminal.market.mvp.data.GoodsBean;
import com.wujia.intellect.terminal.market.mvp.data.MarketBean;
import com.wujia.lib_common.base.baseadapter.CommonAdapter;
import com.wujia.lib_common.base.baseadapter.base.ViewHolder;

import java.util.List;

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class MarketGoodsAdapter extends CommonAdapter<GoodsBean> {
    public MarketGoodsAdapter(Context context, List<GoodsBean> datas) {
        super(context, R.layout.item_goods, datas);
    }

    @Override
    protected void convert(final ViewHolder holder, GoodsBean item, int pos) {

//        holder.setText(R.id.scene_in_mode_tv,item.title);

    }
}
