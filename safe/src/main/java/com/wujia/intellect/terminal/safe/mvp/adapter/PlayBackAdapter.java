package com.wujia.intellect.terminal.safe.mvp.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;

import com.jingxi.smartlife.pad.sdk.doorAccess.base.bean.DoorRecordBean;
import com.wujia.intellect.terminal.safe.R;
import com.wujia.lib_common.base.baseadapter.CommonAdapter;
import com.wujia.lib_common.base.baseadapter.base.ViewHolder;

import java.util.HashMap;
import java.util.List;


/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class PlayBackAdapter extends CommonAdapter<DoorRecordBean> {

    private boolean isEdit;
    private SparseBooleanArray checkMap;


    public void setEdit(boolean edit) {
        isEdit = edit;
    }

    public void itemClick(int pos) {
        if (checkMap.get(pos)) {
            checkMap.put(pos, false);
        } else {
            checkMap.put(pos, true);
        }
        notifyItemChanged(pos);
    }

    public void chooseAll() {
        for (int i = 0; i < getItemCount(); i++) {
            checkMap.put(i, true);
        }
        notifyDataSetChanged();
    }

    public PlayBackAdapter(Context context, List<DoorRecordBean> datas) {
        super(context, R.layout.item_safe_play_back, datas);
        checkMap = new SparseBooleanArray();
    }

    @Override
    protected void convert(ViewHolder holder, DoorRecordBean item, int pos) {

        if (isEdit) {
            holder.setVisible(R.id.play_back_video_btn, false);
            holder.setVisible(R.id.play_back_checkbox, true);

            if (checkMap.get(pos)) {
                holder.setBackgroundRes(R.id.play_back_checkbox, R.mipmap.icon_save_checkon);
            } else {
                holder.setBackgroundRes(R.id.play_back_checkbox, R.mipmap.icon_save_checkoff);
            }

        } else {
            holder.setVisible(R.id.play_back_video_btn, true);
            holder.setVisible(R.id.play_back_checkbox, false);
        }
    }
}
