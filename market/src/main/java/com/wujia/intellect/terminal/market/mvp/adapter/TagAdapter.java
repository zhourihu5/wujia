package com.wujia.intellect.terminal.market.mvp.adapter;

import android.content.Context;

import com.wujia.intellect.terminal.market.R;
import com.wujia.intellect.terminal.market.mvp.data.TagBean;
import com.wujia.lib_common.base.baseadapter.CommonAdapter;
import com.wujia.lib_common.base.baseadapter.base.ViewHolder;

import java.util.List;

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class TagAdapter extends CommonAdapter<TagBean> {
    public TagAdapter(Context context, List<TagBean> datas) {
        super(context, R.layout.item_tag, datas);
    }

    @Override
    protected void convert(final ViewHolder holder, TagBean item, int pos) {

//        holder.setText(R.id.scene_in_mode_tv,item.title);

    }
}
