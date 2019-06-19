package com.jingxi.smartlife.pad.message.mvp.adapter;

import android.content.Context;
import android.view.View;

import com.wujia.businesslib.DataBaseUtil;
import com.wujia.businesslib.data.DBMessage;
import com.jingxi.smartlife.pad.message.R;
import com.wujia.businesslib.data.MsgDto;
import com.wujia.businesslib.event.EventBusUtil;
import com.wujia.businesslib.event.EventMsg;
import com.wujia.lib_common.base.baseadapter.CommonAdapter;
import com.wujia.lib_common.base.baseadapter.base.ViewHolder;
import com.wujia.lib_common.utils.DateUtil;

import java.util.List;

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class MessageAdapter extends CommonAdapter<MsgDto.ContentBean> {
    public  interface ReadMsgCallback{
       void onMsgReadClick(MsgDto.ContentBean item);
    }
    ReadMsgCallback readMsgCallback;
    public MessageAdapter(Context context, List<MsgDto.ContentBean> datas,ReadMsgCallback readMsgCallback) {
        super(context, R.layout.item_msg, datas);
        this.readMsgCallback=readMsgCallback;
    }

    @Override
    protected void convert(final ViewHolder holder, final MsgDto.ContentBean item, final int pos) {

        holder.setText(R.id.tv1, item.getTitle()+ item.getId());
//        holder.setText(R.id.tv2, DateUtil.formatMsgDate(item.createDate));
        holder.setText(R.id.tv2, item.getCreateDate());
        holder.setText(R.id.tv3, item.getTitle());
        holder.setText(R.id.tv4, item.getCreateDate());
        holder.setText(R.id.tv5, item.getContent());

        int res = 0;
        if (item.getType().equals(MsgDto.TYPE_NOTIFY)) {//社区
            res = R.mipmap.ic_msg_label_neighbour;
        } else if (item.getType().equals(MsgDto.TYPE_PROPERTY)) {//物业
            res = R.mipmap.ic_msg_label_serve;
        }
        holder.setImageResource(R.id.img2, res);

        holder.setVisible(R.id.img3, item.getStatus().equals(MsgDto.STATUS_UNREAD));

        if (item.getStatus().equals(MsgDto.STATUS_UNREAD)) {//未读
            holder.setAlpha(R.id.l1, 1f);
            holder.setBackgroundRes(R.id.btn2, R.drawable.btn_rect_accent_select);
        } else {
            holder.setAlpha(R.id.l1, 0.5f);
            holder.setBackgroundRes(R.id.btn2, R.drawable.btn_rect_no_can);
        }

        holder.setBackgroundRes(R.id.l1, R.drawable.bg_white_radius_left_yellow_shape);
        holder.setImageResource(R.id.img1, R.mipmap.btn_more_pressed);
        holder.setVisible(R.id.l2, false);//todo test


        holder.setOnClickListener(R.id.l1, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean visib = holder.getView(R.id.l2).getVisibility() == View.VISIBLE;
                holder.setVisible(R.id.l2, !visib);
                if (visib) {
                    holder.setBackgroundRes(R.id.l1, R.drawable.bg_white_radius_left_yellow_shape);
                    holder.setImageResource(R.id.img1, R.mipmap.btn_more_pressed);
                } else {
                    holder.setBackgroundRes(R.id.l1, R.drawable.bg_white_radius_left_yellow_shape_msg);
                    holder.setImageResource(R.id.img1, R.mipmap.btn_more_normal);
                }

                //呼叫物业
//                holder.setOnClickListener(R.id.btn1, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        new CallDialog(mContext).show();
//                    }
//                });

                holder.setOnClickListener(R.id.btn2, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!item.getStatus().equals(MsgDto.STATUS_UNREAD)) {
                            return;
                        }
                        holder.setAlpha(R.id.l1, 0.5f);
                        holder.setBackgroundRes(R.id.btn2, R.drawable.btn_rect_no_can);
                        holder.setVisible(R.id.img3, false);
                        if(readMsgCallback!=null){
                            readMsgCallback.onMsgReadClick(item);
                        }
//                        item._read_state = 1;

                    }
                });
            }
        });

    }
}
