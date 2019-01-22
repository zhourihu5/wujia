package com.wujia.intellect.terminal.family.mvp.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.wujia.intellect.terminal.family.R;
import com.wujia.intellect.terminal.family.mvp.data.EquipmentBean;
import com.wujia.lib_common.base.baseadapter.CommonAdapter;
import com.wujia.lib_common.base.baseadapter.base.ViewHolder;

import java.util.List;

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class EquipmentAdapter extends CommonAdapter<EquipmentBean> {
    public EquipmentAdapter(Context context, List<EquipmentBean> datas) {
        super(context, R.layout.item_equipment_layout, datas);
    }

    @Override
    protected void convert(ViewHolder holder, EquipmentBean book, int pos) {


    }
}
