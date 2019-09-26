package com.jingxi.smartlife.pad.mvp.home.adapter

import android.content.Context
import android.widget.TextView

import com.jingxi.smartlife.pad.R
import com.wujia.businesslib.data.MsgDto
import com.wujia.lib_common.base.baseadapter.CommonAdapter
import com.wujia.lib_common.base.baseadapter.base.ViewHolder

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
class HomeNotifyAdapter(context: Context, datas: List<MsgDto.ContentBean>) : CommonAdapter<MsgDto.ContentBean>(context, R.layout.item_home_notify_layout, datas) {

    override fun convert(holder: ViewHolder, item: MsgDto.ContentBean, pos: Int) {


        val titleTv = holder.getView<TextView>(R.id.home_notify_item_title)
        //        holder.setText(R.id.home_notify_item_time, DateUtil.formatMsgDate(item.createDate));
        holder.setText(R.id.home_notify_item_time, item.createDate)
        holder.setText(R.id.home_notify_item_info, item.content)

        holder.setVisible(R.id.home_notify_item_point, item.isRead == MsgDto.STATUS_UNREAD)

        titleTv.text = item.title
        var res = 0
        when {
            item.type == MsgDto.TYPE_NOTIFY -> res = R.mipmap.ic_msg_label_neighbour2
            item.type == MsgDto.TYPE_PROPERTY -> res = R.mipmap.ic_msg_label_serve2
            item.type == MsgDto.TYPE_SYSTEM -> res = R.mipmap.icon_msg_label_system
        }
        titleTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, res, 0)
    }
}
