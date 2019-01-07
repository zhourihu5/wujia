package com.abctime.aoplib.aspect;


import com.abctime.lib_common.utils.LogUtil;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * author:Created by xmren on 2018/7/12.
 * email :renxiaomin@100tal.com
 */
@Aspect
public class TraceRunTimeAspect {

    private static final String POINTCUT_METHOD =
            "execution(@com.abctime.aoplib.annotation.TraceRunTime * *(..))";

    private static final String POINTCUT_CONSTRUCTOR =
            "execution(@com.abctime.aoplib.annotation.TraceRunTime *.new(..))";

    @Pointcut(POINTCUT_METHOD)
    public void methodAnnotatedWithTraceRunTime() {
    }

    @Pointcut(POINTCUT_CONSTRUCTOR)
    public void constructorAnnotatedTraceRunTime() {
    }

    @Around("methodAnnotatedWithTraceRunTime() || constructorAnnotatedTraceRunTime()")
    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String className = methodSignature.getDeclaringType().getSimpleName();

        String methodName = methodSignature.getName();

        final MethodWatcher stopWatch = new MethodWatcher();
        stopWatch.start();

        Object result = joinPoint.proceed();

        stopWatch.stop();

        LogUtil.d(buildLogMessage(methodName, stopWatch.getTotalTimeMillis()));
        return result;
    }

    private static String buildLogMessage(String methodName, long methodDuration) {
        StringBuilder message = new StringBuilder();
        message.append("TraceRunTimeAspect --> ");
        message.append(methodName);
        message.append(" --> ");
        message.append("[");
        message.append(methodDuration);
        message.append("ms");
        message.append("]");

        return message.toString();
    }
}
