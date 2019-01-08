package com.wujia.lib_common.data.network.exception;

/**
 * author:Created by xmren on 2018/7/20.
 * email :renxiaomin@100tal.com
 */

public class ApiJsonFormateException extends RuntimeException {
    public String originData;


    public ApiJsonFormateException(String originData, String cause) {
        super(cause);
        this.originData = originData;
    }
}
