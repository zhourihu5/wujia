package com.wujia.intellect.terminal.mvp.home.data;

import com.wujia.businesslib.data.RootResponse;

import java.util.ArrayList;

/**
 * Author: created by shenbingkai on 2019/2/11 00 19
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class HomeRecBean extends RootResponse {

    public int _viewType;

    public HomeRecBean(int type) {
        this._viewType = type;
    }

    public ArrayList<Card> content;

    public class Card {

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

    }

    public class Subscriptions {
        public String id;
        public String quickCardId;
        public String serviceId;
        public String propertyCategoryId;
        public String propertyProductId;
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
