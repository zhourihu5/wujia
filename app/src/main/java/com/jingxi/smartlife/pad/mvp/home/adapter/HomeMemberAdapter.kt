package com.jingxi.smartlife.pad.mvp.home.adapter

import android.content.Context
import android.widget.ImageView

import com.jingxi.smartlife.pad.R
import com.jingxi.smartlife.pad.mvp.home.data.HomeUserInfoBean
import com.wujia.lib.imageloader.ImageLoaderManager
import com.wujia.lib_common.base.baseadapter.CommonAdapter
import com.wujia.lib_common.base.baseadapter.base.ViewHolder
import kotlin.math.min

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
class HomeMemberAdapter(context: Context, datas: List<HomeUserInfoBean.DataBean.UserInfoListBean>) : CommonAdapter<HomeUserInfoBean.DataBean.UserInfoListBean>(context, R.layout.item_home_head, datas) {

    override fun convert(holder: ViewHolder, item: HomeUserInfoBean.DataBean.UserInfoListBean, pos: Int) {

        //        holder.setText(R.id.scene_in_mode_tv,item.title);

        val img = holder.getView<ImageView>(com.jingxi.smartlife.pad.market.R.id.img1)
        ImageLoaderManager.getInstance().loadImage(item.icon, R.mipmap.icon_head_default, img)

    }

    override fun getItemCount(): Int {

        return if (mDatas == null) 0 else min(4, mDatas.size)
    }

}
