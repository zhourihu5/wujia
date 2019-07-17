package com.jingxi.smartlife.pad.mvp.home.adapter

import android.content.Context
import android.widget.ImageView

import com.jingxi.smartlife.pad.R
import com.jingxi.smartlife.pad.mvp.home.data.HomeRecBean
import com.wujia.lib.imageloader.ImageLoaderManager
import com.wujia.lib_common.base.baseadapter.MultiItemTypeAdapter
import com.wujia.lib_common.base.baseadapter.base.ItemViewDelegate
import com.wujia.lib_common.base.baseadapter.base.ViewHolder

/**
 */
class HomeCardAdapter(context: Context, datas: List<HomeRecBean.Card>) : MultiItemTypeAdapter<HomeRecBean.Card>(context, datas) {
    init {

        addItemViewDelegate(1, object : ItemViewDelegate<HomeRecBean.Card> {
            override val itemViewLayoutId: Int
                get() = R.layout.item_home_rec_layout_1_shadow

            override fun isForViewType(item: HomeRecBean.Card, position: Int): Boolean {
                return (item.type == HomeRecBean.TYPE_LINK || item.type == HomeRecBean.TYPE_APP_PAGE
                        || item.type == HomeRecBean.TYPE_IMAGE)
            }

            override fun convert(holder: ViewHolder, item: HomeRecBean.Card, pos: Int) {
                val img = holder.getView<ImageView>(R.id.scene_in_img)
                ImageLoaderManager.getInstance().loadImage(item.image, R.mipmap.default_loading, img)
                holder.setText(R.id.scene_in_mode_tv, item.title)
                holder.setText(R.id.scene_in_mode_status_tv, item.explain)

            }
        })

        addItemViewDelegate(10, object : ItemViewDelegate<HomeRecBean.Card> {
            override val itemViewLayoutId: Int
                get() = R.layout.item_home_rec_layout_add


            override fun isForViewType(item: HomeRecBean.Card, position: Int): Boolean {
                return item.type == HomeRecBean.TYPE_ADD
            }

            override fun convert(viewHolder: ViewHolder, data: HomeRecBean.Card, i: Int) {}
        })
    }
}
