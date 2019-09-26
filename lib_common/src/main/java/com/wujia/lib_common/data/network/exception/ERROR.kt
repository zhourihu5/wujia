package com.wujia.lib_common.data.network.exception

/**
 * 约定异常
 */

object ERROR {
    /**
     * 未知错误
     */
    val UNKNOWN = "1000"
    /**
     * 解析错误
     */
    val PARSE_ERROR = "1001"
    /**
     * 网络错误
     */
    val NETWORK_TIMEOUT = "1002"
    /**
     * 网络连接错误
     */
    val NETWORK_CONNECT_ERROR = "1004"
    /**
     * 协议出错
     */
    val HTTP_ERROR = "1003"
}
