package com.wujia.intellect.terminal.mvp.home.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.wujia.businesslib.data.AppPackageBean;
import com.wujia.businesslib.listener.ItemClickListener;
import com.wujia.intellect.terminal.market.R;
import com.wujia.intellect.terminal.mvp.home.data.HomeRecBean;
import com.wujia.lib.imageloader.ImageLoaderManager;
import com.wujia.lib_common.base.baseadapter.CommonAdapter;
import com.wujia.lib_common.base.baseadapter.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class HomeSubscibeAdapter extends CommonAdapter<HomeRecBean.Subscriptions> {
    private ArrayList<AppPackageBean> mAppList;
    private ItemClickListener mItemClickListener;


    public void setItemClickListener(ItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public HomeSubscibeAdapter(Context context, List<HomeRecBean.Subscriptions> datas, ArrayList<AppPackageBean> applist) {
        super(context, R.layout.item_service_find_child, datas);
        this.mAppList = applist;
    }

    public void updateAppList(ArrayList<AppPackageBean> applist) {
        this.mAppList.clear();
        this.mAppList.addAll(applist);
    }

    @Override
    protected void convert(final ViewHolder holder, HomeRecBean.Subscriptions item, final int pos) {

        ImageView img = holder.getView(R.id.img1);
        ImageLoaderManager.getInstance().loadImage(item.serviceImage, img);

        holder.setText(R.id.tv1, item.serviceTitle);
        holder.setText(R.id.tv2, item.serviceDesc);

        holder.setText(R.id.btn1, "订阅");

        for (int i = 0; i < mAppList.size(); i++) {
            if (item.servicePackage.equals(mAppList.get(i).name)) {
                holder.setText(R.id.btn1, "打开");
                item._installed = true;
                break;
            }
        }

        holder.setOnClickListener(R.id.btn1, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mItemClickListener)
                    mItemClickListener.onItemClick(pos);
            }
        });
    }
}
