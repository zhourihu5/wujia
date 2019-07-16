package com.jingxi.smartlife.pad.property.mvp.adapter

import android.content.Context
import android.view.View

import com.jingxi.smartlife.pad.property.R
import com.jingxi.smartlife.pad.property.mvp.data.WyChildBean
import com.wujia.lib_common.base.baseadapter.CommonAdapter
import com.wujia.lib_common.base.baseadapter.base.ViewHolder

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
class WyOtherTelAdapter(context: Context, datas: List<WyChildBean>) : CommonAdapter<WyChildBean>(context, R.layout.item_propery_other_tel, datas) {

    override fun convert(holder: ViewHolder, item: WyChildBean, pos: Int) {

        //        holder.setText(R.id.scene_in_mode_tv,item.title);

        if (!isLast(pos)) {
            holder.getView<View>(R.id.line1).visibility = View.VISIBLE
        } else {
            holder.getView<View>(R.id.line1).visibility = View.INVISIBLE
        }
    }

}
