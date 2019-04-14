package com.jingxi.smartlife.pad.mvp.home.data;

import com.wujia.businesslib.data.RootResponse;

import java.util.ArrayList;

/**
 * author ：shenbingkai@163.com
 * date ：2019-04-13
 * description ：
 */
public class LockADBean extends RootResponse {

    public ArrayList<AD> content;

    public static class AD{
        public String image;
        public int imageType;
    }
}
