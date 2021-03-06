package com.wujia.lib_common.data.network.exception

/**
 * 约定异常
 */

object ERROR {
    /**
     * 未知错误
     */
    const val UNKNOWN = "1000"
    /**
     * 解析错误
     */
    const val PARSE_ERROR = "1001"
    /**
     * 网络错误
     */
    const val NETWORK_TIMEOUT = "1002"
    /**
     * 网络连接错误
     */
    const val NETWORK_CONNECT_ERROR = "1004"
    /**
     * 协议出错
     */
    const val HTTP_ERROR = "1003"
}
