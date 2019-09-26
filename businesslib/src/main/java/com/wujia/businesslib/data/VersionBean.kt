package com.wujia.businesslib.data

import com.google.gson.annotations.SerializedName
import com.wujia.lib_common.base.RootResponse

import java.io.Serializable

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-31
 * description ：
 */
class VersionBean : RootResponse() {

    var data: Version? = null

    class Version : Serializable {

        @SerializedName("showVer")
        var versionName: String? = null
        @SerializedName("url")
        var imageurl: String? = null
        @SerializedName("sysVer")
        var versionCode: String? = null
        @SerializedName("versionDesc")
        var desc: String? = null

        //        public String packageName;
        //        public int versionId;
        //        public boolean necessaryUpdate;
    }
}
