package com.wujia.intellect.terminal.message.mvp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wujia.businesslib.dialog.CallDialog;
import com.wujia.intellect.terminal.property.R;
import com.wujia.intellect.terminal.property.mvp.adapter.WyChildAdapter;
import com.wujia.intellect.terminal.property.mvp.data.WyChildBean;
import com.wujia.intellect.terminal.property.mvp.data.WySectionBean;
import com.wujia.lib_common.base.baseadapter.CommonAdapter;
import com.wujia.lib_common.base.baseadapter.base.ViewHolder;
import com.wujia.lib_common.base.view.GridDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class WySectionAdapter extends CommonAdapter<WySectionBean> {
    public WySectionAdapter(Context context, List<WySectionBean> datas) {
        super(context, R.layout.item_group_wuye_tel, datas);
    }

    @Override
    protected void convert(final ViewHolder holder, WySectionBean item, final int pos) {

        RecyclerView rv = holder.getView(R.id.rv2);

        final List<WyChildBean> datas = new ArrayList<>();
        datas.add(new WyChildBean());
        datas.add(new WyChildBean());
        datas.add(new WyChildBean());
        datas.add(new WyChildBean());
        datas.add(new WyChildBean());
        datas.add(new WyChildBean());

        rv.addItemDecoration(new GridDecoration(0, 24));
        rv.setAdapter(new WyChildAdapter(mContext, datas));
        if (!isLast(pos)){
            holder.getView(R.id.line1).setVisibility(View.VISIBLE);
        }else{
            holder.getView(R.id.line1).setVisibility(View.INVISIBLE);
        }
        holder.setOnClickListener(R.id.l1, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean visib = holder.getView(R.id.rv2).getVisibility() == View.VISIBLE;
                holder.setVisible(R.id.rv2, !visib);
                if (visib) {
                    holder.getView(R.id.img9).animate().rotation(0);
                    if (!isLast(pos)){
                        holder.getView(R.id.line1).setVisibility(View.VISIBLE);
                    }
                } else {
                    holder.getView(R.id.img9).animate().rotation(180);
                    if (!isLast(pos)) {
                        holder.getView(R.id.line1).setVisibility(View.INVISIBLE);
                    }                }
            }
        });

    }

    private boolean isLast(int pos) {
        return pos == mDatas.size() - 1;
    }
}
