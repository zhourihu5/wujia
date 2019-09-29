package com.wujia.businesslib.base

import com.wujia.businesslib.data.LoginDTO
import com.wujia.lib_common.base.Constants
import com.wujia.lib_common.utils.AppContext
import com.wujia.lib_common.utils.SPHelper

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-21
 * description ：
 */
object DataManager {

    //    private static LoginDTO.DataBean user;

    val user: LoginDTO.DataBean
        @Throws(Exception::class)
        get() {
            val `object` = SPHelper.readObject(AppContext.get(), Constants.SP_KEY_USER)
                    ?: throw Exception("please relogin to get user")
            return `object` as LoginDTO.DataBean
        }

    val familyId: String?
        @Throws(Exception::class)
        get() = user.userInfo!!.fid
    val communtityId: Int?
        @Throws(Exception::class)
        get() = user.userInfo!!.communtityId

    // return "001901181CD10000";
    val dockKey: String
        @Throws(Exception::class)
        get() {
            val user = user
            if (null != user) {
                val count = user.device!!.deviceKey!!.length
                val newDockkey = StringBuilder()
                if (count < 16) {
                    for (i in 0 until 16 - count) {
                        newDockkey.append("0")
                    }
                }
                return user.device!!.deviceKey!! + newDockkey.toString()
            }
            throw Exception("no dock key,please relogin to get dockkey")
        }

    val buttonKey: String?
        @Throws(Exception::class)
        get() {
            val user = user
            if (null != user) {
                return user.device!!.buttonKey
            }
            throw Exception("no dock key,please relogin to get dockkey")
        }
    val sip: LoginDTO.DataBean.SipDTO?
        @Throws(Exception::class)
        get() {
            val user = user
            if (null != user) {
                return user.sip
            }
            throw Exception("no dock key,please relogin to get dockkey")
        }

    val token: String
        get() = SPHelper.get(AppContext.get(), Constants.COMMON_REQUEST_TOKEN, "") as String

    fun saveToken(token: String) {
        SPHelper.put(AppContext.get(), Constants.COMMON_REQUEST_TOKEN, token)
    }
}
