package com.jingxi.smartlife.pad.property.mvp.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.jingxi.smartlife.pad.property.R
import com.jingxi.smartlife.pad.property.mvp.data.WyFixBean
import com.wujia.lib_common.base.baseadapter.CommonAdapter
import com.wujia.lib_common.base.baseadapter.base.ViewHolder
import java.util.*

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
class FixGroupAdapter(context: Context, datas: List<WyFixBean>) : CommonAdapter<WyFixBean>(context, R.layout.item_fix_group, datas) {

    override fun convert(holder: ViewHolder, item: WyFixBean, position: Int) {

        val rv = holder.getView<RecyclerView>(R.id.rv1)

        val datas = ArrayList<WyFixBean>()
        datas.add(WyFixBean())
        datas.add(WyFixBean())
        datas.add(WyFixBean())
        datas.add(WyFixBean())
        datas.add(WyFixBean())
        datas.add(WyFixBean())
        datas.add(WyFixBean())

        //        rv.addItemDecoration(new GridDecoration(0, 12));
        rv.adapter = FixChildAdapter(mContext, datas)
    }
}
