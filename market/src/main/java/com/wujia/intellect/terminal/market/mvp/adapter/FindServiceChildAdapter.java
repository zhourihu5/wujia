package com.wujia.intellect.terminal.market.mvp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.wujia.intellect.terminal.market.R;
import com.wujia.intellect.terminal.market.mvp.data.ServiceBean;
import com.wujia.lib.imageloader.ImageLoaderManager;
import com.wujia.lib.widget.util.ToastUtil;
import com.wujia.lib_common.base.baseadapter.CommonAdapter;
import com.wujia.lib_common.base.baseadapter.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class FindServiceChildAdapter extends CommonAdapter<ServiceBean.Service> {
    public FindServiceChildAdapter(Context context, ArrayList<ServiceBean.Service> datas) {
        super(context, R.layout.item_service_find_child, datas);
    }

    @Override
    protected void convert(final ViewHolder holder, final ServiceBean.Service item, int pos) {

        ImageView img = holder.getView(R.id.img1);
        ImageLoaderManager.getInstance().loadImage(item.image, img);

        holder.setText(R.id.tv1, item.name);
        holder.setText(R.id.tv2, item.explain);

        holder.setOnClickListener(R.id.btn1, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showShort(mContext,"订阅");
            }
        });
    }
}
