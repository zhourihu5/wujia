package com.wujia.businesslib.util

import android.content.Intent
import com.wujia.businesslib.base.BaseApplication
import com.wujia.lib_common.utils.AppContext

object LoginUtil {
    fun toLoginActivity() {
        try {
            val currentActivity = BaseApplication.currentAcitivity
            var context = AppContext.get()
            val intent = Intent()
            if (currentActivity != null) {
                context = currentActivity
            } else {
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK//applicationcontext 启动只能以newtask
            }

            intent.setClassName(context, "com.jingxi.smartlife.pad.mvp.login.LoginActivity")
            context.startActivity(intent)
            if (currentActivity != null) {
                currentActivity!!.finish()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
