package com.jingxi.smartlife.pad.market.mvp.data;

import com.wujia.lib_common.base.RootResponse;

import java.util.ArrayList;

/**
 * author ：shenbingkai@163.com
 * date ：2019-04-23
 * description ：
 */
public class FindBannerBean extends RootResponse {

    public ArrayList<FindBanner> content;

    public static class FindBanner {
        public String title;
        public String imgPic;
        public String links;
        public String isShow;
    }
}
