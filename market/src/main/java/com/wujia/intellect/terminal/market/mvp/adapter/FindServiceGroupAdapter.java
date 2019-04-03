package com.wujia.intellect.terminal.market.mvp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wujia.intellect.terminal.market.R;
import com.wujia.intellect.terminal.market.mvp.data.ServiceBean;
import com.wujia.lib_common.base.baseadapter.CommonAdapter;
import com.wujia.lib_common.base.baseadapter.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class FindServiceGroupAdapter extends CommonAdapter<ServiceBean> {

    private ArrayList<ServiceBean.Service> datas;

    public FindServiceGroupAdapter(Context context, List<ServiceBean> datas) {
        super(context, R.layout.item_service_find_group, datas);
    }

    @Override
    protected void convert(final ViewHolder holder, ServiceBean item, int pos) {

        RecyclerView rv = holder.getView(R.id.rv1);

        datas = new ArrayList<>();

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
