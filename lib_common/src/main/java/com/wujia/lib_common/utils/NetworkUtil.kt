package com.wujia.lib_common.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager

import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.util.Enumeration

/**
 * @title: NetworkUtil.java
 * @description: 网络工具类
 * @date 2015-3-4 上午9:47:30
 */
object NetworkUtil {
    val NONE = -1
    val WIFI = 1
    val MOBILE = 0

    var netStatus = NONE

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
        if (null == netinfo) {
            netStatus = NONE
        } else {
            netStatus = netinfo.type
        }
        return netStatus
    }

    /**
     * 获取本机IP
     *
     * @param context
     * @return
     */
    fun getLocalIpAddress(context: Context): String {

        val type = getNetWorkInfoType(context)
        when (type) {
            NetworkUtil.MOBILE -> {
                try {
                    val en = NetworkInterface
                            .getNetworkInterfaces()
                    while (en.hasMoreElements()) {
                        val intf = en.nextElement()
                        val enumIpAddr = intf
                                .inetAddresses
                        while (enumIpAddr.hasMoreElements()) {
                            val inetAddress = enumIpAddr.nextElement()
                            if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                                // if (!inetAddress.isLoopbackAddress() && inetAddress
                                // instanceof Inet6Address) {
                                return inetAddress.getHostAddress()
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                return ""
            }
            NetworkUtil.WIFI -> {
                val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
                val wifiInfo = wifiManager.connectionInfo
                // 获取32位整型IP地址
                val ipAddress = wifiInfo.ipAddress

                //返回整型地址转换成“*.*.*.*”地址
                return String.format("%d.%d.%d.%d",
                        ipAddress and 0xff, ipAddress shr 8 and 0xff,
                        ipAddress shr 16 and 0xff, ipAddress shr 24 and 0xff)
            }
        }
        return ""
    }

    fun isNetAvailable(context: Context): Boolean {
        //        return hasNet;
        return getNetWork(context)
    }
}