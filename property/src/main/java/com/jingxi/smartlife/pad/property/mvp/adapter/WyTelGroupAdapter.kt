package com.jingxi.smartlife.pad.property.mvp.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.View

import com.jingxi.smartlife.pad.property.R
import com.jingxi.smartlife.pad.property.mvp.data.WyChildBean
import com.jingxi.smartlife.pad.property.mvp.data.WySectionBean
import com.wujia.lib_common.base.baseadapter.CommonAdapter
import com.wujia.lib_common.base.baseadapter.base.ViewHolder
import com.wujia.lib_common.base.view.GridDecoration

import java.util.ArrayList

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
class WyTelGroupAdapter(context: Context, datas: List<WySectionBean>) : CommonAdapter<WySectionBean>(context, R.layout.item_group_wuye_tel, datas) {

    override fun convert(holder: ViewHolder, item: WySectionBean, pos: Int) {

        val rv = holder.getView<RecyclerView>(R.id.rv2)

        val datas = ArrayList<WyChildBean>()
        datas.add(WyChildBean())
        datas.add(WyChildBean())
        datas.add(WyChildBean())
        datas.add(WyChildBean())
        datas.add(WyChildBean())
        datas.add(WyChildBean())

        rv.addItemDecoration(GridDecoration(0, 24))
        rv.adapter = WyTelChildAdapter(mContext, datas)
        if (!isLast(pos)) {
            holder.getView<View>(R.id.line1).visibility = View.VISIBLE
        } else {
            holder.getView<View>(R.id.line1).visibility = View.INVISIBLE
        }
        holder.setOnClickListener(R.id.l1) {
            val visib = holder.getView<View>(R.id.rv2).visibility == View.VISIBLE
            holder.setVisible(R.id.rv2, !visib)
            if (visib) {
                holder.getView<View>(R.id.img9).animate().rotation(0f)
                if (!isLast(pos)) {
                    holder.getView<View>(R.id.line1).visibility = View.VISIBLE
                }
            } else {
                holder.getView<View>(R.id.img9).animate().rotation(180f)
                if (!isLast(pos)) {
                    holder.getView<View>(R.id.line1).visibility = View.INVISIBLE
                }
            }
        }

    }

}
