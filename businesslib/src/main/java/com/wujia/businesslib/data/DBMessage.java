package com.wujia.businesslib.data;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-30
 * description ：
 */

@Deprecated
@Table("table_message")
public class DBMessage {

    public static final String TYPE_PROPERTY = "propertyMessage";
    public static final String TYPE_NOTIFY = "noticeMessage";

    @PrimaryKey(AssignType.AUTO_INCREMENT)
    public int _id;

    public String _type;

    public String title;
    public String pureText;
    public String type;
    public String id;
    public String communityId;
    public String createDate;
    public String typeText;
    public String senderAccId;


    public String info_nickName;
    public String info_headImage;
    public String info_mobile;
    public String info_accid;
    public String info_communityName;
    public String info_communityId;


    public int _read_state;
    public int _show_state;
}
