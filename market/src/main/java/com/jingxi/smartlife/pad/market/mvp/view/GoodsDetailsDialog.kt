package com.jingxi.smartlife.pad.market.mvp.view

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager

import com.jingxi.smartlife.pad.market.R
import com.wujia.businesslib.dialog.CommDialog
import com.wujia.lib.imageloader.DensityUtil
import com.wujia.lib.widget.util.ToastUtil

/**
 * Author: created by shenbingkai on 2019/2/23 17 17
 * Email:  shenbingkai@gamil.com
 * Description:
 */
class GoodsDetailsDialog(context: Context) : CommDialog(context, R.style.dialogStyle), View.OnClickListener {

    override fun init(context: Context) {

        setSize()

        findViewById<View>(R.id.btn1).setOnClickListener(this)

    }

    override fun getLayoutId(): Int {
        return R.layout.dialog_goods_details
    }

    private fun setSize() {
        val dialogWindow = window
        val lp = dialogWindow!!.attributes
        dialogWindow.setGravity(Gravity.CENTER)

        lp.width = DensityUtil.dp2px(mContext, (845 + baseWidthPx).toFloat())
        lp.height = DensityUtil.dp2px(mContext, 343f)

        //        lp.y=176;
        dialogWindow.attributes = lp
    }

    override fun onClick(v: View) {
        ToastUtil.showShort(context, "增加成功")
        dismiss()
    }
}
