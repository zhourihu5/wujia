package com.wujia.businesslib.data

import com.litesuits.orm.db.annotation.PrimaryKey
import com.litesuits.orm.db.annotation.Table
import com.litesuits.orm.db.enums.AssignType

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-30
 * description ：
 */

@Deprecated("")
@Table("table_message")
class DBMessage {

    @PrimaryKey(AssignType.AUTO_INCREMENT)
    var _id: Int = 0

    var _type: String? = null

    var title: String? = null
    var type: String? = null
    var id: String? = null
    var communityId: String? = null
    var createDate: String? = null
    var typeText: String? = null
    var senderAccId: String? = null


    var info_nickName: String? = null
    var info_mobile: String? = null
    var info_communityName: String? = null
    var info_communityId: String? = null


    var _read_state: Int = 0
    var _show_state: Int = 0

    companion object {

        const val TYPE_NOTIFY = "noticeMessage"
    }
}
