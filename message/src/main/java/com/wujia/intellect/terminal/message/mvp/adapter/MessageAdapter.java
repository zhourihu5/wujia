package com.wujia.intellect.terminal.message.mvp.adapter;

import android.content.Context;
import android.view.View;

import com.wujia.businesslib.dialog.CallDialog;
import com.wujia.intellect.terminal.message.R;
import com.wujia.intellect.terminal.message.mvp.data.MsgBean;
import com.wujia.lib_common.base.baseadapter.CommonAdapter;
import com.wujia.lib_common.base.baseadapter.base.ViewHolder;

import java.util.List;

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class MessageAdapter extends CommonAdapter<MsgBean> {
    public MessageAdapter(Context context, List<MsgBean> datas) {
        super(context, R.layout.item_msg, datas);
    }

    @Override
    protected void convert(final ViewHolder holder, MsgBean item, int pos) {

//        holder.setText(R.id.scene_in_mode_tv,item.title);

        holder.setOnClickListener(R.id.l1, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean visib = holder.getView(R.id.l2).getVisibility() == View.VISIBLE;
                holder.setVisible(R.id.l2, !visib);
                if (visib) {
                    holder.setBackgroundRes(R.id.l1, R.drawable.bg_white_radius_left_yellow_shape);
                    holder.getView(R.id.img1).animate().rotation(0);
                } else {
                    holder.setBackgroundRes(R.id.l1, R.drawable.bg_white_radius_left_yellow_shape_msg);
                    holder.getView(R.id.img1).animate().rotation(180);
                }
                holder.setOnClickListener(R.id.btn1, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new CallDialog(mContext).show();
                    }
                });
            }
        });

    }
}
