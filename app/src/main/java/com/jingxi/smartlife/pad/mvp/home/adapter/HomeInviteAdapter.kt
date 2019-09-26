package com.jingxi.smartlife.pad.mvp.home.adapter

import android.content.Context
import android.widget.ImageView

import com.jingxi.smartlife.pad.R
import com.jingxi.smartlife.pad.mvp.home.data.HomeUserInfoBean
import com.wujia.lib.imageloader.ImageLoaderManager
import com.wujia.lib_common.base.baseadapter.CommonAdapter
import com.wujia.lib_common.base.baseadapter.base.ViewHolder

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
class HomeInviteAdapter(context: Context, datas: List<HomeUserInfoBean.DataBean.UserInfoListBean>) : CommonAdapter<HomeUserInfoBean.DataBean.UserInfoListBean>(context, R.layout.item_home_invite_layout, datas) {

    override fun convert(holder: ViewHolder, item: HomeUserInfoBean.DataBean.UserInfoListBean, pos: Int) {

        holder.setText(R.id.tv1, item.userName)
        val img = holder.getView<ImageView>(com.jingxi.smartlife.pad.market.R.id.img1)
        ImageLoaderManager.instance.loadImage(item.icon, R.mipmap.icon_head_default, img)
    }
}
