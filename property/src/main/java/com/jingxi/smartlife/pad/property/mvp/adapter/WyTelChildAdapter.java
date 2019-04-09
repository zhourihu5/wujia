package com.jingxi.smartlife.pad.property.mvp.adapter;

import android.content.Context;

import com.jingxi.smartlife.pad.property.R;
import com.jingxi.smartlife.pad.property.mvp.data.WyChildBean;
import com.wujia.lib_common.base.baseadapter.CommonAdapter;
import com.wujia.lib_common.base.baseadapter.base.ViewHolder;

import java.util.List;

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class WyTelChildAdapter extends CommonAdapter<WyChildBean> {
    public WyTelChildAdapter(Context context, List<WyChildBean> datas) {
        super(context, R.layout.item_child_wuye_tel, datas);
    }

    @Override
    protected void convert(final ViewHolder holder, WyChildBean item, int pos) {

//        holder.setText(R.id.scene_in_mode_tv,item.title);

    }
}
