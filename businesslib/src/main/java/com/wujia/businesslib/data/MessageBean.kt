package com.wujia.businesslib.data

import com.wujia.lib_common.base.RootResponse

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-30
 * description ：
 */
class MessageBean : RootResponse() {

    var content: Message? = null
    var _type = ""

    class Message {

        var propertyInformation: PropertyInfo? = null
        var propertyMessage: PropertyMessage? = null
    }

    class PropertyInfo {

        var nickName: String? = null
        var headImage: String? = null
        var mobile: String? = null
        var accid: String? = null
        var communityName: String? = null
        var communityId: String? = null
    }

    class PropertyMessage {

        var title: String? = null
        var pureText: String? = null
        var type: String? = null
        var id: String? = null
        var communityId: String? = null
        var createDate: String? = null
        var typeName: String? = null
        var typeMsg: String? = null
        var senderAccId: String? = null
    }
}
