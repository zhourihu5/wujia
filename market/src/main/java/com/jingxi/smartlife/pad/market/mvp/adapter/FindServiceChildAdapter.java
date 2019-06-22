package com.jingxi.smartlife.pad.market.mvp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.jingxi.smartlife.pad.market.R;
import com.wujia.businesslib.data.CardDetailBean;
import com.wujia.businesslib.dialog.SimpleDialog;
import com.wujia.businesslib.listener.OnDialogListener;
import com.wujia.lib.imageloader.ImageLoaderManager;
import com.wujia.lib_common.base.baseadapter.CommonAdapter;
import com.wujia.lib_common.base.baseadapter.base.ViewHolder;

import java.util.List;

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class FindServiceChildAdapter extends CommonAdapter<CardDetailBean.ServicesBean> {

//    private int mType;

    public interface SubsribeClickCallback{
        void subscibe(CardDetailBean.ServicesBean item);

        void unsubscibe(CardDetailBean.ServicesBean item, int pos);
    }
    SubsribeClickCallback subsribeClickCallback;

    public FindServiceChildAdapter(Context context, List<CardDetailBean.ServicesBean> datas, SubsribeClickCallback subsribeClickCallback) {
        super(context, R.layout.item_service_find_child, datas);
//        this.mType = type;
        this.subsribeClickCallback=subsribeClickCallback;
    }
    public FindServiceChildAdapter(Context context, List<CardDetailBean.ServicesBean> datas) {
        super(context, R.layout.item_service_find_child, datas);
    }

    public void setSubsribeClickCallback(SubsribeClickCallback subsribeClickCallback) {
        this.subsribeClickCallback = subsribeClickCallback;
    }

    @Override
    protected void convert(final ViewHolder holder, final CardDetailBean.ServicesBean item, final int pos) {

        ImageView img = holder.getView(R.id.img1);
        ImageLoaderManager.getInstance().loadImage(item.getCover(), img);
        holder.setText(R.id.tv1, item.getTitle());
        holder.setText(R.id.tv2, item.getMemo());

        if(item.getIsSubscribe()==0){
            holder.setVisible(R.id.btn1, true);
            holder.setVisible(R.id.btn2, false);
            holder.setOnClickListener(R.id.btn1, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(subsribeClickCallback!=null){
                        subsribeClickCallback.subscibe(item);
                    }
//                    subscibe(item);
                }
            });
        }else {
            holder.setText(R.id.btn2, "取消订阅");
            holder.setVisible(R.id.btn1, false);
            holder.setVisible(R.id.btn2, true);
            holder.setOnClickListener(R.id.btn2, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new SimpleDialog.Builder().title("温馨提示").confirm("确定").message("确定取消该订阅？").listener(new OnDialogListener() {
                        @Override
                        public void dialogSureClick() {
//                                    unsubscibe(item, pos);
                            if(subsribeClickCallback!=null){
                                subsribeClickCallback.unsubscibe(item, pos);
                            }
                        }
                    }).build(mContext).show();

                }
            });
        }

    }
}
