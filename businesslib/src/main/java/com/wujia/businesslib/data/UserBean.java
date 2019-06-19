package com.wujia.businesslib.data;

import java.io.Serializable;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-20
 * description ：
 */
@Deprecated
public class UserBean extends RootResponse {

    public User content;


//    nickName	家庭昵称
//    openId	家庭用户openId
//    headImage	头像
//    familyId	家庭ID
//    communityId	社区ID

   public static class User implements Serializable {
        public String familyId;//todo sdk 家庭ID
        public String nickName;
        public String openId;//原来接口获取卡片接口需要的参数
        public String headImage;
        public String communityId;//todo SDK 社区id
        public String accid;//todo sdk需要的padAccid  字段
        public String dockkey;//todo sdk安防相关
        public String docksn;
        public String buttonkey;//todo sdk安防相关 锁
    }
}
