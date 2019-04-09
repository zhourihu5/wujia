package com.jingxi.smartlife.pad.mvp.home.data;

import com.wujia.businesslib.data.RootResponse;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Author: created by shenbingkai on 2019/2/11 00 19
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class HomeRecBean extends RootResponse {


    public static final String TYPE_LINK = "link";
    public static final String TYPE_FUN = "function";
    public static final String TYPE_IMAGE = "image";
    public static final String TYPE_ADD = "add";


    public static final String TYPE_LINK_EXTERNAL = "external";//外链
    public static final String TYPE_LINK_INTERNALL = "internal";//内链

    public HomeRecBean() {
    }

    public ArrayList<Card> content;

    public static class Card implements Serializable {

        public Card(String type) {
            this.type = type;
        }

        public Card() {
        }


        public String id;
        public String title;
        public String image;
        public String linkType;
        public String linkUrl;
        public String headImage;
        public String type;
        public String communityId;
        public String explain;
        public ArrayList<Subscriptions> subscriptions;


        //重写equals方法
        @Override
        public boolean equals(Object o) {
            return o instanceof Card
                    && this.id.equals(((Card) o).id);
        }

    }

    public static class Subscriptions implements Serializable {
        public String id;
        public String quickCardId;
        public String serviceId;
        public String serviceImage;
        public String serviceTitle;
        public String servicePackage;
        public String propertyCategoryId;
        public String propertyProductId;
        public String serviceUrl;
        public String serviceDesc;
        public String type;

        public boolean _installed;

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
