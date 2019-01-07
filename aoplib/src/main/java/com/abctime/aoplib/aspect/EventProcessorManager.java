package com.abctime.aoplib.aspect;

/**
 * author:Created by xmren on 2018/7/19.
 * email :renxiaomin@100tal.com
 */

public class EventProcessorManager {
    public static IEventProcessor geProcessor(String eventProcessorName){
        if(eventProcessorName.equals("")){
            return new OrderEventProcessor();
        }
        return null;
    }
}
