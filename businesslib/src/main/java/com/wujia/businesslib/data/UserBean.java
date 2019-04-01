package com.wujia.businesslib.data;

import java.io.Serializable;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-20
 * description ：
 */
public class UserBean extends RootResponse {

    public User content;


//    nickName	家庭昵称
//    openId	家庭用户openId
//    headImage	头像
//    familyId	家庭ID
//    communityId	社区ID

   public static class User implements Serializable {
        public String familyId;
        public String nickName;
        public String openId;
        public String headImage;
        public String communityId;
        public String accid;
    }
}
