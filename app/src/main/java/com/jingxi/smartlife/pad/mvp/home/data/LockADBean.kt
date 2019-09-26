package com.jingxi.smartlife.pad.mvp.home.data

import com.google.gson.annotations.SerializedName
import com.wujia.lib_common.base.RootResponse

/**
 * author ：shenbingkai@163.com
 * date ：2019-04-13
 * description ：
 */
class LockADBean : RootResponse() {

    var data: AD? = null

    class AD {
        @SerializedName("cover")
        var image: String? = null
        var url: String? = null

        //        public int imageType;
    }
}
