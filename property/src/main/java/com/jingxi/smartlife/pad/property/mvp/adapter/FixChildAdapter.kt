package com.jingxi.smartlife.pad.property.mvp.adapter

import android.content.Context

import com.jingxi.smartlife.pad.property.R
import com.jingxi.smartlife.pad.property.mvp.data.WyFixBean
import com.wujia.lib_common.base.baseadapter.CommonAdapter
import com.wujia.lib_common.base.baseadapter.base.ViewHolder

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
class FixChildAdapter(context: Context, datas: List<WyFixBean>) : CommonAdapter<WyFixBean>(context, R.layout.item_home_rec_layout_1_shadow, datas) {

    override fun convert(holder: ViewHolder, item: WyFixBean, position: Int) {

    }
}
