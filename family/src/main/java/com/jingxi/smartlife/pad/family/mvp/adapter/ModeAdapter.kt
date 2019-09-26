package com.jingxi.smartlife.pad.family.mvp.adapter

import android.content.Context

import com.jingxi.smartlife.pad.family.R
import com.jingxi.smartlife.pad.family.mvp.data.ModeBean
import com.wujia.lib_common.base.baseadapter.CommonAdapter
import com.wujia.lib_common.base.baseadapter.base.ViewHolder

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
class ModeAdapter(context: Context, datas: List<ModeBean>) : CommonAdapter<ModeBean>(context, R.layout.item_mode_layout, datas) {

    override fun convert(holder: ViewHolder, item: ModeBean, pos: Int) {

        holder.setText(R.id.scene_in_mode_tv, item.title)
        when (pos) {
            0 -> holder.setImageResource(R.id.scene_in_img, R.mipmap.img_card_backhome_0)
            1 -> holder.setImageResource(R.id.scene_in_img, R.mipmap.img_card_backhome_1)
            2 -> holder.setImageResource(R.id.scene_in_img, R.mipmap.img_card_backhome_2)
        }

    }
}
