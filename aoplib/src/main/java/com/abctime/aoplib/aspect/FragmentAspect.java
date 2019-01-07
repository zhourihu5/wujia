package com.abctime.aoplib.aspect;

import com.abctime.lib_common.utils.LogUtil;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

/**
 * author:Created by xmren on 2018/7/12.
 * email :renxiaomin@100tal.com
 */
@Aspect
public class FragmentAspect {
    @After("execution(* android.support.v4.app.on**(..))")
    public void onFragmentLiftCyle(JoinPoint joinPoint) throws Throwable {
        LogUtil.i("aspect:" + joinPoint.getSignature());
    }
}
