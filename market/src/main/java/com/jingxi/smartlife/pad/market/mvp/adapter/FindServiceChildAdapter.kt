package com.jingxi.smartlife.pad.market.mvp.adapter

import android.content.Context
import android.widget.ImageView
import com.jingxi.smartlife.pad.market.R
import com.wujia.businesslib.data.CardDetailBean
import com.wujia.businesslib.dialog.SimpleDialog
import com.wujia.lib.imageloader.ImageLoaderManager
import com.wujia.lib_common.base.baseadapter.CommonAdapter
import com.wujia.lib_common.base.baseadapter.base.ViewHolder

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
class FindServiceChildAdapter : CommonAdapter<CardDetailBean.ServicesBean> {

    private var subsribeClickCallback: SubsribeClickCallback? = null

    //    private int mType;

    interface SubsribeClickCallback {
        fun subscibe(item: CardDetailBean.ServicesBean)

        fun unsubscibe(item: CardDetailBean.ServicesBean, pos: Int)
    }

    constructor(context: Context, datas: List<CardDetailBean.ServicesBean>) : super(context, R.layout.item_service_find_child, datas)

    fun setSubsribeClickCallback(subsribeClickCallback: SubsribeClickCallback) {
        this.subsribeClickCallback = subsribeClickCallback
    }

    override fun convert(holder: ViewHolder, item: CardDetailBean.ServicesBean, pos: Int) {

        val img = holder.getView<ImageView>(R.id.img1)
        item.cover?.let { ImageLoaderManager.instance.loadImage(it, 0, img) }//placeholder should be passed
        holder.setText(R.id.tv1, item.title)
        holder.setText(R.id.tv2, item.memo)

        if (item.isSubscribe == 0) {
            holder.setVisible(R.id.btn1, true)
            holder.setVisible(R.id.btn2, false)
            holder.setOnClickListener(R.id.btn1) {
                if (subsribeClickCallback != null) {
                    subsribeClickCallback!!.subscibe(item)
                }
                //                    subscibe(item);
            }
        } else {
            holder.setText(R.id.btn2, "取消订阅")
            holder.setVisible(R.id.btn1, false)
            holder.setVisible(R.id.btn2, true)
            holder.setOnClickListener(R.id.btn2) {
                SimpleDialog.Builder().title("温馨提示").confirm("确定").message("确定取消该订阅？").listener {
                    //                                    unsubscibe(item, pos);
                    if (subsribeClickCallback != null) {
                        subsribeClickCallback!!.unsubscibe(item, pos)
                    }
                }.build(mContext).show()
            }
        }

    }
}
