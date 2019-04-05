package com.wujia.lib_common.data.network.exception;

/**
 * Author: shenbingkai
 * CreateDate: 2019-04-06 00:51
 * Description:
 */

public class ApiJsonFormateException extends RuntimeException {
    public String originData;


    public ApiJsonFormateException(String originData, String cause) {
        super(cause);
        this.originData = originData;
    }
}
