package com.abctime.aoplib.aspect;

import com.abctime.aoplib.annotation.RegisterTracker;
import com.abctime.aoplib.aspect.statistics.StatisticsHelper;
import com.abctime.aoplib.aspect.statistics.UserFigure;
import com.abctime.lib_common.data.SPHelper;
import com.abctime.lib_common.utils.AppContext;

import org.aspectj.lang.ProceedingJoinPoint;
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
public class RegisterTrackerAspect {

    private static final String POINTCUT_METHOD = "execution(@com.abctime.aoplib.annotation.RegisterTracker * *(..))";

    private static final String POINTCUT_CONSTRUCTOR = "execution(@com.abctime.aoplib.annotation.RegisterTracker *.new(..))";

    @Pointcut(POINTCUT_METHOD)
    public void methodAnnotatedRegister() {
    }

    @Pointcut(POINTCUT_CONSTRUCTOR)
    public void constructorAnnotatedRegister() {
    }

    @Around("methodAnnotatedRegister() || constructorAnnotatedRegister()")
    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        Object proceed = joinPoint.proceed();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        RegisterTracker trackerAnnotation = method.getAnnotation(RegisterTracker.class);
        if (trackerAnnotation != null) {
            String memberid = (String) SPHelper.get(AppContext.get(), "memberid", "");
            String username = (String) SPHelper.get(AppContext.get(), "username", "");
            StatisticsHelper.getHelper().registerEvent(UserFigure.createUser(memberid, username));
        }
        return proceed;
    }

}
