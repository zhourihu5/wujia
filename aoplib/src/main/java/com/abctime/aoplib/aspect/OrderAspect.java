package com.abctime.aoplib.aspect;

import android.text.TextUtils;

import com.abctime.aoplib.aspect.statistics.Order;
import com.abctime.aoplib.aspect.statistics.StatisticsHelper;
import com.abctime.lib_common.data.SPHelper;
import com.abctime.lib_common.utils.AppContext;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * author:Created by xmren on 2018/7/12.
 * email :renxiaomin@100tal.com
 */
@Aspect
public class OrderAspect {

    private static final String ORDER_CREATE = "execution(@com.abctime.aoplib.annotation.OrderCreateTracker * *(..))";
    private static final String ORDER_PAY = "execution(@com.abctime.aoplib.annotation.OrderPayTracker * *(..))";

    @Pointcut(ORDER_PAY)
    public void methodOrderPay() {
    }

    @Pointcut(ORDER_CREATE)
    public void methodOrderCreate() {
    }

    @Around("methodOrderCreate()")
    public Object weaveOrderCreateJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        if (result != null && result instanceof Order) {
            String memberid = (String) SPHelper.get(AppContext.get(), "memberid", "");
            StatisticsHelper.getHelper().placeOrder(memberid, (Order) result);
        }
        return result;
    }


    @Around("methodOrderPay()")
    public Object weavePaySuccJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 1) {
            String memberid = (String) SPHelper.get(AppContext.get(), "memberid", "");
            Object arg = args[0];
            String arg1 = (String)args[1];

            arg1 = TextUtils.isEmpty(arg1) ? "" : arg1;

            if (arg != null && arg instanceof Order) {
                StatisticsHelper.getHelper().orderPaySuccess(memberid, arg1, (Order) arg);
            }
        }
        return result;
    }


}
