package com.jingxi.smartlife.pad.safe.mvp.adapter

import android.content.Context
import android.text.TextUtils
import android.util.SparseBooleanArray

import com.jingxi.smartlife.pad.safe.R
import com.jingxi.smartlife.pad.sdk.doorAccess.base.bean.DoorRecordBean
import com.wujia.lib_common.base.baseadapter.CommonAdapter
import com.wujia.lib_common.base.baseadapter.base.ViewHolder
import com.wujia.lib_common.utils.DateUtil


/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
class PlayBackAdapter(context: Context, datas: List<DoorRecordBean>) : CommonAdapter<DoorRecordBean>(context, R.layout.item_safe_play_back, datas) {


    private var isEdit: Boolean = false
    val checkMap: SparseBooleanArray?

    fun setEdit(edit: Boolean) {
        isEdit = edit
    }

    fun itemClick(pos: Int) {
        if (checkMap!!.get(pos)) {
            checkMap.put(pos, false)
        } else {
            checkMap.put(pos, true)
        }
        notifyItemChanged(pos)
    }

    fun chooseAll() {
        for (i in 0 until itemCount) {
            checkMap!!.put(i, true)
        }
        notifyDataSetChanged()
    }

    init {
        checkMap = SparseBooleanArray()
    }

    fun clearCheck() {
        checkMap?.clear()
    }

    override fun convert(holder: ViewHolder, item: DoorRecordBean, pos: Int) {

        if (isEdit) {
            holder.setVisible(R.id.play_back_video_btn, false)
            holder.setVisible(R.id.play_back_checkbox, true)

            if (checkMap!!.get(pos)) {
                holder.setBackgroundRes(R.id.play_back_checkbox, R.mipmap.icon_save_checkon)
            } else {
                holder.setBackgroundRes(R.id.play_back_checkbox, R.mipmap.icon_save_checkoff)
            }

        } else {
            holder.setVisible(R.id.play_back_video_btn, true)
            holder.setVisible(R.id.play_back_checkbox, false)
        }

        holder.setText(R.id.play_back_title, item.show_name)
        holder.setText(R.id.play_back_time_tv, DateUtil.formathhMMss(item.startTime))

        holder.setVisible(R.id.play_back_save_state, !TextUtils.isEmpty(item.thumbPath))
    }
}
