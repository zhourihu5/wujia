package com.wujia.intellect.terminal.mvp.setting.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.wujia.intellect.terminal.R;
import com.wujia.intellect.terminal.mvp.home.data.HomeRecBean;
import com.wujia.lib.imageloader.ImageLoaderManager;
import com.wujia.lib_common.base.baseadapter.CommonAdapter;
import com.wujia.lib_common.base.baseadapter.base.ViewHolder;

import java.util.List;

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class HomeCardManagerAdapter extends CommonAdapter<HomeRecBean.Card> {

    private OnManagerCardListener managerCardListener;

    public static final int FORM_ADDED = 0;
    public static final int FORM_UNADD = 1;
    private int form;

    public HomeCardManagerAdapter(Context context, List<HomeRecBean.Card> datas, int form) {
        super(context, R.layout.item_home_rec_wrapper, datas);
        this.form = form;
    }

    @Override
    protected void convert(ViewHolder holder, HomeRecBean.Card item, final int position) {
        FrameLayout cont = holder.getView(R.id.card_manager_cont);
        View subview = null;

        switch (item.type) {
            case HomeRecBean.TYPE_FUN:
//                subview = LayoutInflater.from(mContext).inflate(R.layout.item_home_rec_layout_0, null);
//                break;
            case HomeRecBean.TYPE_LINK:
                subview = LayoutInflater.from(mContext).inflate(R.layout.item_home_rec_layout_1, null);
                TextView title = subview.findViewById(R.id.scene_in_mode_tv);
                title.setText(item.title);

                TextView desc = subview.findViewById(R.id.scene_in_mode_status_tv);
                desc.setText(item.explain);

                ImageView img = subview.findViewById(R.id.scene_in_img);
                ImageLoaderManager.getInstance().loadImage(item.image, img);

                break;
            case HomeRecBean.TYPE_IMAGE:
                subview = LayoutInflater.from(mContext).inflate(R.layout.item_home_rec_layout_2, null);
                ImageView headImg = subview.findViewById(R.id.scene_in_img);
                ImageLoaderManager.getInstance().loadImage(item.headImage, headImg);
                break;
        }

        cont.removeAllViews();
        cont.addView(subview);

        if (form == FORM_ADDED) {
            holder.setImageResource(R.id.card_manager_btn, R.mipmap.icon_smart_manage_release);
        }
        holder.setOnClickListener(R.id.card_manager_btn, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (form == FORM_ADDED) {
                    if (null != managerCardListener)
                        managerCardListener.removeCard(position);
                } else {
                    if (null != managerCardListener)
                        managerCardListener.addCard(position);
                }
            }
        });
    }

    public void setManagerCardListener(OnManagerCardListener managerCardListener) {
        this.managerCardListener = managerCardListener;
    }

    public interface OnManagerCardListener {
        void addCard(int pos);

        void removeCard(int pos);
    }
}
