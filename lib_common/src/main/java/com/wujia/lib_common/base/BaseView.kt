package com.wujia.lib_common.base

/**
 * Created by xmren on 2018/5/4.
 */

interface BaseView {

//    val context: Context
    fun showErrorMsg(msg: String)

    fun showLoadingDialog(text: String)

    fun hideLoadingDialog()

    fun onLoginStatusError()
}
