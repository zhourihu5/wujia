package com.jingxi.smartlife.pad.family.mvp.adapter

import android.content.Context

import com.jingxi.smartlife.pad.family.R
import com.jingxi.smartlife.pad.family.mvp.data.EquipmentBean
import com.wujia.lib_common.base.baseadapter.CommonAdapter
import com.wujia.lib_common.base.baseadapter.base.ViewHolder

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
class EquipmentAdapter(context: Context, datas: List<EquipmentBean>) : CommonAdapter<EquipmentBean>(context, R.layout.item_equipment_layout, datas) {

    override fun convert(holder: ViewHolder, item: EquipmentBean, pos: Int) {

        holder.setText(R.id.equipment_name, item.title)
        holder.setImageResource(R.id.equipment_img, item.icon)

    }
}
