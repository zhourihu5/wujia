package com.jingxi.smartlife.pad.market.mvp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jingxi.smartlife.pad.market.R;
import com.wujia.businesslib.data.CardDetailBean;
import com.wujia.lib_common.base.baseadapter.CommonAdapter;
import com.wujia.lib_common.base.baseadapter.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class FindServiceGroupAdapter extends CommonAdapter<CardDetailBean.ServicesBean> {

    private ArrayList<CardDetailBean.ServicesBean> datas;

    public FindServiceGroupAdapter(Context context, List<CardDetailBean.ServicesBean> datas) {
        super(context, R.layout.item_service_find_group, datas);
    }

    @Override
    protected void convert(final ViewHolder holder, CardDetailBean.ServicesBean item, int pos) {

        RecyclerView rv = holder.getView(R.id.rv1);

        datas = new ArrayList<>();

//        rv.addItemDecoration(new GridDecoration(0, 12));
        FindServiceChildAdapter adapter = new FindServiceChildAdapter(mContext, datas, new FindServiceChildAdapter.SubsribeClickCallback() {
            @Override
            public void subscibe(CardDetailBean.ServicesBean item) {

            }

            @Override
            public void unsubscibe(CardDetailBean.ServicesBean item, int pos) {

            }
        });
        rv.setAdapter(adapter);

        holder.setOnClickListener(R.id.tv2, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
