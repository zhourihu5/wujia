package com.jingxi.smartlife.pad.mvp.home.adapter;

import android.content.Context;

import com.jingxi.smartlife.pad.R;
import com.jingxi.smartlife.pad.mvp.home.data.HomeMeberBean;
import com.jingxi.smartlife.pad.mvp.setting.data.FamilyMeberBean;
import com.wujia.lib_common.base.baseadapter.CommonAdapter;
import com.wujia.lib_common.base.baseadapter.base.ViewHolder;

import java.util.List;

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class HomeMemberAdapter extends CommonAdapter<HomeMeberBean> {
    public HomeMemberAdapter(Context context, List<HomeMeberBean> datas) {
        super(context, R.layout.item_home_head, datas);
    }

    @Override
    protected void convert(ViewHolder holder, HomeMeberBean item, int pos) {

//        holder.setText(R.id.scene_in_mode_tv,item.title);

    }

    @Override
    public int getItemCount() {

        return mDatas == null ? 0 : Math.min(4, mDatas.size());
    }

}
