package com.jingxi.smartlife.pad.property.mvp.adapter

import android.content.Context
import android.widget.TextView
import com.jingxi.smartlife.pad.property.R
import com.jingxi.smartlife.pad.property.mvp.data.YellowPage
import com.wujia.lib_common.base.baseadapter.CommonAdapter
import com.wujia.lib_common.base.baseadapter.base.ViewHolder

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
class YellowPageAdapter(context: Context, datas: List<YellowPage>) : CommonAdapter<YellowPage>(context, R.layout.item_yellow_page, datas) {

    override fun convert(holder: ViewHolder, item: YellowPage, position: Int) {
        val tvName = holder.getView<TextView>(R.id.tvName)
        val tvPhoneName = holder.getView<TextView>(R.id.tvPhoneName)
        val tvPhoneNum = holder.getView<TextView>(R.id.tvPhoneNum)
        val tvAddressName = holder.getView<TextView>(R.id.tvAddressName)
        val tvAddress = holder.getView<TextView>(R.id.tvAddress)
        tvName.setText(item.name + "信息")
        tvPhoneName.setText(item.name + "电话")
        tvAddressName.setText(item.name + "地址")
        tvPhoneNum.setText(item.phone)
        tvPhoneNum.setText(item.address)

    }
}
