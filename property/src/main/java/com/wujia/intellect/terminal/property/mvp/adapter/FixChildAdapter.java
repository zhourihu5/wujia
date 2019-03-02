package com.wujia.intellect.terminal.property.mvp.adapter;

import android.content.Context;

import com.wujia.intellect.terminal.property.R;
import com.wujia.intellect.terminal.property.mvp.data.VipServiceBean;
import com.wujia.intellect.terminal.property.mvp.data.WyFixBean;
import com.wujia.lib_common.base.baseadapter.CommonAdapter;
import com.wujia.lib_common.base.baseadapter.base.ViewHolder;

import java.util.List;

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class FixChildAdapter extends CommonAdapter<WyFixBean> {
    public FixChildAdapter(Context context, List<WyFixBean> datas) {
        super(context, R.layout.item_home_rec_layout_1, datas);

    }

    @Override
    protected void convert(ViewHolder holder, WyFixBean item, int position) {

    }
}
