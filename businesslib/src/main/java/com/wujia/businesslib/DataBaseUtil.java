package com.wujia.businesslib;

import com.litesuits.orm.LiteOrm;
import com.litesuits.orm.db.assit.QueryBuilder;
import com.wujia.lib_common.utils.AppContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-27
 * description ：
 */
public class DataBaseUtil {


    private static LiteOrm liteOrm;

    private static LiteOrm getLiteOrm() {

        if (liteOrm == null)

        {
            liteOrm = LiteOrm.newSingleInstance(AppContext.get(), "wj_terminal.db");
            liteOrm.setDebugged(true); // open the log
        }

        return liteOrm;
    }

    public static <T> ArrayList<T> query(Class<T> cls) {
        return getLiteOrm().query(cls);
    }

    public static <T> ArrayList<T> query(QueryBuilder builder) {
        return getLiteOrm().query(builder);
    }

    public static <T> ArrayList<T> queryEquals(Map<String, Object> map, Class<T> cls) {
        QueryBuilder builder = new QueryBuilder<T>(cls);
        for (String key : map.keySet()) {
            builder.whereAppendAnd();
            builder.whereEquals(key, map.get(key));
        }
        return getLiteOrm().query(builder);
    }

    public static <T> ArrayList<T> queryEquals(String key, Object value, Class<T> cls) {
        return getLiteOrm().query(new QueryBuilder<T>(cls).whereEquals(key, value));
    }

    public static <T> ArrayList<T> queryNotEquals(Map<String, Object> map, Class<T> cls) {
        QueryBuilder builder = new QueryBuilder<T>(cls);
        for (String key : map.keySet()) {
            builder.whereAppendAnd();
            builder.whereNoEquals(key, map.get(key));
        }
        return getLiteOrm().query(builder);
    }


    public static <T> ArrayList<T> queryGreater(String key, Object value, Class<T> cls) {
        return getLiteOrm().query(new QueryBuilder<T>(cls)
                .whereGreaterThan(key, value));
    }

    public static void insert(Object obj) {
        DataBaseUtil.getLiteOrm().insert(obj);
    }

    public static <T> void insert(List<T> list) {
        DataBaseUtil.getLiteOrm().insert(list);
    }

    public static void update(Object obj) {
        DataBaseUtil.getLiteOrm().update(obj);
    }

}
