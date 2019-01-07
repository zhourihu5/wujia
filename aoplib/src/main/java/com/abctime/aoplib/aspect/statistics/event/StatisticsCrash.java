package com.abctime.aoplib.aspect.statistics.event;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * author:Created by xmren on 2018/7/13.
 * email :renxiaomin@100tal.com
 */

public class StatisticsCrash extends Statistics {


    private Throwable ex;

    public StatisticsCrash(Throwable ex) {
        this.ex = ex;
    }

    @Override
    public String getType() {
        return CRASH;
    }

    @Override
    public String getExtras() {
        if (ex == null) {
            return "";
        }
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        return writer.toString();
    }

}
