package com.abctime.aoplib.aspect;

import android.text.TextUtils;

import com.abctime.aoplib.annotation.Tracker;
import com.abctime.aoplib.aspect.statistics.StatisticsHelper;

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
public class TrackerAspect {

    private static final String POINTCUT_METHOD =
            "execution(@com.abctime.aoplib.annotation.Tracker * *(..))";

    private static final String POINTCUT_CONSTRUCTOR =
            "execution(@com.abctime.aoplib.annotation.Tracker *.new(..))";

    @Pointcut(POINTCUT_METHOD)
    public void methodAnnotatedWithTracker() {
    }

    @Pointcut(POINTCUT_CONSTRUCTOR)
    public void constructorAnnotatedTraceker() {
    }

    @Around("methodAnnotatedWithTracker() || constructorAnnotatedTraceker()")
    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Tracker trackerAnnotation = method.getAnnotation(Tracker.class);
        if (trackerAnnotation != null) {
            String eventProcessorName = trackerAnnotation.eventProcessor();
            if (TextUtils.isEmpty(eventProcessorName)) {
                String eventId = trackerAnnotation.evevtId();
                if (TextUtils.isEmpty(eventId)) {
                    if (result != null)
                        eventId = result.toString();
                }
                if ("page_enter".equals(trackerAnnotation.eventType())) {
                    StatisticsHelper.getHelper().pageEnterTrack( "page_" + eventId);
                } else if ("page_end".equals(trackerAnnotation.eventType())) {
                    StatisticsHelper.getHelper().pageEndTrack("page_" + eventId);
                } else {
                    StatisticsHelper.getHelper().customEventTrack(trackerAnnotation.eventType() + "_" + eventId);
                }
            } else {
                EventProcessorManager.geProcessor(eventProcessorName).tracker();
            }
        }
        return result;
    }

}
