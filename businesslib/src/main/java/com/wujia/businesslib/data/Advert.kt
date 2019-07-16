package com.wujia.businesslib.data

import com.google.gson.annotations.SerializedName

import java.io.Serializable

/**
 * author ：shenbingkai@163.com
 * date ：2019-04-13
 * description ：
 */
class Advert : Serializable {

    @SerializedName("url")
    var href: String? = null//链接
    @SerializedName("cover")
    var url: String? = null//图片
    var title: String? = null
    var type: String? = null
    var id: Int = 0
}
