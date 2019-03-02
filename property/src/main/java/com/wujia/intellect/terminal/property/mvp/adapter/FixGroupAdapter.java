package com.wujia.intellect.terminal.property.mvp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.wujia.intellect.terminal.property.R;
import com.wujia.intellect.terminal.property.mvp.data.WyFixBean;
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
public class FixGroupAdapter extends CommonAdapter<WyFixBean> {
    public FixGroupAdapter(Context context, List<WyFixBean> datas) {
        super(context, R.layout.item_fix_group, datas);

    }

    @Override
    protected void convert(ViewHolder holder, WyFixBean item, int position) {

        RecyclerView rv = holder.getView(R.id.rv1);

        final List<WyFixBean> datas = new ArrayList<>();
        datas.add(new WyFixBean());
        datas.add(new WyFixBean());
        datas.add(new WyFixBean());
        datas.add(new WyFixBean());
        datas.add(new WyFixBean());
        datas.add(new WyFixBean());
        datas.add(new WyFixBean());

        rv.addItemDecoration(new GridDecoration(0, 12));
        rv.setAdapter(new FixChildAdapter(mContext, datas));
    }
}
