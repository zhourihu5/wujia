package com.jingxi.smartlife.pad.mvp.setting.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView

import com.jingxi.smartlife.pad.R
import com.jingxi.smartlife.pad.mvp.home.data.HomeRecBean
import com.wujia.lib.imageloader.ImageLoaderManager
import com.wujia.lib_common.base.baseadapter.CommonAdapter
import com.wujia.lib_common.base.baseadapter.base.ViewHolder

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
class HomeCardManagerAdapter(context: Context, datas: List<HomeRecBean.Card>, private val form: Int) : CommonAdapter<HomeRecBean.Card>(context, R.layout.item_home_rec_wrapper, datas) {

    private var managerCardListener: OnManagerCardListener? = null

    override fun convert(holder: ViewHolder, item: HomeRecBean.Card, position: Int) {
        val cont = holder.getView<FrameLayout>(R.id.card_manager_cont)
        var subview: View? = null

        when (item.type) {
            HomeRecBean.TYPE_APP_PAGE,
                //                subview = LayoutInflater.from(mContext).inflate(R.layout.item_home_rec_layout_0, null);
                //                break;
            HomeRecBean.TYPE_IMAGE, HomeRecBean.TYPE_LINK -> {
                subview = LayoutInflater.from(mContext).inflate(R.layout.item_home_rec_layout_1, null)

                val desc = subview!!.findViewById<TextView>(R.id.scene_in_mode_status_tv)
                desc.text = item.explain

                val img = subview.findViewById<ImageView>(R.id.scene_in_img)
                ImageLoaderManager.instance.loadImage(item.image, R.mipmap.default_loading, img)
            }
        }

        val title = subview!!.findViewById<TextView>(R.id.scene_in_mode_tv)
        title.text = item.title

        cont.removeAllViews()
        cont.addView(subview)

        if (form == FORM_ADDED) {
            holder.setImageResource(R.id.card_manager_btn, R.mipmap.icon_smart_manage_release)
        }
        holder.setOnClickListener(R.id.card_manager_btn) {
            if (form == FORM_ADDED) {
                if (null != managerCardListener)
                    managerCardListener!!.removeCard(position)
            } else {
                if (null != managerCardListener)
                    managerCardListener!!.addCard(position)
            }
        }
    }

    fun setManagerCardListener(managerCardListener: OnManagerCardListener) {
        this.managerCardListener = managerCardListener
    }

    interface OnManagerCardListener {
        fun addCard(pos: Int)

        fun removeCard(pos: Int)
    }

    companion object {

        const val FORM_ADDED = 0
        const val FORM_UNADD = 1
    }
}
