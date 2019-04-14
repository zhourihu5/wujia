package com.jingxi.smartlife.pad.mvp.home.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.jingxi.smartlife.pad.mvp.home.data.HomeMeberBean;
import com.jingxi.smartlife.pad.R;
import com.jingxi.smartlife.pad.mvp.home.data.HomeMeberBean;
import com.wujia.lib.imageloader.ImageLoaderManager;
import com.wujia.lib_common.base.baseadapter.CommonAdapter;
import com.wujia.lib_common.base.baseadapter.base.ViewHolder;

import java.util.List;

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class HomeInviteAdapter extends CommonAdapter<HomeMeberBean> {
    public HomeInviteAdapter(Context context, List<HomeMeberBean> datas) {
        super(context, R.layout.item_home_invite_layout, datas);
    }

    @Override
    protected void convert(ViewHolder holder, HomeMeberBean item, int pos) {

        holder.setText(R.id.tv1, item.phone);
        ImageView img = holder.getView(com.jingxi.smartlife.pad.market.R.id.img1);
        ImageLoaderManager.getInstance().loadImage(item.head_res, img);
    }
}
