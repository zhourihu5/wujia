package com.jingxi.smartlife.pad.market.mvp.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.View

import com.jingxi.smartlife.pad.market.R
import com.jingxi.smartlife.pad.market.mvp.data.GoodsBean
import com.wujia.lib_common.base.baseadapter.CommonAdapter
import com.wujia.lib_common.base.baseadapter.base.ViewHolder

import java.util.ArrayList

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
class CarGroupAdapter(context: Context, datas: List<GoodsBean>) : CommonAdapter<GoodsBean>(context, R.layout.item_car_goods_group, datas) {

    override fun convert(holder: ViewHolder, item: GoodsBean, pos: Int) {

        val rv = holder.getView<RecyclerView>(R.id.rv2)

        val datas = ArrayList<GoodsBean>()
        datas.add(GoodsBean())
        datas.add(GoodsBean())
        datas.add(GoodsBean())
        datas.add(GoodsBean())

        if (isLast(pos)) {
            holder.getView<View>(R.id.l4).visibility = View.VISIBLE
        } else {
            holder.getView<View>(R.id.l4).visibility = View.GONE
        }

        val adapter = CarChildAdapter(mContext, datas)
        rv.adapter = adapter

    }

}
