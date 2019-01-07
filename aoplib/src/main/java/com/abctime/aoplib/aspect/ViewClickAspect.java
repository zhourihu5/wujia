package com.abctime.aoplib.aspect;

import android.util.Log;
import android.view.View;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * author:Created by xmren on 2018/7/12.
 * email :renxiaomin@100tal.com
 */
@Aspect
public class ViewClickAspect {
//    private static final String TAG = ViewClickAspect.class.getName();
//    private boolean canDoubleClick = false;
//    private View mLastView;
//
//    @Before("@annotation(com.abctime.aoplib.annotation.DoubleClick)")
//    public void beforeEnableDoubleClcik(JoinPoint joinPoint) throws Throwable {
//        canDoubleClick = true;
//    }
//
//    @Around("execution(* android.view.View.OnClickListener.onClick(..))  && target(Object) && this(Object)")
//    public void OnClickListener(ProceedingJoinPoint joinPoint) throws Throwable {
//        Object[] objects = joinPoint.getArgs();
//        View view = objects.length == 0 ? null : (View) objects[0];
//        if (view != mLastView || canDoubleClick || !NoDoubleClickUtils.isDoubleClick()) {
//            joinPoint.proceed();
//            canDoubleClick = false;
//        }
//        mLastView = view;
//    }
}
