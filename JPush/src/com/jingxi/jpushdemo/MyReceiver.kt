package com.jingxi.jpushdemo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import cn.jpush.android.api.JPushInterface
import com.wujia.businesslib.DownloadUtil
import com.wujia.businesslib.base.BaseApplication
import com.wujia.businesslib.data.Advert
import com.wujia.businesslib.data.VersionBean
import com.wujia.businesslib.event.*
import com.wujia.businesslib.listener.DownloadListener
import com.wujia.lib_common.base.Constants
import com.wujia.lib_common.utils.AppUtil
import com.wujia.lib_common.utils.GsonUtil
import com.wujia.lib_common.utils.LogUtil
import com.wujia.lib_common.utils.VersionUtil
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONException
import org.json.JSONObject

/**
 * 自定义接收器
 *
 *
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        try {
            val bundle = intent.extras
            Logger.d(TAG, "[MyReceiver] onReceive - " + intent.action + ", extras: " + printBundle(bundle!!))

            if (JPushInterface.ACTION_REGISTRATION_ID == intent.action) {
                val regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID)
                Logger.d(TAG, "[MyReceiver] 接收Registration Id : " + regId!!)
                //send the Registration Id to your server...

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED == intent.action) {
                Logger.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE)!!)
                Logger.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息:contentType: " + bundle.getString(JPushInterface.EXTRA_CONTENT_TYPE)!!)
                processCustomMessage(context, bundle)

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED == intent.action) {
                Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知")
                val notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID)
                Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: $notifactionId")

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED == intent.action) {
                Logger.d(TAG, "[MyReceiver] 用户点击打开了通知")

                //打开自定义的Activity
                //				Intent i = new Intent(context, TestActivity.class);
                val i = Intent()
                i.setClassName(context, "com.jingxi.smartlife.pad.mvp.MainActivity")
                i.putExtras(bundle)
                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                context.startActivity(i)

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK == intent.action) {
                Logger.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA)!!)
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE == intent.action) {
                val connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false)
                Logger.w(TAG, "[MyReceiver]" + intent.action + " connected state change to " + connected)
            } else {
                Logger.d(TAG, "[MyReceiver] Unhandled intent - " + intent.action!!)
            }
        } catch (e: Exception) {

        }

    }

    private fun processCustomMessage(context: Context, bundle: Bundle) {
        val message = bundle.getString(JPushInterface.EXTRA_MESSAGE)
        val extras = bundle.getString(JPushInterface.EXTRA_EXTRA)

        when (bundle.getString(JPushInterface.EXTRA_CONTENT_TYPE)) {
            TYPE_MARKET//todo 服务
            -> {
                var marketType = EventSubscription.TYPE_NOTIFY
                try {
                    marketType = Integer.valueOf(message!!)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                val eventSubscription = EventSubscription(marketType)
                eventSubscription.eventType = EventSubscription.PUSH_NOTIFY
                EventBusUtil.post(eventSubscription)
            }
            TYPE_ADV -> {

                val currentActivity = BaseApplication.currentAcitivity
                if (currentActivity != null && currentActivity.javaClass.name == "com.jingxi.smartlife.pad.safe.mvp.view.VideoCallActivity") {
                    return
                }
                val advert = message?.let { GsonUtil.GsonToBean(it, Advert::class.java) }
                val intent = Intent()
                intent.setClassName(context, "com.jingxi.smartlife.pad.mvp.login.AdvertActivity")
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                intent.putExtra(Constants.INTENT_KEY_1, advert)
                context.startActivity(intent)
                EventBusUtil.post(EventWakeup())
            }
            TYPE_CARD -> EventBusUtil.post(EventCardChange())
            TYPE_MSG -> EventBusUtil.post(EventMsg(EventMsg.TYPE_NEW_MSG))
            TYPE_SYS -> {
                val bean = message?.let { GsonUtil.GsonToBean(it, VersionBean.Version::class.java) }

                val update = isUpdate(bean!!)
                if (update) {
                    bean.imageurl?.let {
                        DownloadUtil.download(it, object : DownloadListener {
                            override fun onTaskStart() {
                                LogUtil.i("download onTaskStart")
                            }

                            override fun onTaskProgress(percent: Int, currentOffset: Long, totalLength: Long) {
                                LogUtil.i(String.format("download onTaskProgress percent=%d,currentOffset=%d,totalLength=%d", percent, currentOffset, totalLength))
                            }

                            override fun onTaskComplete(state: Int, filePath: String) {
                                when (state) {

                                    DownloadUtil.STATE_COMPLETE -> {
                                        LogUtil.i("download success,filepath:$filePath")
                                        Observable.create(ObservableOnSubscribe<Boolean> { emitter ->
                                            LogUtil.i("install $filePath")
                                            val install = AppUtil.install(filePath)
                                            emitter.onNext(install)
                                        }).subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe { install -> LogUtil.i("install success:" + install!!) }
                                    }
                                    DownloadUtil.STATE_CANCELED, DownloadUtil.STATE_OTHER -> LogUtil.i("download error")
                                }
                            }
                        })
                    }
                }
            }
            TYPE_GRB->{//团购
                EventBusUtil.post(EventGroupBuy())
            }
        }
    }

    companion object {

        const val TYPE_MSG = "MSG"
        const val TYPE_CARD = "CARD"
        const val TYPE_ADV = "ADV"
        const val TYPE_SYS = "SYS"
        const val TYPE_MARKET = "MARKET"//TODO 服务
        const val TYPE_GRB="GRB"//团购

        private const val TAG = "JIGUANG-Example"

        // 打印所有的 intent extra 数据
        private fun printBundle(bundle: Bundle): String {
            val sb = StringBuilder()
            for (key in bundle.keySet()) {
                if (key == JPushInterface.EXTRA_NOTIFICATION_ID) {
                    sb.append("\nkey:" + key + ", value:" + bundle.getInt(key))
                } else if (key == JPushInterface.EXTRA_CONNECTION_CHANGE) {
                    sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key))
                } else if (key == JPushInterface.EXTRA_EXTRA) {
                    if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                        Logger.i(TAG, "This message has no Extra data")
                        continue
                    }

                    try {
                        val json = JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA))
                        val it = json.keys()

                        while (it.hasNext()) {
                            val myKey = it.next()
                            sb.append("\nkey:" + key + ", value: [" +
                                    myKey + " - " + json.optString(myKey) + "]")
                        }
                    } catch (e: JSONException) {
                        Logger.e(TAG, "Get message extra JSON error!")
                    }

                } else {
                    sb.append("\nkey:" + key + ", value:" + bundle.get(key))
                }
            }
            return sb.toString()
        }

        fun isUpdate(bean: VersionBean.Version): Boolean {
            val versionId = VersionUtil.versionCode
            val versonCode = Integer.valueOf(bean.versionCode)
            val versonName = VersionUtil.versionName
            return versonCode > versionId || versonCode == versionId && versonName != bean.versionName
        }
    }
}
