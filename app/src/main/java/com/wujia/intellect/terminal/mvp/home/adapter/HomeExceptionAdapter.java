package com.wujia.intellect.terminal.mvp.home.adapter;

import android.content.Context;

import com.wujia.intellect.terminal.R;
import com.wujia.intellect.terminal.mvp.home.data.HomeExceptionBean;
import com.wujia.intellect.terminal.mvp.home.data.HomeNotifyBean;
import com.wujia.lib_common.base.baseadapter.CommonAdapter;
import com.wujia.lib_common.base.baseadapter.base.ViewHolder;

import java.util.List;

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class HomeExceptionAdapter extends CommonAdapter<HomeExceptionBean> {
    public HomeExceptionAdapter(Context context, List<HomeExceptionBean> datas) {
        super(context, R.layout.item_exception_msg, datas);
    }

    @Override
    protected void convert(ViewHolder holder, HomeExceptionBean item, int pos) {

//        holder.setText(R.id.scene_in_mode_tv,item.title);

    }
}
