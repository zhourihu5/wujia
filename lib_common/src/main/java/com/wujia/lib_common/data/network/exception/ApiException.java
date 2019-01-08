package com.wujia.lib_common.data.network.exception;


public class ApiException extends Exception {
    public int code;

    public ApiException(Throwable throwable) {
        super(throwable.getMessage(), throwable);
    }

    public ApiException(Throwable throwable, int code, String message) {
        super(message, throwable);
        this.code = code;
    }

    public boolean isNetworkConnectError() {
        return code == ERROR.NETWORK_CONNECT_ERROR ? true : false;
    }

}

