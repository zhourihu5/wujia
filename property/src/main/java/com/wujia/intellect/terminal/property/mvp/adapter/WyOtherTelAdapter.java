package com.wujia.intellect.terminal.property.mvp.adapter;

import android.content.Context;
import android.view.View;

import com.wujia.intellect.terminal.property.R;
import com.wujia.intellect.terminal.property.mvp.data.WyChildBean;
import com.wujia.lib_common.base.baseadapter.CommonAdapter;
import com.wujia.lib_common.base.baseadapter.base.ViewHolder;

import java.util.List;

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class WyOtherTelAdapter extends CommonAdapter<WyChildBean> {
    public WyOtherTelAdapter(Context context, List<WyChildBean> datas) {
        super(context, R.layout.item_propery_other_tel, datas);
    }

    @Override
    protected void convert(final ViewHolder holder, WyChildBean item, int pos) {

//        holder.setText(R.id.scene_in_mode_tv,item.title);

        if (!isLast(pos)) {
            holder.getView(R.id.line1).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.line1).setVisibility(View.INVISIBLE);
        }
    }

}
