package com.wujia.lib_common.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

/**
 * Created by xmren on 2018/5/17.
 */

object GsonUtil {
    private var gson: Gson? = null

    init {
        if (gson == null) {
            gson = Gson()
        }
    }

    /**
     * 转成json
     *
     * @param object
     * @return
     */
    fun GsonString(`object`: Any): String? {
        var gsonString: String? = null
        if (gson != null) {
            gsonString = gson!!.toJson(`object`)
        }
        return gsonString
    }

    /**
     * 转成bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    fun <T> GsonToBean(gsonString: String, cls: Class<T>): T? {
        var t: T? = null
        if (gson != null) {
            t = gson!!.fromJson(gsonString, cls)
        }
        return t
    }

    /**
     * 转成list
     *
     * @param gsonString
     * @param cls
     * @return
     */
    @Deprecated("")
    fun <T> GsonToList(gsonString: String, cls: Class<T>): List<T>? {
        var list: List<T>? = null
        if (gson != null) {
            list = gson!!.fromJson<List<T>>(gsonString, object : TypeToken<ArrayList<T>>() {

            }.type)
        }
        return list
    }


    /**
     * 复杂类型需要使用给方法处理
     * 例如GsonToList需要用方法处理
     *
     * @param gsonString
     * @param typeToken
     * @param <T>
     * @return
    </T> */
    fun <T> GsonToType(gsonString: String, typeToken: TypeToken<T>): T? {
        var any: T? = null
        if (gson != null) {
            any = gson!!.fromJson<T>(gsonString, typeToken.type)
        }
        return any
    }

}
