package com.abctime.aoplib.aspect;

import com.abctime.aoplib.aspect.statistics.StatisticsHelper;
import com.abctime.lib_common.utils.LogUtil;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * author:Created by xmren on 2018/7/12.
 * email :renxiaomin@100tal.com
 */
@Aspect
public class ActivityAspect {

    @Pointcut("execution(* com.abctime.lib_common.base.BaseActivity.onCreate(..))")
    public void onCreateCutPoint() {
    }

    @Pointcut("execution(* com.abctime.lib_common.base.BaseActivity.onResume())")
    public void onResumeCutPoint() {

    }

    @Pointcut("execution(* com.abctime.lib_common.base.BaseActivity.onPause(..))")
    public void onPauseCutPoint() {

    }

    @Pointcut("execution(* android.app.Activity.onStart(..))")
    public void onStartCutPoint() {

    }

    @Pointcut("execution(* android.app.Activity.onStop(..))")
    public void onStopCutPoint() {

    }

    @Pointcut("execution(* com.abctime.lib_common.base.BaseActivity.onDestroy(..))")
    public void onDestroyCutPoint() {

    }

    @After("onResumeCutPoint()")
    public void onActivityCreate(JoinPoint joinPoint) throws Throwable {
        LogUtil.i("aspect:" + joinPoint.getSignature());
        Object target = joinPoint.getTarget();
        StatisticsHelper.getHelper().pageEnterTrack("page_" + target.getClass().getSimpleName());
    }

    @After("onPauseCutPoint()")
    public void onActivityDestroy(JoinPoint joinPoint) throws Throwable {
        LogUtil.i("aspect:" + joinPoint.getSignature());
        Object target = joinPoint.getTarget();
        StatisticsHelper.getHelper().pageEndTrack("page_" + target.getClass().getSimpleName());
    }
}
