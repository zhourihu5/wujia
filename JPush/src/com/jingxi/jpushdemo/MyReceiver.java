package com.jingxi.jpushdemo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.wujia.lib_common.base.Constants;
import com.wujia.businesslib.DownloadUtil;
import com.wujia.businesslib.base.BaseApplication;
import com.wujia.businesslib.data.Advert;
import com.wujia.businesslib.data.VersionBean;
import com.wujia.businesslib.event.EventBusUtil;
import com.wujia.businesslib.event.EventCardChange;
import com.wujia.businesslib.event.EventMsg;
import com.wujia.businesslib.event.EventSubscription;
import com.wujia.businesslib.event.EventWakeup;
import com.wujia.businesslib.listener.DownloadListener;
import com.wujia.lib_common.utils.AppUtil;
import com.wujia.lib_common.utils.GsonUtil;
import com.wujia.lib_common.utils.LogUtil;
import com.wujia.lib_common.utils.VersionUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 自定义接收器
 * <p>
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class MyReceiver extends BroadcastReceiver {

    public static final String TYPE_MSG = "MSG";
    public static final String TYPE_CARD = "CARD";
    public static final String TYPE_ADV = "ADV";
    public static final String TYPE_SYS = "SYS";
    public static final String TYPE_MARKET = "MARKET";//TODO 服务

    private static final String TAG = "JIGUANG-Example";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            Logger.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                Logger.d(TAG, "[MyReceiver] 接收Registration Id : " + regId);
                //send the Registration Id to your server...

            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                Logger.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                Logger.d(TAG, "[MyReceiver] 接收到推送下来的自定义消息:contentType: " + bundle.getString(JPushInterface.EXTRA_CONTENT_TYPE));
                processCustomMessage(context, bundle);

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知");
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                Logger.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                Logger.d(TAG, "[MyReceiver] 用户点击打开了通知");

                //打开自定义的Activity
//				Intent i = new Intent(context, TestActivity.class);
                Intent i = new Intent();
                i.setClassName(context, "com.jingxi.smartlife.pad.mvp.MainActivity");
                i.putExtras(bundle);
                //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                Logger.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                Logger.w(TAG, "[MyReceiver]" + intent.getAction() + " connected state change to " + connected);
            } else {
                Logger.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {

        }

    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    Logger.i(TAG, "This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Logger.e(TAG, "Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.get(key));
            }
        }
        return sb.toString();
    }

    private void processCustomMessage(Context context, Bundle bundle) {
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        String type = bundle.getString(JPushInterface.EXTRA_CONTENT_TYPE);

        switch (type) {
            case TYPE_MARKET://todo 服务
                int marketType = EventSubscription.TYPE_NOTIFY;
                try {
                    marketType = Integer.valueOf(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                EventSubscription eventSubscription = new EventSubscription(marketType);
                eventSubscription.eventType = EventSubscription.PUSH_NOTIFY;
                EventBusUtil.post(eventSubscription);
                break;
            case TYPE_ADV:

                Activity currentActivity = BaseApplication.getCurrentAcitivity();
                if (currentActivity != null && currentActivity.getClass().getName().equals("com.jingxi.smartlife.pad.safe.mvp.view.VideoCallActivity")) {
                    return;
                }
                Advert advert = GsonUtil.GsonToBean(message, Advert.class);
                Intent intent = new Intent();
                intent.setClassName(context, "com.jingxi.smartlife.pad.mvp.login.AdvertActivity");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(Constants.INTENT_KEY_1, advert);
                context.startActivity(intent);
                EventBusUtil.post(new EventWakeup());

                break;
            case TYPE_CARD:
                EventBusUtil.post(new EventCardChange());
                break;
            case TYPE_MSG:
                EventBusUtil.post(new EventMsg(EventMsg.TYPE_NEW_MSG));
                break;
            case TYPE_SYS:
                VersionBean.Version bean = GsonUtil.GsonToBean(message, VersionBean.Version.class);

                boolean update = isUpdate(bean);
                if (update )
                 {
                    DownloadUtil.download(bean.imageurl, new DownloadListener() {
                        @Override
                        public void onTaskStart() {
                            LogUtil.i("download onTaskStart" );
                        }

                        @Override
                        public void onTaskProgress(int percent, long currentOffset, long totalLength) {
                            LogUtil.i(String.format("download onTaskProgress percent=%d,currentOffset=%d,totalLength=%d" ,percent,currentOffset,totalLength));
                        }

                        @Override
                        public void onTaskComplete(int state, final String filePath) {
                            switch (state) {

                                case DownloadUtil.STATE_COMPLETE:
                                    LogUtil.i("download success,filepath:" + filePath);
                                    Observable.create(new ObservableOnSubscribe<Boolean>() {
                                        @Override
                                        public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                                            LogUtil.i("install " + filePath);
                                            boolean install = AppUtil.install(filePath);
                                            emitter.onNext(install);
                                        }
                                    }).subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(new Consumer<Boolean>() {
                                                @Override
                                                public void accept(Boolean install) throws Exception {
                                                    LogUtil.i("install success:" + install);
                                                }
                                            });
                                    break;
                                case DownloadUtil.STATE_CANCELED:
                                case DownloadUtil.STATE_OTHER:
                                    LogUtil.i("download error");
                                    break;
                            }
                        }
                    });
                }

                break;
        }
    }

    public static boolean isUpdate(VersionBean.Version bean) {
        int versionId = VersionUtil.getVersionCode();
        int versonCode= Integer.valueOf(bean.versionCode);
        String versonName=VersionUtil.getVersionName();
        return versonCode> versionId||
                (versonCode==versionId&&!versonName.equals(bean.versionName));
    }
}
