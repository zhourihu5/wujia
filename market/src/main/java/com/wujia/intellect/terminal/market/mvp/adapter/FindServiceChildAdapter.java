package com.wujia.intellect.terminal.market.mvp.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.wujia.businesslib.DataBaseUtil;
import com.wujia.businesslib.dialog.SimpleDialog;
import com.wujia.businesslib.event.EventBusUtil;
import com.wujia.businesslib.event.EventSubscription;
import com.wujia.businesslib.listener.OnDialogListener;
import com.wujia.intellect.terminal.market.R;
import com.wujia.intellect.terminal.market.mvp.data.ServiceBean;
import com.wujia.lib.imageloader.ImageLoaderManager;
import com.wujia.lib.widget.util.ToastUtil;
import com.wujia.lib_common.base.baseadapter.CommonAdapter;
import com.wujia.lib_common.base.baseadapter.base.ViewHolder;
import com.wujia.lib_common.utils.LogUtil;

import java.util.ArrayList;

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class FindServiceChildAdapter extends ServiceBaseAdapter<ServiceBean.Service> {

    private int mType;

    public FindServiceChildAdapter(Context context, ArrayList<ServiceBean.Service> datas, int type) {
        super(context, R.layout.item_service_find_child, datas);
        this.mType = type;
    }

    @Override
    protected void convert(final ViewHolder holder, final ServiceBean.Service item, final int pos) {

        ImageView img = holder.getView(R.id.img1);
        ImageLoaderManager.getInstance().loadImage(item.image, img);
        holder.setText(R.id.tv1, item.name);
        holder.setText(R.id.tv2, item.explain);

        switch (mType) {

            case TYPE_MY:

                holder.setVisible(R.id.btn1, false);
                holder.setVisible(R.id.btn2, true);
                holder.setText(R.id.btn2, "取消订阅");

                holder.setOnClickListener(R.id.btn2, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        mContext,"确定取消该订阅？","确定"
                        new SimpleDialog.Builder().title("温馨提示").confirm("确定").message("确定取消该订阅？").listener(new OnDialogListener() {
                            @Override
                            public void dialogSureClick() {
                                unsubscibe(item, pos);
                            }
                        }).build(mContext).show();
                    }
                });
                break;

            case TYPE_GOV:
                holder.setVisible(R.id.btn1, false);
                break;

            case TYPE_FIND:
            case TYPE_ALL:
            case TYPE_RECOMMEND:

                holder.setOnClickListener(R.id.btn1, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subscibe(item);
                    }
                });
                break;
        }
    }
}
