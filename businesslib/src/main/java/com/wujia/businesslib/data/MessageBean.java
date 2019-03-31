package com.wujia.businesslib.data;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;
import com.wujia.businesslib.data.RootResponse;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-30
 * description ：
 */
public class MessageBean extends RootResponse {

    public Message content;

    public static class Message {

        public PropertyInfo propertyInformation;
        public PropertyMessage propertyMessage;
    }

    public static class PropertyInfo {

        public String nickName;
        public String headImage;
        public String mobile;
        public String accid;
        public String communityName;
        public String communityId;
    }

    public static class PropertyMessage {

        public String title;
        public String pureText;
        public String type;
        public String id;
        public String communityId;
        public String createDate;
        public String typeMsg;
        public String senderAccId;
    }
}
