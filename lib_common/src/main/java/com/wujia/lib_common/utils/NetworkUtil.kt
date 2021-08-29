package com.wujia.lib_common.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import java.net.Inet4Address
import java.net.NetworkInterface

/**
 * @title: NetworkUtil.java
 * @description: 网络工具类
 * @date 2015-3-4 上午9:47:30
 */
object NetworkUtil {
    private const val NONE = -1
    private const val WIFI = 1
    private const val MOBILE = 0

    private var netStatus = NONE

    /**
     * @param context
     * @return 参数说明
     * @methods getNetWork
     * @description 判断有无网络
     * @date 2015-3-4 上午11:24:04
     */
    fun getNetWork(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netinfo = cm.activeNetworkInfo
        return null != netinfo
    }

    /**
     * @param context
     * @return 参数说明
     * @methods getNetWorkInfoType
     * @description 判断WIFI还是流量
     * @date 2015-3-4 上午11:24:22
     */
    private fun getNetWorkInfoType(context: Context): Int {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netinfo = cm.activeNetworkInfo
        netStatus = if (null == netinfo) {
            NONE
        } else {
            netinfo.type
        }
        return netStatus
    }

    fun isNetAvailable(context: Context): Boolean {
        //        return hasNet;
        return getNetWork(context)
    }
}