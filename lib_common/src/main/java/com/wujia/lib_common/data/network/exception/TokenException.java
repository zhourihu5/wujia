package com.wujia.lib_common.data.network.exception;


public class TokenException extends RuntimeException {

    public TokenException(Throwable throwable) {
        super(throwable.getMessage(), throwable);
    }

    public TokenException(String message) {
        super(message);
    }


}

