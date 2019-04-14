package com.jingxi.smartlife.pad.mvp.home.adapter;

import android.content.Context;
import android.widget.TextView;

import com.wujia.businesslib.data.DBMessage;
import com.jingxi.smartlife.pad.R;
import com.wujia.lib_common.base.baseadapter.CommonAdapter;
import com.wujia.lib_common.base.baseadapter.base.ViewHolder;
import com.wujia.lib_common.utils.DateUtil;

import java.util.List;

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class HomeNotifyAdapter extends CommonAdapter<DBMessage> {
    public HomeNotifyAdapter(Context context, List<DBMessage> datas) {
        super(context, R.layout.item_home_notify_layout, datas);
    }

    @Override
    protected void convert(ViewHolder holder, DBMessage item, int pos) {

//        holder.setText(R.id.scene_in_mode_tv,item.title);

        TextView titleTv = holder.getView(R.id.home_notify_item_title);
        holder.setText(R.id.home_notify_item_time, DateUtil.formatMsgDate(item.createDate));
        holder.setText(R.id.home_notify_item_info, item.pureText);

        holder.setVisible(R.id.home_notify_item_point, item._read_state == 0);

        titleTv.setText(item.title);
        int res = 0;
        if (item._type.equals(DBMessage.TYPE_NOTIFY)) {
            res = R.mipmap.ic_msg_label_neighbour;
        } else if (item._type.equals(DBMessage.TYPE_PROPERTY)) {
            res = R.mipmap.ic_msg_label_serve;
        }
        titleTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, res, 0);
    }
}
