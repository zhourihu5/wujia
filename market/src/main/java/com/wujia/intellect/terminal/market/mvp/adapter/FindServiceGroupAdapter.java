package com.wujia.intellect.terminal.market.mvp.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wujia.intellect.terminal.market.R;
import com.wujia.intellect.terminal.market.mvp.data.MarketBean;
import com.wujia.intellect.terminal.market.mvp.view.ShopDetailsFragment;
import com.wujia.lib_common.base.baseadapter.CommonAdapter;
import com.wujia.lib_common.base.baseadapter.MultiItemTypeAdapter;
import com.wujia.lib_common.base.baseadapter.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class FindServiceGroupAdapter extends CommonAdapter<MarketBean> {

    public FindServiceGroupAdapter(Context context, List<MarketBean> datas) {
        super(context, R.layout.item_service_find_group, datas);
    }

    @Override
    protected void convert(final ViewHolder holder, MarketBean item, int pos) {

        RecyclerView rv = holder.getView(R.id.rv1);

        final List<MarketBean> datas = new ArrayList<>();
        datas.add(new MarketBean());
        datas.add(new MarketBean());
        datas.add(new MarketBean());
        datas.add(new MarketBean());

//        rv.addItemDecoration(new GridDecoration(0, 12));
        FindServiceChildAdapter adapter = new FindServiceChildAdapter(mContext, datas);
        rv.setAdapter(adapter);

        holder.setOnClickListener(R.id.tv2, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
