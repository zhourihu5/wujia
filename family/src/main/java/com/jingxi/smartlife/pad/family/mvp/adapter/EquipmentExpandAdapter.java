package com.jingxi.smartlife.pad.family.mvp.adapter;

import android.content.Context;

import com.jingxi.smartlife.pad.family.R;
import com.jingxi.smartlife.pad.family.mvp.data.EquipmentBean;
import com.wujia.lib_common.base.baseadapter.CommonAdapter;
import com.wujia.lib_common.base.baseadapter.base.ViewHolder;

import java.util.List;

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class EquipmentExpandAdapter extends CommonAdapter<EquipmentBean.Menu> {
    public EquipmentExpandAdapter(Context context, List<EquipmentBean.Menu> datas) {
        super(context, R.layout.item_equipment_expand_layout, datas);
    }

    @Override
    protected void convert(ViewHolder holder, EquipmentBean.Menu item, int pos) {

        holder.setText(R.id.equipment_name, item.title);
        holder.setImageResource(R.id.equipment_img, item.icon);

    }
}
