package com.abctime.aoplib.aspect;

/**
 * author:Created by xmren on 2018/7/12.
 * email :renxiaomin@100tal.com
 */

public class AspectConstant {
    static final String START = "execution(@com.abctime.aoplib.annotation.";
    static final String END_METHOD = " * *(..))";
    static final String END_CONSTRUCTOR = " *.new(..))";
}
