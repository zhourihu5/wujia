package com.jingxi.smartlife.pad.mvp.home.adapter;

import android.content.Context;
import android.widget.TextView;

import com.wujia.businesslib.data.DBMessage;
import com.jingxi.smartlife.pad.R;
import com.wujia.businesslib.data.MsgDto;
import com.wujia.lib_common.base.baseadapter.CommonAdapter;
import com.wujia.lib_common.base.baseadapter.base.ViewHolder;
import com.wujia.lib_common.utils.DateUtil;

import java.util.List;

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class HomeNotifyAdapter extends CommonAdapter<MsgDto.ContentBean> {
    public HomeNotifyAdapter(Context context, List<MsgDto.ContentBean> datas) {
        super(context, R.layout.item_home_notify_layout, datas);
    }

    @Override
    protected void convert(ViewHolder holder, MsgDto.ContentBean item, int pos) {

//        holder.setText(R.id.scene_in_mode_tv,item.title);

        TextView titleTv = holder.getView(R.id.home_notify_item_title);
//        holder.setText(R.id.home_notify_item_time, DateUtil.formatMsgDate(item.createDate));
        holder.setText(R.id.home_notify_item_time, item.getCreateDate());
        holder.setText(R.id.home_notify_item_info, item.getContent());

        holder.setVisible(R.id.home_notify_item_point, item.getIsRead()==MsgDto.STATUS_UNREAD);

        titleTv.setText(item.getTitle());
        int res = 0;
        if (item.getType().equals(MsgDto.TYPE_NOTIFY)) {
//            res = R.mipmap.ic_msg_label_neighbour;
            res = R.mipmap.ic_msg_label_neighbour2;
        } else if (item.getType().equals(MsgDto.TYPE_PROPERTY)) {
//            res = R.mipmap.ic_msg_label_serve;
            res = R.mipmap.ic_msg_label_serve2;
        } else if (item.getType().equals(MsgDto.TYPE_SYSTEM)) {
            res = R.mipmap.icon_msg_label_system;
        }
        titleTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, res, 0);
    }
}
