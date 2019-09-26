package com.jingxi.smartlife.pad.market.mvp.adapter

import android.content.Context

import com.jingxi.smartlife.pad.market.R
import com.jingxi.smartlife.pad.market.mvp.data.TagBean
import com.wujia.lib_common.base.baseadapter.CommonAdapter
import com.wujia.lib_common.base.baseadapter.base.ViewHolder

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
class TagAdapter(context: Context, datas: List<TagBean>) : CommonAdapter<TagBean>(context, R.layout.item_tag, datas) {

    override fun convert(holder: ViewHolder, item: TagBean, pos: Int) {

        //        holder.setText(R.id.scene_in_mode_tv,item.title);

    }
}
