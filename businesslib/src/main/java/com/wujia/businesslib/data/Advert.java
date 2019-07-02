package com.wujia.businesslib.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * author ：shenbingkai@163.com
 * date ：2019-04-13
 * description ：
 */
public class Advert implements Serializable {

    @SerializedName("url")
    public String href;//链接
    @SerializedName("cover")
    public String url;//图片
    public String title;
    public String type;
    public int id;
}
