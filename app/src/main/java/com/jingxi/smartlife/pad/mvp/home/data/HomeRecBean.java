package com.jingxi.smartlife.pad.mvp.home.data;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.wujia.lib_common.base.RootResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Author: created by shenbingkai on 2019/2/11 00 19
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class HomeRecBean extends RootResponse {


    public static final String TYPE_LINK = "WU";
    public static final String TYPE_APP_PAGE = "IU";
    public static final String TYPE_IMAGE = "IMG";
    public static final String TYPE_FUN = "OP";//功能型 TODO


    public static final String TYPE_ADD = "add";


    public HomeRecBean() {
    }

    public ArrayList<Card> data;

    public static class Card implements Serializable {

        public Card(String type) {
            this.type = type;
        }

        public Card() {
        }


        public String id;

        public String title;
        @SerializedName("icon")
        public String image;
        //        public String linkType;
        @SerializedName("url")
        public String linkUrl;

//        public String headImage;

        //        public String isShow;
        public List<UserCards> userCards;

        public String type;
        @SerializedName("memo")
        private String explain;

        @SuppressWarnings("UnusedReturnValue")
        public String getExplain() {
            if (!TextUtils.isEmpty(explain)) {
                return explain.replaceAll("_", "\n");
            }
            return explain;
        }
        //        public ArrayList<Subscriptions> subscriptions;//todo 目前接口缺该字段，是否需要？

        @Override
        public int hashCode() {
            return Objects.hash(id);
        }

        //重写equals方法
        @Override
        public boolean equals(Object o) {
            return o instanceof Card
                    && this.id.equals(((Card) o).id);
        }

    }

    public static class UserCards {
        public String isShow;
    }


//    {
//        "id": 1,
//            "title": "慢走",
//            "image": "http://i0.hexunimg.cn/2016-07-09/184839746.jpg",
//            "position": 0,
//            "linkType": "external",
//            "linkUrl": "https://www.baidu.com/",
//            "headImage": "http://i0.hexunimg.cn/2016-07-09/184839746.jpg",
//            "type": "link",
//            "status": "pushing",
//            "beginTime": 1552873170000,
//            "endTime": null,
//            "explain": "第一日本小李",
//            "communityId": 1,
//            "subscriptions": [
//        {
//            "id": 1,
//                "quickCardId": 1,
//                "serviceId": 1,
//                "propertyCategoryId": null,
//                "propertyProductId": null
//        }
//]
//    },
//    {
//        "id": 2,
//            "title": "快餐外卖美食",
//            "image": "http://i0.hexunimg.cn/2016-07-09/184839746.jpg",
//            "position": 0,
//            "linkType": null,
//            "linkUrl": "",
//            "headImage": "http://i0.hexunimg.cn/2016-07-09/184839746.jpg",
//            "type": "function",
//            "status": "pushing",
//            "beginTime": 1552873170000,
//            "endTime": null,
//            "explain": "排插",
//            "communityId": 1,
//            "subscriptions": []
//    }
}
