package com.wujia.lib.widget.util

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import com.wujia.lib.uikit.R


/**
 * author ：shenbingkai@163.com
 * date ：2019-01-27 00:21
 * description ： toast 工具
 */
object ToastUtil {
    //    public static Handler handler=new Handler(Looper.getMainLooper());
    internal var toast: Toast? = null

    private fun show(context: Context, msg: String, @DrawableRes iconId: Int, duration: Int) {
        //        Toast toast=null;
        val title: TextView
        val icon: ImageView
        val appContext = context.applicationContext
        if (toast == null) {
            toast = Toast(appContext)
            val contentView = View.inflate(appContext, R.layout.layout_toast, null)

            toast!!.view = contentView
            toast!!.setGravity(Gravity.CENTER, 0, 0)

        }
        title = toast!!.view.findViewById(R.id.toast_msg)
        title.text = msg
        toast!!.duration = duration
        icon = toast!!.view.findViewById(R.id.toast_icon)
        if (iconId != 0) {
            icon.setImageResource(iconId)
            icon.visibility = View.VISIBLE
        } else {
            icon.setImageResource(iconId)
            icon.visibility = View.GONE
        }

        toast!!.show()
    }

    fun showShort(context: Context?, msg: String) {
        context?.let { show(it, msg, 0, Toast.LENGTH_SHORT) }
    }

    fun showShort(context: Context, strId: Int) {
        show(context, context.getString(strId), 0, Toast.LENGTH_SHORT)
    }

    fun showLong(context: Context, msg: String) {
        show(context, msg, 0, Toast.LENGTH_LONG)
    }

    fun showShortWithIcon(context: Context, msg: String, iconId: Int) {
        show(context, msg, iconId, Toast.LENGTH_LONG)
    }
}
