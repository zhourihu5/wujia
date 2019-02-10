package com.wujia.intellect.terminal.mvp.home.adapter;

import android.content.Context;

import com.wujia.intellect.terminal.R;
import com.wujia.intellect.terminal.mvp.home.data.HomeMeberBean;
import com.wujia.intellect.terminal.mvp.home.data.HomeNotifyBean;
import com.wujia.lib_common.base.baseadapter.CommonAdapter;
import com.wujia.lib_common.base.baseadapter.base.ViewHolder;

import java.util.List;

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class HomeNotifyAdapter extends CommonAdapter<HomeNotifyBean> {
    public HomeNotifyAdapter(Context context, List<HomeNotifyBean> datas) {
        super(context, R.layout.item_home_notify_layout, datas);
    }

    @Override
    protected void convert(ViewHolder holder, HomeNotifyBean item, int pos) {

//        holder.setText(R.id.scene_in_mode_tv,item.title);

    }
}
