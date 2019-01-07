package com.abctime.aoplib.aspect;

import com.abctime.aoplib.annotation.LoginTracker;
import com.abctime.aoplib.aspect.statistics.StatisticsHelper;
import com.abctime.aoplib.aspect.statistics.UserFigure;
import com.abctime.lib_common.data.SPHelper;
import com.abctime.lib_common.utils.AppContext;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * author:Created by xmren on 2018/7/12.
 * email :renxiaomin@100tal.com
 */
@Aspect
public class LoginTrackerAspect {

    private static final String POINTCUT_METHOD =
            "execution(@com.abctime.aoplib.annotation.LoginTracker * *(..))";

    private static final String POINTCUT_CONSTRUCTOR =
            "execution(@com.abctime.aoplib.annotation.LoginTracker *.new(..))";

    @Pointcut(POINTCUT_METHOD)
    public void methodAnnotatedLogin() {
    }

    @Pointcut(POINTCUT_CONSTRUCTOR)
    public void constructorAnnotatedLogin() {
    }

    @Around("methodAnnotatedLogin() || constructorAnnotatedLogin()")
    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        LoginTracker trackerAnnotation = method.getAnnotation(LoginTracker.class);
        if (trackerAnnotation != null) {
            String memberid = (String) SPHelper.get(AppContext.get(), "memberid", "");
            String username = (String) SPHelper.get(AppContext.get(), "username", "");
            StatisticsHelper.getHelper().loginEvent(UserFigure.createUser(memberid, username));
        }
        return result;
    }

}
