package com.jingxi.smartlife.pad.family.mvp.adapter;

import android.content.Context;

import com.jingxi.smartlife.pad.family.R;
import com.jingxi.smartlife.pad.family.mvp.data.ModeBean;
import com.wujia.lib_common.base.baseadapter.CommonAdapter;
import com.wujia.lib_common.base.baseadapter.base.ViewHolder;

import java.util.List;

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class ModeAdapter extends CommonAdapter<ModeBean> {
    public ModeAdapter(Context context, List<ModeBean> datas) {
        super(context, R.layout.item_mode_layout, datas);
    }

    @Override
    protected void convert(ViewHolder holder, ModeBean item, int pos) {

        holder.setText(R.id.scene_in_mode_tv, item.title);

    }
}
