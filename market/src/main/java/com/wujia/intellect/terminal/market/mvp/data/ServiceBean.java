package com.wujia.intellect.terminal.market.mvp.data;

import com.litesuits.orm.db.annotation.Ignore;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;
import com.wujia.businesslib.data.RootResponse;

import java.util.ArrayList;

/**
 * Author: created by shenbingkai on 2019/2/23 00 12
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class ServiceBean extends RootResponse {

    public static final int TYPE_NATIVE = 0;
    public static final int TYPE_HTML = 1;
    public static final int TYPE_RN = 2;
    public static final int TYPE_WEB = 3;
//    0-native（仅支持android）; 1-HTML5;2-ReactNative,3-REMOTE-HTML5


//    explain: "爱艺奇",
//    image: "http://image.house-keeper.cn/smart/image/2018-04-26/426afa6c-a3d0-42c7-9b09-9f6931d1f324.jpg",
//    classification_name: "知识服务",
//    app_type: 0,
//    app_url: "http://image.house-keeper.cn/smart/image/2018-04-26/e4283230-33a9-47ce-9131-40b819538515.apk",
//    subscribe: 0,
//    classStatus: "on",
//    service_id: "1",
//    name: "爱奇艺",
//    classification_id: 1,
//    id: 1,
//    position: 0,
//    packageName: "com.qiyi.video",
//    status: "on"


    public int totalSize;
    public ArrayList<Service> content;


    @Table("table_service")
    public static class Service {

        @PrimaryKey(AssignType.AUTO_INCREMENT)
        public int _id;

        public String explain;
        public String image;
        public int app_type;
        public String app_url;
        public String service_id;
        public String name;
        public String packageName;
    }
}
