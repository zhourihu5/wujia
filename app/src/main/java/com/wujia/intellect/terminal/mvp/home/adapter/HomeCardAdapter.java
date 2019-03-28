package com.wujia.intellect.terminal.mvp.home.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.wujia.intellect.terminal.R;
import com.wujia.intellect.terminal.mvp.home.data.HomeRecBean;
import com.wujia.intellect.terminal.mvp.home.data.HomeRecBean.Card;
import com.wujia.lib.imageloader.ImageLoaderManager;
import com.wujia.lib.imageloader.ImageLoaderUtils;
import com.wujia.lib_common.base.baseadapter.MultiItemTypeAdapter;
import com.wujia.lib_common.base.baseadapter.base.ItemViewDelegate;
import com.wujia.lib_common.base.baseadapter.base.ViewHolder;

import java.util.List;

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class HomeCardAdapter extends MultiItemTypeAdapter<HomeRecBean.Card> {
    public HomeCardAdapter(Context context, List<HomeRecBean.Card> datas) {
        super(context, datas);


//        addItemViewDelegate(0, new ItemViewDelegate<HomeRecBean.Card>() {
//            @Override
//            public int getItemViewLayoutId() {
//                return R.layout.item_home_rec_layout_0_shadow;
//            }
//
//            @Override
//            public boolean isForViewType(HomeRecBean.Card item, int position) {
//                return item._viewType == 0;
//            }
//
//            @Override
//            public void convert(ViewHolder viewHolder, HomeRecBean.Card data, int i) {
//
//            }
//        });
        addItemViewDelegate(1, new ItemViewDelegate<HomeRecBean.Card>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_home_rec_layout_1_shadow;
            }

            @Override
            public boolean isForViewType(HomeRecBean.Card item, int position) {
                return item.type.equals(HomeRecBean.TYPE_LINK) || item.type.equals(HomeRecBean.TYPE_FUN);
            }

            @Override
            public void convert(ViewHolder holder, HomeRecBean.Card item, int pos) {
                ImageView img = holder.getView(R.id.scene_in_img);
                ImageLoaderManager.getInstance().loadImage(item.image, img);
                holder.setText(R.id.scene_in_mode_tv, item.title);
                holder.setText(R.id.scene_in_mode_status_tv, item.explain);

            }
        });

        addItemViewDelegate(2, new ItemViewDelegate<HomeRecBean.Card>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_home_rec_layout_2_shadow;
            }

            @Override
            public boolean isForViewType(HomeRecBean.Card item, int position) {
                return item.type.equals(HomeRecBean.TYPE_IMAGE);
            }

            @Override
            public void convert(ViewHolder holder, HomeRecBean.Card item, int pos) {
                ImageView img = holder.getView(R.id.scene_in_img);
                ImageLoaderManager.getInstance().loadImage(item.headImage, img);
            }
        });
        addItemViewDelegate(10, new ItemViewDelegate<HomeRecBean.Card>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.item_home_rec_layout_add;
            }

            @Override
            public boolean isForViewType(HomeRecBean.Card item, int position) {
                return item.type.equals(HomeRecBean.TYPE_ADD);
            }

            @Override
            public void convert(ViewHolder viewHolder, HomeRecBean.Card data, int i) {
            }
        });
    }
}
