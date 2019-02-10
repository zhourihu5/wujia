package com.wujia.intellect.terminal.mvp.home.adapter;

import android.content.Context;

import com.wujia.intellect.terminal.R;
import com.wujia.intellect.terminal.mvp.home.data.HomeMeberBean;
import com.wujia.intellect.terminal.mvp.home.data.HomeRecBean;
import com.wujia.lib_common.base.baseadapter.CommonAdapter;
import com.wujia.lib_common.base.baseadapter.MultiItemTypeAdapter;
import com.wujia.lib_common.base.baseadapter.base.ItemViewDelegate;
import com.wujia.lib_common.base.baseadapter.base.ViewHolder;

import java.util.List;

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class HomeRecAdapter extends MultiItemTypeAdapter<HomeRecBean> {
    public HomeRecAdapter(Context context, List<HomeRecBean> datas) {
        super(context, datas);


        addItemViewDelegate(0, new ItemViewDelegate<HomeRecBean>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_home_rec_layout_0;
            }

            @Override
            public boolean isForViewType(HomeRecBean item, int position) {
                return item.type==0;
            }

            @Override
            public void convert(ViewHolder viewHolder, HomeRecBean data, int i) {

            }
        });
        addItemViewDelegate(1, new ItemViewDelegate<HomeRecBean>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_home_rec_layout_1;
            }

            @Override
            public boolean isForViewType(HomeRecBean item, int position) {
                return item.type==1;
            }

            @Override
            public void convert(ViewHolder viewHolder, HomeRecBean data, int i) {

            }
        });

        addItemViewDelegate(2, new ItemViewDelegate<HomeRecBean>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_home_rec_layout_2;
            }

            @Override
            public boolean isForViewType(HomeRecBean item, int position) {
                return item.type==2;
            }

            @Override
            public void convert(ViewHolder viewHolder, HomeRecBean data, int i) {

            }
        });
    }
}
