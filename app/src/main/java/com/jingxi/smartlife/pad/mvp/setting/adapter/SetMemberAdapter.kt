package com.jingxi.smartlife.pad.mvp.setting.adapter

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
class SetMemberAdapter(context: Context, datas: List<HomeUserInfoBean.DataBean.UserInfoListBean>) : CommonAdapter<HomeUserInfoBean.DataBean.UserInfoListBean>(context, R.layout.item_family_member_layout, datas) {

    override fun convert(holder: ViewHolder, item: HomeUserInfoBean.DataBean.UserInfoListBean, pos: Int) {

        holder.setText(R.id.name_member_set_item, item.userName)

        val img = holder.getView<ImageView>(R.id.icon_member_set_item)
        ImageLoaderManager.getInstance().loadImage(item.icon, R.mipmap.icon_head_default, img)


    }
}
