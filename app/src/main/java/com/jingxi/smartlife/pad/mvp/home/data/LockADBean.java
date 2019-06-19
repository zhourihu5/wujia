package com.jingxi.smartlife.pad.mvp.home.data;

import com.google.gson.annotations.SerializedName;
import com.wujia.businesslib.data.RootResponse;

import java.util.ArrayList;

/**
 * author ：shenbingkai@163.com
 * date ：2019-04-13
 * description ：
 */
public class LockADBean extends RootResponse {

    public AD data;

    public static class AD{
        @SerializedName("cover")
        public String image;
        public String url;

//        public int imageType;
    }
}
