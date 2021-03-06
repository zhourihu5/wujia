package com.jingxi.smartlife.pad.message.mvp.adapter

import android.content.Context
import android.view.View

import com.jingxi.smartlife.pad.message.R
import com.wujia.businesslib.data.MsgDto
import com.wujia.lib_common.base.baseadapter.CommonAdapter
import com.wujia.lib_common.base.baseadapter.base.ViewHolder

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
class MessageAdapter(context: Context, datas: List<MsgDto.ContentBean>, private var readMsgCallback: ReadMsgCallback?) : CommonAdapter<MsgDto.ContentBean>(context, R.layout.item_msg, datas) {
    interface ReadMsgCallback {
        fun onMsgReadClick(item: MsgDto.ContentBean)
    }

    override fun convert(holder: ViewHolder, item: MsgDto.ContentBean, pos: Int) {

        //        holder.setText(R.id.tv1, item.getTitle()+ item.getId());
        holder.setText(R.id.tv1, item.title)
        //        holder.setText(R.id.tv2, DateUtil.formatMsgDate(item.createDate));
        holder.setText(R.id.tv2, item.createDate)
        holder.setText(R.id.tv3, item.title)
        holder.setText(R.id.tv4, item.createDate)
        holder.setText(R.id.tv5, item.content)

        var res = 0
        holder.setImageResource(R.id.img2, res)
        if (item.type == MsgDto.TYPE_NOTIFY) {//社区
            //            res = R.mipmap.ic_msg_label_neighbour;
            res = R.mipmap.ic_msg_label_neighbour2
        } else if (item.type == MsgDto.TYPE_PROPERTY) {//物业
            //            res = R.mipmap.ic_msg_label_serve;
            res = R.mipmap.ic_msg_label_serve2
        } else if (item.type == MsgDto.TYPE_SYSTEM) {//
            res = R.mipmap.icon_msg_label_system
        }

        holder.setVisible(R.id.img3, item.isRead == MsgDto.STATUS_UNREAD)

        if (item.isRead == MsgDto.STATUS_UNREAD) {//未读
            holder.setAlpha(R.id.l1, 1f)
            holder.setBackgroundRes(R.id.btn2, R.drawable.btn_rect_accent_select)
        } else {
            holder.setAlpha(R.id.l1, 0.5f)
            holder.setBackgroundRes(R.id.btn2, R.drawable.btn_rect_no_can)
        }

        holder.setBackgroundRes(R.id.l1, R.drawable.bg_white_radius_left_yellow_shape)
        holder.setImageResource(R.id.img1, R.mipmap.btn_more_pressed)
        holder.setVisible(R.id.l2, false)//todo test,修改为有状态的？？


        holder.setOnClickListener(R.id.l1, View.OnClickListener {
            val visib = holder.getView<View>(R.id.l2).visibility == View.VISIBLE
            holder.setVisible(R.id.l2, !visib)
            if (visib) {
                holder.setBackgroundRes(R.id.l1, R.drawable.bg_white_radius_left_yellow_shape)
                holder.setImageResource(R.id.img1, R.mipmap.btn_more_pressed)
            } else {
                holder.setBackgroundRes(R.id.l1, R.drawable.bg_white_radius_left_yellow_shape_msg)
                holder.setImageResource(R.id.img1, R.mipmap.btn_more_normal)
            }

            //呼叫物业
            //                holder.setOnClickListener(R.id.btn1, new View.OnClickListener() {
            //                    @Override
            //                    public void onClick(View v) {
            //                        new CallDialog(mContext).show();
            //                    }
            //                });

            holder.setOnClickListener(R.id.btn2, View.OnClickListener {
                if (item.isRead != MsgDto.STATUS_UNREAD) {
                    return@OnClickListener
                }
                holder.setAlpha(R.id.l1, 0.5f)
                holder.setBackgroundRes(R.id.btn2, R.drawable.btn_rect_no_can)
                holder.setVisible(R.id.img3, false)
                if (readMsgCallback != null) {
                    readMsgCallback!!.onMsgReadClick(item)
                }
            })
        })

    }
}
