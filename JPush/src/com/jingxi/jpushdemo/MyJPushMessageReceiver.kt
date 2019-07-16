package com.jingxi.jpushdemo

import android.content.Context

import cn.jpush.android.api.JPushMessage
import cn.jpush.android.service.JPushMessageReceiver

/**
 * 自定义JPush message 接收器,包括操作tag/alias的结果返回(仅仅包含tag/alias新接口部分)
 */
class MyJPushMessageReceiver : JPushMessageReceiver() {

    override fun onTagOperatorResult(context: Context?, jPushMessage: JPushMessage) {
        TagAliasOperatorHelper.instance.onTagOperatorResult(context!!, jPushMessage)
        super.onTagOperatorResult(context, jPushMessage)
    }

    override fun onCheckTagOperatorResult(context: Context, jPushMessage: JPushMessage) {
        TagAliasOperatorHelper.instance.onCheckTagOperatorResult(context, jPushMessage)
        super.onCheckTagOperatorResult(context, jPushMessage)
    }

    override fun onAliasOperatorResult(context: Context, jPushMessage: JPushMessage) {
        TagAliasOperatorHelper.instance.onAliasOperatorResult(context, jPushMessage)
        super.onAliasOperatorResult(context, jPushMessage)
    }

    override fun onMobileNumberOperatorResult(context: Context, jPushMessage: JPushMessage) {
        TagAliasOperatorHelper.instance.onMobileNumberOperatorResult(context, jPushMessage)
        super.onMobileNumberOperatorResult(context, jPushMessage)
    }
}
