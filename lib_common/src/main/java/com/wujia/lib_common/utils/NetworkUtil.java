package com.wujia.lib_common.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @title: NetworkUtil.java
 * @description: 网络工具类
 * @date 2015-3-4 上午9:47:30
 */
public class NetworkUtil {
    public static final int NONE = -1;
    public static final int WIFI = 1;
    public static final int MOBILE = 0;

    public static int netStatus = NONE;
    private static boolean hasNet = false;

    /**
     * @param context
     * @return 参数说明
     * @methods getNetWork
     * @description 判断有无网络
     * @date 2015-3-4 上午11:24:04
     */
    public static boolean getNetWork(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        hasNet = !(null == netinfo);
        return hasNet;
    }

    /**
     * @param context
     * @return 参数说明
     * @methods getNetWorkInfoType
     * @description 判断WIFI还是流量
     * @date 2015-3-4 上午11:24:22
     */
    private static int getNetWorkInfoType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();
        if (null == netinfo) {
            netStatus = NONE;
        } else {
            netStatus = netinfo.getType();
        }
        return netStatus;
    }

    /**
     * 获取本机IP
     *
     * @param context
     * @return
     */
    public static String getLocalIpAddress(Context context) {

        final int type = getNetWorkInfoType(context);
        switch (type) {
            case NetworkUtil.MOBILE:
                try {
                    for (Enumeration<NetworkInterface> en = NetworkInterface
                            .getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf
                                .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress()
                                    && inetAddress instanceof Inet4Address) {
                                // if (!inetAddress.isLoopbackAddress() && inetAddress
                                // instanceof Inet6Address) {
                                return inetAddress.getHostAddress().toString();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "";
            case NetworkUtil.WIFI:
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                // 获取32位整型IP地址
                int ipAddress = wifiInfo.getIpAddress();

                //返回整型地址转换成“*.*.*.*”地址
                return String.format("%d.%d.%d.%d",
                        (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
                        (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
        }
        return "";
    }

    public static boolean isNetAvailable(Context context) {
//        return hasNet;
        return getNetWork(context);
    }
}