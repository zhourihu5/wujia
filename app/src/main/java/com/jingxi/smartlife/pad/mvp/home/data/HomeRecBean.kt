package com.jingxi.smartlife.pad.mvp.home.data

import android.text.TextUtils
import com.google.gson.annotations.SerializedName
import com.wujia.lib_common.base.RootResponse
import java.io.Serializable
import java.util.*


/**
 * Author: created by shenbingkai on 2019/2/11 00 19
 * Email:  shenbingkai@gamil.com
 * Description:
 */
class HomeRecBean : RootResponse() {

    var data: ArrayList<Card>? = null

    class Card : Serializable {


        var id: String? = null

        var title: String? = null
        @SerializedName("icon")
        var image: String? = null
        //        public String linkType;
        @SerializedName("url")
        var linkUrl: String? = null

        //        public String headImage;

        //        public String isShow;
        var isShow: String? = null

        var type: String?=null
        @SerializedName("memo")
        var explain: String? = null
        get(){
            return if (!TextUtils.isEmpty(field)) {
                field!!.replace("_".toRegex(), "\n")
            } else field
        }

        constructor(type: String) {
            this.type = type
        }


        override fun hashCode(): Int {
            return Objects.hash(id)
        }

        //重写equals方法
        override fun equals(o: Any?): Boolean {
            return o is Card && this.id == o.id
        }

    }

    companion object {


        const val TYPE_LINK = "WU"
        const val TYPE_APP_PAGE = "IU"
        const val TYPE_IMAGE = "IMG"


        const val TYPE_ADD = "add"
    }


}
