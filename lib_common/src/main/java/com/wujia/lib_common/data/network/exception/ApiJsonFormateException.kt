package com.wujia.lib_common.data.network.exception

/**
 * Author: shenbingkai
 * CreateDate: 2019-04-06 00:51
 * Description:
 */

class ApiJsonFormateException(var originData: String, cause: String) : RuntimeException(cause)
