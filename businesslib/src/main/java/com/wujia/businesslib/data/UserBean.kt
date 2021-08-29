package com.wujia.businesslib.data

import com.wujia.lib_common.base.RootResponse

import java.io.Serializable

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-20
 * description ：
 */
@Deprecated("")
class UserBean : RootResponse() {

    var content: User? = null


    //    nickName	家庭昵称
    //    openId	家庭用户openId
    //    headImage	头像
    //    familyId	家庭ID
    //    communityId	社区ID

    class User : Serializable {
        var familyId: String? = null//todo sdk 家庭ID
        var openId: String? = null//原来接口获取卡片接口需要的参数
        var headImage: String? = null
        var communityId: String? = null//todo SDK 社区id
        var accid: String? = null//todo sdk需要的padAccid  字段
        var dockkey: String? = null//todo sdk安防相关
    }
}
