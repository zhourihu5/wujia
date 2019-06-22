package com.wujia.businesslib.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-31
 * description ：
 */
public class VersionBean extends RootResponse {

    public Version data;

    public static class Version implements Serializable {

        @SerializedName("showVer")
        public String versionName;
        @SerializedName("url")
        public String imageurl;
        @SerializedName("sysVer")
        public String versionCode;
        @SerializedName("versionDesc")
        public String desc;

//        public String packageName;
//        public int versionId;
//        public boolean necessaryUpdate;
    }
}
