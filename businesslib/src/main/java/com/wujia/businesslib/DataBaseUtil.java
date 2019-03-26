package com.wujia.businesslib;

import com.litesuits.orm.LiteOrm;
import com.wujia.lib_common.utils.AppContext;

import java.util.ArrayList;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-27
 * description ：
 */
public class DataBaseUtil {


    private static LiteOrm liteOrm;

    public static LiteOrm getLiteOrm() {


        if (liteOrm == null)

        {
            liteOrm = LiteOrm.newSingleInstance(AppContext.get(), "wj_terminal.db");
            liteOrm.setDebugged(true); // open the log
        }

        return liteOrm;
    }

    public static <T> ArrayList<T> getMemberList(Class<T> cls) {
        return getLiteOrm().query(cls);
    }
}
